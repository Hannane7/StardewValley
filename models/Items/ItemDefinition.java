package com.untildawn.models.Items;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;

import java.util.Map;

/*
    Like template for each item.
 */
public class ItemDefinition {
    private final ItemType type;
    private final ItemIDs id;
    private final String displayName;
    private final Map<ItemAttributes, Object> baseAttributes;

    public ItemDefinition(ItemType type, ItemIDs id, String displayName, Map<ItemAttributes, Object> baseAttributes) {
        this.type = type;
        this.id = id;
        this.displayName = displayName;
        this.baseAttributes = baseAttributes;
    }

    public ItemIDs getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public Map<ItemAttributes, Object> getBaseAttributes() {
        return baseAttributes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Object getAttribute(ItemAttributes attributes) {
        return baseAttributes.get(attributes);
    }

    public boolean hasAttribute(ItemAttributes attributes) {
        return baseAttributes.containsKey(attributes);
    }
}
