package com.untildawn.models.Players;

public class Wallet {
    private int coin;

    public Wallet(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void increaseCoin(int coin) {
        this.coin += coin;
    }

    public void decreaseCoin(int coin) {

        this.coin -= coin;
    }
}
