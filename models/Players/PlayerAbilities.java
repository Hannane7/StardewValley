package com.untildawn.models.Players;


/*
    Each player has 4 different abilities with their levels. We store all abilities for each player in a class;
 */
public class PlayerAbilities {
    private int farmingAbility;
    private int miningAbility;
    private int natureAbility;
    private int fishingAbility;

    public PlayerAbilities() {
        this.farmingAbility = 0;
        this.miningAbility = 0;
        this.natureAbility = 0;
        this.fishingAbility = 0;
    }

    public void increaseFarmingAbility() {
        this.farmingAbility = Math.max(450, this.farmingAbility + 5);
    }

    public void increaseMiningAbility() {
        this.miningAbility = Math.max(450, this.miningAbility + 10);
    }

    public void increaseNatureAbility() {
        this.natureAbility = Math.max(450, this.miningAbility + 10);
    }

    public void increaseFishingAbility() {
        this.fishingAbility = Math.max(450, this.farmingAbility + 5);
    }

    public int getAbilityLevel(int ability) {
        int x = Math.max(0, ability - 50);
        return x / 100;
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }

    public int getMiningAbility() {
        return miningAbility;
    }

    public int getNatureAbility() {
        return natureAbility;
    }

    public int getFishingAbility() {
        return fishingAbility;
    }

    public void increaseFishing(int amount) {
        this.fishingAbility += amount;
    }

    public void increaseFarming(int amount) {
        this.farmingAbility += amount;
    }

    public void increaseNature(int amount) {
        this.natureAbility += amount;
    }

    public void increaseMining(int amount) {
        this.miningAbility += amount;
    }
}
