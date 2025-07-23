package com.untildawn.models.MapElements;


import com.untildawn.Enums.MapConsts.MapSizes;

public class GameMap {
    private Tile[][] map;

    public GameMap() {
        this.map = new Tile[MapSizes.MAP_ROWS.getSize()][MapSizes.MAP_COLS.getSize()];
    }

    public Tile[][] getMap() {
        return map;
    }

    public void addTile(Tile tile) {
        map[(int) tile.getPosition().getY()][(int) tile.getPosition().getX()] = tile;
    }
    public Tile getTile(int y, int x) {
        try {
            return map[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

}
