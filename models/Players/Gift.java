package com.untildawn.models.Players;

import com.untildawn.models.Items.ItemInstance;

import java.util.ArrayList;

public class Gift {
    private Player receiver;
    private Player donor;
    private ArrayList<ItemInstance> items;
    private int rate;
    private boolean isRated;

    public Gift(Player receiver, Player donor, ArrayList<ItemInstance> items) {
        this.donor = donor;
        this.receiver = receiver;
        this.items = new ArrayList<>(items);
        this.rate = -1;
        this.isRated = false;
    }

    public ArrayList<ItemInstance> getItems() {
        return items;
    }

    public Player getDonor() {
        return donor;
    }

    public Player getReceiver() {
        return receiver;
    }

    public int getRate() {
        return rate;
    }

    public boolean setRate(int rate) {
        if (this.isRated) return false;

        this.rate = rate;
        this.isRated = true;
        return true;
    }

    public String getItemName() {
        try {
            return items.get(0).getDefinition().getDisplayName();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getItemAmount() {
        return items.size();
    }
}
