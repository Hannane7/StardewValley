package com.untildawn.models.MapElements;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.Enums.MapConsts.FarmElementsPosition;
import com.untildawn.Enums.MapConsts.MapSizes;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PrepareMap {
    public static GameMap prepareMap() {
        GameMap map = new GameMap();
        for (int y = 0; y < MapSizes.MAP_ROWS.getSize(); y++) {
            for (int x = 0; x < MapSizes.MAP_COLS.getSize(); x++) {
                Tile newTile = new Tile(new Position(y, x), new ItemInstance(
                    Objects.requireNonNull(App.getItemDefinition("VOID"))));
                map.addTile(newTile);
            }
        }
        // add cottage, lake, quarry and ... to the map!
//        MapDesigner.designTopLeft(map);
//        MapDesigner.designTopRight(map);
//        MapDesigner.designBottomLeft(map);
//        MapDesigner.designBottomRight(map);

        return map;
    }

//    public static ArrayList<PlayerMap> makePlayerMaps(GameMap gameMap) {
//
//        int[][] starts = {{0, 0}, {0, 60}, {60, 0}, {60, 60}};
//
//        for (int y = 0; y < MapSizes.MAP_ROWS.getSize(); y++) {
//            for (int x = 0; x < MapSizes.MAP_COLS.getSize(); x++) {
//                if (gameMap.getTile(y, x).getBackGroundColor() == null) {
//                    gameMap.getTile(y, x).setBackGroundColor(AnsiColors.GREEN);
//                    gameMap.getTile(y, x).setForGroundColor(AnsiColors.BLACK);
//                }
//            }
//        }
//
//        for (int y = 30; y < 60; y++) {
//            for (int x = 30; x < 60; x++) {
//                gameMap.getTile(y, x).setBackGroundColor(AnsiColors.WHITE);
//                gameMap.getTile(y, x).setForGroundColor(AnsiColors.BLUE);
//            }
//        }
//
//
//        ArrayList<PlayerMap> playerMaps = new ArrayList<>();
//
//        // prepare greenhouse tiles
//        Tile topLeftGreenHouse = gameMap.getTile(FarmElementsPosition.TopLeftFarm.GREENHOUSE.getY(),
//            FarmElementsPosition.TopLeftFarm.GREENHOUSE.getX());
//        Tile topRightGreenHouse = gameMap.getTile(FarmElementsPosition.TopRightFarm.GREENHOUSE.getY(),
//            FarmElementsPosition.TopRightFarm.GREENHOUSE.getX());
//        Tile bottomLeftGreenHouse = gameMap.getTile(FarmElementsPosition.BottomLeftFarm.GREENHOUSE.getY(),
//            FarmElementsPosition.BottomLeftFarm.GREENHOUSE.getX());
//        Tile bottomRightGreenHouse = gameMap.getTile(FarmElementsPosition.BottomRightFarm.GREENHOUSE.getY(),
//            FarmElementsPosition.BottomRightFarm.GREENHOUSE.getX());
//        // prepare lake tiles
//        Tile[] topLeftLakes = new Tile[2];
//        topLeftLakes[0] = gameMap.getTile(FarmElementsPosition.TopLeftFarm.LAKE_1.getY(),
//            FarmElementsPosition.TopLeftFarm.LAKE_1.getX());
//        topLeftLakes[1] = gameMap.getTile(FarmElementsPosition.TopLeftFarm.LAKE_2.getY(),
//            FarmElementsPosition.TopLeftFarm.LAKE_2.getX());
//        Tile[] topRightLakes = new Tile[1];
//        topRightLakes[0] = gameMap.getTile(FarmElementsPosition.TopRightFarm.LAKE.getY(),
//            FarmElementsPosition.TopRightFarm.LAKE.getX());
//        Tile[] bottomLeftLakes = new Tile[1];
//        bottomLeftLakes[0] = gameMap.getTile(FarmElementsPosition.BottomLeftFarm.LAKE.getY(),
//            FarmElementsPosition.BottomLeftFarm.LAKE.getX());
//        Tile[] bottomRightLakes = new Tile[2];
//        bottomRightLakes[0] = gameMap.getTile(FarmElementsPosition.BottomRightFarm.LAKE_1.getY(),
//            FarmElementsPosition.BottomRightFarm.LAKE_1.getX());
//        bottomRightLakes[1] = gameMap.getTile(FarmElementsPosition.BottomRightFarm.LAKE_2.getY(),
//            FarmElementsPosition.BottomRightFarm.LAKE_2.getX());
//
//        // prepare cottage tiles
//        Tile topLeftCottage = gameMap.getTile(FarmElementsPosition.TopLeftFarm.COTTAGE.getY(),
//            FarmElementsPosition.TopLeftFarm.COTTAGE.getX());
//        Tile topRightCottage = gameMap.getTile(FarmElementsPosition.TopRightFarm.COTTAGE.getY(),
//            FarmElementsPosition.TopRightFarm.COTTAGE.getX());
//        Tile bottomLeftCottage = gameMap.getTile(FarmElementsPosition.BottomLeftFarm.COTTAGE.getY(),
//            FarmElementsPosition.BottomLeftFarm.COTTAGE.getX());
//        Tile bottomRightCottage = gameMap.getTile(FarmElementsPosition.BottomRightFarm.COTTAGE.getY(),
//            FarmElementsPosition.BottomRightFarm.COTTAGE.getX());
//
//        // prepare quarry tiles
//        Tile[] topLeftQuarries = new Tile[1];
//        topLeftQuarries[0] = gameMap.getTile(FarmElementsPosition.TopLeftFarm.QUARRY.getY(),
//            FarmElementsPosition.TopLeftFarm.QUARRY.getX());
//        Tile[] topRightQuarries = new Tile[2];
//        topRightQuarries[0] = gameMap.getTile(FarmElementsPosition.TopRightFarm.QUARRY_1.getY(),
//            FarmElementsPosition.TopRightFarm.QUARRY_1.getX());
//        topRightQuarries[1] = gameMap.getTile(FarmElementsPosition.TopRightFarm.QUARRY_2.getY(),
//            FarmElementsPosition.TopRightFarm.QUARRY_2.getX());
//        Tile[] bottomLeftQuarries = new Tile[2];
//        bottomLeftQuarries[0] = gameMap.getTile(FarmElementsPosition.BottomLeftFarm.QUARRY_1.getY(),
//            FarmElementsPosition.BottomLeftFarm.QUARRY_1.getX());
//        bottomLeftQuarries[1] = gameMap.getTile(FarmElementsPosition.BottomLeftFarm.QUARRY_2.getY(),
//            FarmElementsPosition.BottomLeftFarm.QUARRY_2.getX());
//        Tile[] bottomRightQuarries = new Tile[1];
//        bottomRightQuarries[0] = gameMap.getTile(FarmElementsPosition.BottomRightFarm.QUARRY.getY(),
//            FarmElementsPosition.BottomRightFarm.QUARRY.getX());
//
//
//        playerMaps.add(new PlayerMap(getTiles(gameMap, 1),
//            topLeftGreenHouse, topLeftCottage, topLeftLakes, topLeftQuarries, new Position(0, 0),
//            new Position(29, 29)));
//        playerMaps.add(new PlayerMap(getTiles(gameMap, 2),
//            topRightGreenHouse, topRightCottage, topRightLakes, topRightQuarries, new Position(0, 60),
//            new Position(29, 89)));
//        playerMaps.add(new PlayerMap(getTiles(gameMap, 3),
//            bottomLeftGreenHouse, bottomLeftCottage, bottomLeftLakes, bottomLeftQuarries, new Position(60, 0),
//            new Position(89, 29)));
//        playerMaps.add(new PlayerMap(getTiles(gameMap, 4),
//            bottomRightGreenHouse, bottomRightCottage, bottomRightLakes, bottomRightQuarries, new Position(60, 60),
//            new Position(89, 89)));
//
//        // prepare NPC houses
//
//        for (NPCConst.HomePositions homePositions : NPCConst.HomePositions.values()) {
//            Tile tile = gameMap.getTile(homePositions.getY(), homePositions.getX());
//            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("home"))));
//            tile.setForGroundColor(AnsiColors.BLACK);
//            tile.setBackGroundColor(AnsiColors.BLUE);
//        }
//
//        for (NPCConst.ShopPositions shopPositions : NPCConst.ShopPositions.values()) {
//            Tile tile = gameMap.getTile(shopPositions.getY(), shopPositions.getX());
//            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("shop"))));
//            tile.setForGroundColor(AnsiColors.BLACK);
//            tile.setBackGroundColor(AnsiColors.YELLOW);
//
//        }
//
//        MapDesigner.addColor(gameMap);
//        MapDesigner.addBorders(gameMap);
//        MapDesigner.add2D(gameMap);
//
//        return playerMaps;
//    }

    private static Tile[][] getTiles(GameMap gameMap, int mapNumber) {
        int yStart;
        int xStart;
        switch (mapNumber) {
            case 1:
                yStart = 0;
                xStart = 0;
                break;
            case 2:
                yStart = 0;
                xStart = 60;
                break;
            case 3:
                yStart = 60;
                xStart = 0;
                break;
            case 4:
                yStart = 60;
                xStart = 60;
                break;
            default:
                return null;
        }
        Tile[][] tiles = new Tile[MapSizes.FARM_ROWS.getSize()][MapSizes.FARM_COLS.getSize()];
        for (int y = yStart; y < MapSizes.FARM_ROWS.getSize() + yStart; y++) {
            for (int x = xStart; x < MapSizes.FARM_COLS.getSize() + xStart; x++) {
                tiles[y - yStart][x - xStart] = gameMap.getTile(y, x);
            }
        }
        return tiles;
    }

    public static void loadSecondLayer(TiledMap map, GameMap gameMap) {
        MapProperties props = map.getProperties();
        int width = props.get("width", Integer.class);
        int height = props.get("height", Integer.class);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null || cell.getTile() == null) continue;
                MapProperties tileProps = cell.getTile().getProperties();
                if (tileProps.containsKey("type")) {
                    String type = tileProps.get("type", String.class);
                    System.out.println("x: " + x + " y: " + y + " type: " + type);
                    setTileFromTMX(type, y, x, gameMap);
                }
            }
        }
    }

    private static void setTileFromTMX(String type, int y, int x, GameMap gameMap) {
        switch (type) {
            case "Cottage":
                ItemInstance item1 = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage")));
                gameMap.getTile(y, x).setItem(item1);
                break;
            case "Lake":
                ItemInstance item2 = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake")));
                gameMap.getTile(y, x).setItem(item2);
                break;
            case "Quarry":
                ItemInstance item3 = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry")));
                gameMap.getTile(y, x).setItem(item3);
                break;
            default:
                ItemInstance item4 = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID")));
                gameMap.getTile(y, x).setItem(item4);
                break;
        }
    }

    public static ArrayList<PlayerMap> loadPlayerMaps(GameMap gameMap) {
        ArrayList<PlayerMap> playerMaps = new ArrayList<>();

        Position topLeftCottagePosition = new Position(95, 25);
        Position[] topLeftLakePosition = new Position[]{new Position(0, 0), new Position(0, 0)};
        Position[] topLeftQuarryPosition = new Position[]{new Position(0, 0), new Position(0, 0)};


        Position topRightCottagePosition = new Position(0, 0);
        Position[] topRightLakePosition = new Position[]{new Position(0, 0), new Position(0, 0)};
        Position[] topRightQuarryPosition = new Position[]{new Position(0, 0), new Position(0, 0)};


        Position bottomLeftCottagePosition = new Position(0, 0);
        Position[] bottomLeftLakePosition = new Position[]{new Position(0, 0), new Position(0, 0)};
        Position[] bottomLeftQuarryPosition = new Position[]{new Position(0, 0), new Position(0, 0)};


        Position bottomRightCottagePosition = new Position(0, 0);
        Position[] bottomRightLakePosition = new Position[]{new Position(0, 0), new Position(0, 0)};
        Position[] bottomRightQuarryPosition = new Position[]{new Position(0, 0), new Position(0, 0)};

        playerMaps.add(createPlayerMap(gameMap, 2, 1, new int[]{3, 4}, 1,
            topLeftCottagePosition, topLeftLakePosition, topLeftQuarryPosition,
            new Position(68, 0), new Position(99, 29)));


        playerMaps.add(createPlayerMap(gameMap, 1, 1, new int[]{4}, 2,
            topRightCottagePosition, topRightLakePosition, topRightQuarryPosition,
            new Position(68, 70), new Position(99, 99)));


        playerMaps.add(createPlayerMap(gameMap, 1, 1, new int[]{4}, 3,
            bottomLeftCottagePosition, bottomLeftLakePosition, bottomLeftQuarryPosition,
            new Position(0, 0), new Position(29, 29)));


        playerMaps.add(createPlayerMap(gameMap, 2, 1, new int[]{3, 4}, 4,
            bottomRightCottagePosition, bottomRightLakePosition, bottomRightQuarryPosition,
            new Position(0, 70), new Position(29, 99)));

        return playerMaps;
    }

    private static PlayerMap createPlayerMap(GameMap gameMap,
                                             int numOfLakes,
                                             int numOfQuarries,
                                             int[] lakeSizes,
                                             int mapNumber,
                                             Position cottagePosition,
                                             Position[] lakePositions,
                                             Position[] quarryPositions,
                                             Position startPosition,
                                             Position endPosition) {
        Tile[] topLeftCottage = new Tile[25];
        Tile[][] topLeftLakes = new Tile[numOfLakes][lakeSizes[0] * lakeSizes[0]];
        Tile[][] topLeftQuarry = new Tile[numOfQuarries][4];


        int i = 0;
        for (int y = (int) cottagePosition.getY(); y < (int) cottagePosition.getY() + 5; y++) {
            for (int x = (int) cottagePosition.getX(); x < (int) cottagePosition.getX() + 5; x++) {
                topLeftCottage[i++] = gameMap.getTile(y, x);
            }
        }


        i = 0;
        for (int y = (int) quarryPositions[0].getY(); y < (int) quarryPositions[0].getY() + 2; y++) {
            for (int x = (int) quarryPositions[0].getX(); x < (int) quarryPositions[0].getX() + 2; x++) {
                topLeftQuarry[0][i++] = gameMap.getTile(y, x);
            }
        }
        if (numOfQuarries == 2) {
            i = 0;
            for (int y = (int) quarryPositions[1].getY(); y < (int) quarryPositions[1].getY() + 2; y++) {
                for (int x = (int) quarryPositions[1].getX(); x < (int) quarryPositions[1].getX() + 2; x++) {
                    topLeftQuarry[1][i++] = gameMap.getTile(y, x);
                }
            }
        }


        i = 0;
        for (int y = (int) lakePositions[0].getY(); y < (int) lakePositions[0].getY() + lakeSizes[0]; y++) {
            for (int x = (int) lakePositions[0].getX(); x < (int) lakePositions[0].getX() + lakeSizes[0]; x++) {
                topLeftLakes[0][i++] = gameMap.getTile(y, x);
            }
        }
        if (numOfLakes == 2) {
            topLeftLakes[1] = new Tile[lakeSizes[1] * lakeSizes[1]];
            i = 0;
            for (int y = (int) lakePositions[1].getY(); y < (int) lakePositions[1].getY() + lakeSizes[1]; y++) {
                for (int x = (int) lakePositions[1].getX(); x < (int) lakePositions[1].getX() + lakeSizes[1]; x++) {
                    topLeftLakes[1][i++] = gameMap.getTile(y, x);
                }
            }
        }
        return new PlayerMap(getTiles(gameMap, mapNumber), topLeftCottage, topLeftLakes, topLeftQuarry,
            startPosition, endPosition);

    }
}
