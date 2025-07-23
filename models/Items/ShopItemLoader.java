package com.untildawn.models.Items;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.MapElements.Shop;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShopItemLoader {

    public static Map<String, List<ItemDefinition>> loader(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filePath));
        Map<String, List<ItemDefinition>> shopItemMap = new HashMap<>();

        for (JsonNode shopNode : root) {
            String shopName = shopNode.get("shopName").asText();
            JsonNode itemsNode = shopNode.get("items");

            if (shopName == null || shopName.isBlank() || itemsNode == null || !itemsNode.isArray()) {
                continue;
            }

            List<ItemDefinition> itemDefinitions = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                String idStr = itemNode.get("itemId").asText();
                String typeStr = itemNode.get("type").asText();
                String displayName = itemNode.get("name").asText();

                if (idStr == null || idStr.isBlank() || typeStr == null || typeStr.isBlank()) continue;

                ItemIDs itemId;
                ItemType itemType;

                try {
                    itemId = ItemIDs.valueOf(idStr.trim());
                    itemType = ItemType.valueOf(typeStr.trim());
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid item or type: " + idStr + " / " + typeStr);
                    continue;
                }

                Map<ItemAttributes, Object> attributes = new HashMap<>();
                JsonNode attrNode = itemNode.get("baseAttributes");

                if (attrNode != null) {
                    Iterator<Map.Entry<String, JsonNode>> fields = attrNode.fields();
                    while (fields.hasNext()) {
                        var entry = fields.next();
                        String key = entry.getKey();

                        try {
                            ItemAttributes attrKey = ItemAttributes.valueOf(key.trim());
                            JsonNode valueNode = entry.getValue();
                            Object value;

                            if (valueNode.isInt()) {
                                value = valueNode.asInt();
                            } else if (valueNode.isDouble()) {
                                value = valueNode.asDouble();
                            } else if (valueNode.isArray()) {
                                List<Object> list = new ArrayList<>();
                                for (JsonNode item : valueNode) {
                                    if (item.isInt()) list.add(item.asInt());
                                    else if (item.isDouble()) list.add(item.asDouble());
                                    else list.add(item.asText());
                                }
                                value = list;
                            } else if (valueNode.isObject()) {
                                Map<String, Object> map = new HashMap<>();
                                valueNode.fields().forEachRemaining(e -> {
                                    JsonNode val = e.getValue();
                                    if (val.isInt()) {
                                        map.put(e.getKey(), val.asInt());
                                    } else if (val.isDouble()) {
                                        map.put(e.getKey(), val.asDouble());
                                    } else {
                                        map.put(e.getKey(), val.asText());
                                    }
                                });
                                value = map;
                            } else {
                                value = valueNode.asText();
                            }

                            attributes.put(attrKey, value);
                        } catch (IllegalArgumentException ignored) {
                            System.err.println("Unknown attribute: " + key + " → skipped");
                        }
                    }
                }

                ItemDefinition def = new ItemDefinition(itemType, itemId, displayName, attributes);
                itemDefinitions.add(def);
            }

            shopItemMap.put(shopName, itemDefinitions);
        }

        return shopItemMap;
    }

    public static void loadShopItems() {
        Game game = App.getCurrentGame();
        Map<String, List<ItemDefinition>> shopItems;
        try {
            shopItems = loader("src/main/java/org/example/Data/ShopItems.json");
        } catch (IOException e) {
            System.err.println("Error load shop items.");
            return;
        }

        for (Map.Entry<String, List<ItemDefinition>> entry : shopItems.entrySet()) {
            String shopName = entry.getKey();
            List<ItemDefinition> items = entry.getValue();
            Map<ItemDefinition, Integer> shopProducts = new HashMap<>();
            for (ItemDefinition item : items) {
                int dailyLimit = (int) item.getAttribute(ItemAttributes.dailyLimit);
                shopProducts.put(item, dailyLimit);
            }
            Shop shop = game.getShop(shopName);
            shop.setProducts(shopProducts);
        }
    }
}

