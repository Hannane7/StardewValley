package com.untildawn.models.MapElements;

import com.fasterxml.jackson.databind.type.ClassKey;
import com.untildawn.models.Animals.Barn;
import com.untildawn.models.Animals.Coop;


public class PlayerMap {
    private Tile[][] map;
    private GreenHouse greenHouse;
    private Cottage cottage;
    private Quarry[] quarries;
    private Lake[] lakes;
    private Position startPosition;
    private Position endPosition;
    private Barn barn;
    private Coop coop;
    private boolean hasBarn;
    private boolean hasCoop;
    public PlayerMap(Tile[][] map
            , Tile[] cottageTile
            , Tile[][] lakeTiles
            , Tile[][]quarryTiles
            , Position startPosition
            , Position endPosition) {
        this.map = map;
        // make farm greenhouse
        // make farm cottage
        this.cottage = new Cottage(cottageTile);
        // make farm quarries
        try {
            this.quarries = new Quarry[2];
            this.quarries[0] = new Quarry(quarryTiles[0]);
            this.quarries[1] = new Quarry(quarryTiles[1]);
        } catch (Exception e) {

        }
        // make farm lakes
        try {
            this.lakes = new Lake[2];
            this.lakes[0] = new Lake(lakeTiles[0]);
            this.lakes[1] = new Lake(lakeTiles[1]);
        } catch (Exception e) {

        }
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.hasBarn = false;
        this.hasCoop = false;
    }

    public Tile getTile(int y, int x) {
        return map[y][x];
    }

    public Tile[][] getMap() {
        return map;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public Lake[] getLakes() {
        return lakes;
    }

    public Quarry[] getQuarries() {
        return quarries;
    }

    public boolean hasTile(Tile tile) {
        for (Tile[] rows : this.map) {
            for (Tile rowTile : rows) {
                if (rowTile == tile) {
                    return true;
                }
            }
        }
        return false;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Barn getBarn() {
        return barn;
    }

    public void setBarn(Barn barn) {
        this.barn = barn;
        this.hasBarn = true;
    }

    public Coop getCoop() {
        return coop;
    }

    public void setCoop(Coop coop) {
        this.coop = coop;
        this.hasCoop = true;
    }

    public boolean hasBarn() {
        return hasBarn;
    }

    public boolean hasCoop() {
        return hasCoop;
    }
}
