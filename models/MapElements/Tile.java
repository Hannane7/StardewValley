package com.untildawn.models.MapElements;

import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.models.Items.ItemInstance;


import java.util.ArrayList;

/*
    Game map is consisted of many tiles, each of which has a position and an item to represent.
 */
public class Tile {
    private final Position position;
    private ItemInstance item;
    private AnsiColors forGroundColor;
    private AnsiColors backGroundColor;
    private boolean isPlowed;
    private int dayPassedFromPlant;
    private boolean isFertilized;
    private boolean isWatered;
    private ArrayList<ItemInstance> fish;
    private boolean hasGiantPlant;
    private ArrayList<Tile> giantGroup;
    private boolean isProtectedByScareCrow;
    private boolean isAttackedByCrow;
    private ItemInstance fruit;

    private int dayLeftFromPlant;
    public Tile(Position position, ItemInstance item) {
        this.position = position;
        this.item = item;
        this.isPlowed = false;
        this.isWatered = false;
        this.isFertilized = false;
        this.isProtectedByScareCrow = false;
        this.hasGiantPlant = false;
        this.isAttackedByCrow = false;
        fish = new ArrayList<>();
        this.giantGroup = new ArrayList<>();
        this.fruit = null;
        this.hasGiantPlant = false;
        fish = new ArrayList<>();
        this.giantGroup = new ArrayList<>();
    }

    public boolean getPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public void setItem(ItemInstance item) {
        this.item = item;
    }

    public ItemInstance getItem() {
        return item;
    }

    public Position getPosition() {
        return position;
    }

    public void setBackGroundColor(AnsiColors backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setForGroundColor(AnsiColors forGroundColor) {
        this.forGroundColor = forGroundColor;
    }

    public AnsiColors getBackGroundColor() {
        return backGroundColor;
    }

    public AnsiColors getForGroundColor() {
        return forGroundColor;
    }

    public boolean isEmpty() {
        return this.item.getDefinition().getId().name().equals("VOID");
    }

    public void strikeLightning() {
        //TODO: implement Lightning logic
    }

    public void setDayPassedFromPlant(int dayPassedFromPlant) {
        this.dayPassedFromPlant = dayPassedFromPlant;
    }

    public int getDayPassedFromPlant() {
        return dayPassedFromPlant;
    }

    public void fertilize() {
        this.isFertilized = true;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        this.isWatered = watered;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public boolean isFertilized() {
        return isFertilized;
    }
    public boolean hasGiantPlant() {
        return hasGiantPlant;
    }

    public void setGiantPlant(boolean hasGiantPlant) {
        this.hasGiantPlant = hasGiantPlant;
    }

    public void setGiantGroup(ArrayList<Tile> giantGroup) {
        this.giantGroup = giantGroup;
    }

    public ArrayList<Tile> getGiantGroup() {
        return giantGroup;
    }

    public boolean isProtectedByScareCrow() {
        return isProtectedByScareCrow;
    }

    public void setProtectedByScareCrow(boolean protectedByScareCrow) {
        isProtectedByScareCrow = protectedByScareCrow;
    }

    public boolean isAttackedByCrow() {
        return isAttackedByCrow;
    }

    public void setAttackedByCrow(boolean attackedByCrow) {
        isAttackedByCrow = attackedByCrow;
    }

    public boolean hasFruit() {
        return this.fruit != null;
    }

    public ItemInstance getFruit() {
        return fruit;
    }

    public void addFruit(ItemInstance fruit) {
        this.fruit = fruit;
    }

    public ArrayList<ItemInstance> getFish() {
        return fish;
    }

    public void setFish(ArrayList<ItemInstance> fish) {
        this.fish = fish;
    }



    public int getDayLeftFromPlant() {
        return dayLeftFromPlant;
    }

    public void setDayLeftFromPlant(int dayLeftFromPlant) {
        this.dayLeftFromPlant = dayLeftFromPlant;
    }
}
