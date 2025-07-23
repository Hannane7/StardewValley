package com.untildawn.models.Animals;

import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.Players.Player;


import java.util.HashMap;
import java.util.Map;

public class Animal extends ItemInstance {
    private String name;
    private int friendShip;
    private Player owner;
    private boolean isPet;
    private boolean isFed;
    private Position position;
    private boolean isOutside;
    private boolean hasProduct;
    private Map<ItemInstance, Integer> products;

    public Animal(ItemDefinition definition, String name, Player owner, Position position) {
        super(definition);
        this.name = name;
        this.owner = owner;
        this.position = position;
        this.friendShip = 0;
        this.isPet = false;
        this.isFed = false;
        this.isOutside = false;
        this.hasProduct = false;
        this.products = new HashMap<ItemInstance, Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendShip() {
        return friendShip;
    }

    public void increaseFriendShip(int friendShip) {
        this.friendShip = Math.min(1000, this.friendShip + friendShip);
    }
    public void decreaseFriendShip(int friendShip) {
        this.friendShip = Math.max(0, this.friendShip - friendShip);
    }
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public boolean isFed() {
        return isFed;
    }

    public void setFed(boolean fed) {
        isFed = fed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public boolean isHasProduct() {
        return hasProduct;
    }

    public void setHasProduct(boolean hasProduct) {
        this.hasProduct = hasProduct;
    }

    public Map<ItemInstance, Integer> getProducts() {
        return products;
    }
    public void setProduct(ItemInstance product, int quantity) {
        this.products.put(product, quantity);
    }
}
