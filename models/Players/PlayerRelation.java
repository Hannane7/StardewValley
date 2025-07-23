package com.untildawn.models.Players;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class PlayerRelation {
    private Player player1;
    private Player player2;
    private int xp;
    private boolean areMarried;
    private ArrayList<String> talkHistory;
    private int friendshipLevel;
    private ArrayList<Gift> gifs;
    private boolean isFlowerGifted;
    private int numberOfHugsInDay;

    public PlayerRelation(Player player1, Player player2) {
        this.talkHistory = new ArrayList<>();
        this.player1 = player1;
        this.player2 = player2;
        this.xp = 0;
        this.areMarried = false;
        this.friendshipLevel = 0;
        this.isFlowerGifted = false;
        this.gifs = new ArrayList<>();
        this.numberOfHugsInDay = 0;
    }

    public void setXp(int xp) {
        if (!isFlowerGifted) {
            this.xp = Math.max(0, Math.min(xp, 599));
        } else {
            if (!areMarried) {
                this.xp = Math.max(0, Math.min(xp, 999));
            } else {
                this.xp = Math.max(0, Math.min(xp, 1200));
            }
        }

        if (this.xp < 100) {
            friendshipLevel = 0;
        } else if (this.xp < 300) {
            friendshipLevel = 1;
        } else if (this.xp < 600) {
            friendshipLevel = 2;
        } else if (this.xp < 1000) {
            friendshipLevel = 3;
        } else {
            friendshipLevel = 4;
        }
    }

    public void setAreMarried(boolean areMarried) {
        this.areMarried = areMarried;
    }

    public boolean areMarried() {
        return areMarried;
    }

    public int getXp() {
        return xp;
    }
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private int getMaxXpToLevelUp() {
        return (this.friendshipLevel + 1) * 100;
    }

    public void addTalk(String message, Player player) {
        this.talkHistory.add(player.getName() + ": " + message);
    }

    public ArrayList<String> getTalkHistory() {
        return talkHistory;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public ArrayList<Gift> getGifs() {
        return gifs;
    }

    public void addGift(Gift gift) {
        this.gifs.add(gift);
    }

    public void setFlowerGifted(boolean flowerGifted) {
        isFlowerGifted = flowerGifted;
    }

    public void increaseNumberOfHugsInDay() {
        this.numberOfHugsInDay++;
    }

    public int getNumberOfHugsInDay() {
        return numberOfHugsInDay;
    }
}
