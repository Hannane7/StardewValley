package com.untildawn.models.MapElements;

public class GreenHouse {
    private Tile tile;
    private boolean isRepaired;
    public GreenHouse(Tile tile) {
        this.isRepaired = false;
        this.tile = tile;
    }
    public void repair() {
        this.isRepaired = true;
    }

}
