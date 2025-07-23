package com.untildawn.models.MapElements;

//import edu.stanford.nlp.parser.lexparser.Item;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.NPCs.NPC;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    A super class for shops in the game
 */
public class Shop {
    private String shopName;
    private Tile tile;
    private NPC owner;
    private int startTime;
    private int endTime;
    private Map<ItemDefinition, Integer> shopItems;
    private Position position;

    public Shop(String shopName, Tile tile, NPC owner, int startTime, int endTime, Position position) {
        this.shopName = shopName;
        this.tile = tile;
        this.owner = owner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shopItems = new HashMap<>();
        this.position = position;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public NPC getOwner() {
        return owner;
    }

    public String getShopName() {
        return shopName;
    }

    public Tile getTile() {
        return tile;
    }

    public Map<ItemDefinition, Integer> getShopItems() {
        return shopItems;
    }

    public ItemDefinition getProductDefinition(String productId) {
        for (Map.Entry<ItemDefinition, Integer> entry : this.shopItems.entrySet()) {
            ItemDefinition item = entry.getKey();
            if (item.getId().name().equalsIgnoreCase(productId)) {
                return item;
            }
        }
        return null;
    }
    public boolean hasProduct(String productId) {
        for (Map.Entry<ItemDefinition, Integer> entry : this.shopItems.entrySet()) {
            ItemDefinition item = entry.getKey();
            if (item.getId().name().equalsIgnoreCase(productId)) {
                return true;
            }
        }
        return false;
    }

    public void setProductStock(String productId, int newStock) {
        for (Map.Entry<ItemDefinition, Integer> entry : this.shopItems.entrySet()) {
            ItemDefinition item = entry.getKey();
            int stock = entry.getValue();
            if (item.getId().name().equalsIgnoreCase(productId)) {
                this.shopItems.put(item, newStock);
            }
        }
    }
    public int getProductStock(String productId) {
        for (Map.Entry<ItemDefinition, Integer> entry : this.shopItems.entrySet()) {
            ItemDefinition item = entry.getKey();
            int stock = entry.getValue();
            if (item.getId().name().equalsIgnoreCase(productId)) {
                return stock;
            }
        }
        return 0;
    }
    public void setProducts(Map<ItemDefinition, Integer> products) {
        this.shopItems = products;
    }

    public Position getPosition() {
        return position;
    }
}
