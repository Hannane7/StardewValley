package com.untildawn.models.Items;

import com.untildawn.Enums.ItemConsts.ItemIDs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Refrigerator {
    private Map<ItemIDs, ArrayList<ItemInstance>> items;
    public Refrigerator(){
        this.items = new HashMap<>();
    }
    public boolean addItem(ItemInstance item) {
        ItemIDs id = item.getDefinition().getId();
        if (id == null) {
            return false;
        }
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : this.items.entrySet()) {
            if (entry.getKey() == id) {
                ArrayList<ItemInstance> itemInstances = entry.getValue();
                itemInstances.add(item);
                return true;
            }
        }

        ArrayList<ItemInstance> newItemsList = new ArrayList<>();
        newItemsList.add(item);
        this.items.put(id, newItemsList);
        return true;
    }
    public void trashItem(ItemIDs id, int amount) {
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : this.items.entrySet()) {
            if (entry.getKey() == id) {
                ArrayList<ItemInstance> itemList = this.items.get(id);
                for (int i = 0; i < Math.min(amount, itemList.size()); i++) {
                    itemList.remove(itemList.size() - 1);
                }
                if (itemList.isEmpty()) {
                    this.items.remove(entry.getKey());
                }
                return;
            }
        }
    }

    public void trashItemAll(ItemIDs id) {
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : this.items.entrySet()) {
            if (entry.getKey() == id) {
                this.items.remove(id);
                this.items.remove(entry.getKey());
            }
        }
    }
    public boolean hasItem(ItemIDs id, int amount) {
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : this.items.entrySet()) {
            if (entry.getKey() == id && entry.getValue().size() >= amount) {
                return true;
            }
        }
        return false;
    }
    public boolean hasCapacity() {
        return this.items.size() < 4;
    }
    public int getItemAmount(ItemIDs id) {
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : this.items.entrySet()) {
            if (entry.getKey() == id) {
                return this.items.get(id).size();
            }
        }
        return 0;
    }
}
