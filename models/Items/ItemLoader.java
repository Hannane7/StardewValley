package com.untildawn.models.Items;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class ItemLoader {

    public static List<ItemDefinition> loader(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(filePath));
        List<ItemDefinition> itemDefinitions = new ArrayList<>();

        for (JsonNode node : root) {
            String idStr = node.get("itemId").asText();
            String typeStr = node.get("type").asText();
            String displayName = node.get("name").asText();

            if (idStr == null || idStr.isBlank() || typeStr == null || typeStr.isBlank()) continue;

            ItemIDs itemId;
            ItemType itemType;

            try {
                itemId = ItemIDs.valueOf(idStr.trim());
                itemType = ItemType.valueOf(typeStr.trim().toLowerCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid item or type: " + idStr + " / " + typeStr);
                continue;
            }

            Map<ItemAttributes, Object> attributes = new HashMap<>();
            JsonNode attrNode = node.get("baseAttributes");

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
                        } else if (valueNode.isTextual()) {
                            value = valueNode.asText();
                        } else if (valueNode.asBoolean()) {
                            value = valueNode.asBoolean();
                        } else if (valueNode.isDouble()) {
                            value = valueNode.asDouble();
                        } else if (valueNode.isArray()) {
                            List<Object> list = new ArrayList<>();
                            for (JsonNode itemNode : valueNode) {
                                if (itemNode.isInt()) {
                                    list.add(itemNode.asInt());
                                } else if (itemNode.isDouble()) {
                                    list.add(itemNode.asDouble());
                                } else if (itemNode.asBoolean()) {
                                    list.add(itemNode.asBoolean());
                                } else {
                                    list.add(itemNode.asText());
                                }
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

        return itemDefinitions;
    }

    public static void loadItems() {
        ArrayList<ItemDefinition> itemDefinitions;
        try {
            itemDefinitions = new ArrayList<>(loader("core/src/main/java/com/untildawn/data/ItemsDefiniton.json"));
        } catch (IOException e) {
            System.err.println("Problem loading the game. Please try again later.");
            e.printStackTrace();
            System.exit(1);
            return;
        }

        App.setItemDefinitions(itemDefinitions);
    }
}
