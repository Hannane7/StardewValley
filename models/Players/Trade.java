package com.untildawn.models.Players;

import com.untildawn.models.Items.ItemDefinition;

public class Trade {
    private final Player seller;
    private final Player buyer;
    private final ItemDefinition itemSold;
    private final ItemDefinition itemCost;
    private final int itemCostAmount;
    private final int price;

    public Trade(Player seller, Player buyer, ItemDefinition itemSold,
                 ItemDefinition itemCost, int itemCostAmount, int price) {
        this.buyer = buyer;
        this.seller = seller;
        this.itemCost = itemCost;
        this.itemSold = itemSold;
        this.price = price;
        this.itemCostAmount = itemCostAmount;
    }

    public ItemDefinition getItemCost() {
        return itemCost;
    }

    public ItemDefinition getItemSold() {
        return itemSold;
    }

    public Player getBuyer() {
        return buyer;
    }

    public Player getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }

    public int getItemCostAmount() {
        return itemCostAmount;
    }
}
