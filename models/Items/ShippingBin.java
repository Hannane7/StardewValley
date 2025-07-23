package com.untildawn.models.Items;

import com.untildawn.models.Players.Player;

import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private Map<Player, Integer> playerSoldItemsPrice;

    public ShippingBin() {
        this.playerSoldItemsPrice = new HashMap<>();
    }

    public void putIntoBin(Player player, int totalPrice) {
        this.playerSoldItemsPrice.put(player, totalPrice);
    }

    public int getPriceAndSetBinEmpty(Player player) {
        if (playerSoldItemsPrice.containsKey(player)) {
            int totalPrice = this.playerSoldItemsPrice.get(player);
            this.playerSoldItemsPrice.put(player, 0);
            return totalPrice;
        }
        return 0;
    }

    public Map<Player, Integer> getPlayerSoldItemsPrice() {
        return playerSoldItemsPrice;
    }
}
