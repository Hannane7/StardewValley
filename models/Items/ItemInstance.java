package com.untildawn.models.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.untildawn.Enums.ItemConsts.ItemAttributes;


import java.util.HashMap;
import java.util.Map;

/*
    Every item in the game!
 */
public class ItemInstance {
    private final ItemDefinition definition;
    private final String uniqueId; // UUID is unique for each item in the game.
    private Map<ItemAttributes, Object> attributes; // like durability, level, ...
    private boolean isWatered;
    private boolean isDroppedByPlayer;
    private Texture texture;

    public ItemInstance(ItemDefinition definition) {
        this.definition = definition;
        this.uniqueId = null; // a function will be implemented to generate UUID
        this.attributes = definition.getBaseAttributes();
        this.isWatered = false;
        this.isDroppedByPlayer = false;
        if (definition.hasAttribute(ItemAttributes.path)) {
            if (!((String) definition.getAttribute(ItemAttributes.path)).isEmpty())
                texture = new Texture((Gdx.files.internal((String) definition.getAttribute(ItemAttributes.path))));
        }
    }

    public ItemDefinition getDefinition() {
        return definition;
    }

    public Map<ItemAttributes, Object> getAttributes() {
        return attributes;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public boolean isDroppedByPlayer() {
        return isDroppedByPlayer;
    }

    public void setDroppedByPlayer(boolean droppedByPlayer) {
        isDroppedByPlayer = droppedByPlayer;
    }

    public Object getAttribute(ItemAttributes attribute) {
        return attributes.get(attribute);
    }

    public void setAttribute(ItemAttributes attribute, Object value) {
        attributes.put(attribute, value);
    }

    public int decreaseDurability() {
        int x = (int) attributes.get(ItemAttributes.durability);
        attributes.put(ItemAttributes.durability, x - 1);
        return x;

    }

    public void increaseDurability(int durability) {
        attributes.put(ItemAttributes.durability, durability);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
