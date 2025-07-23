package com.untildawn.models.MapElements;


//import edu.stanford.nlp.parser.lexparser.Item;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.Enums.MapConsts.FarmElementsPosition;
import com.untildawn.Enums.MapConsts.MapSizes;
import com.untildawn.models.App;
import com.untildawn.models.Items.ItemInstance;


import java.util.ArrayList;
import java.util.Objects;

public class MapDesigner {
    public static void designTopLeft(GameMap map) {
        map.getTile(FarmElementsPosition.TopLeftFarm.COTTAGE.getY(),
                FarmElementsPosition.TopLeftFarm.COTTAGE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage"))));
        map.getTile(FarmElementsPosition.TopLeftFarm.GREENHOUSE.getY(),
                FarmElementsPosition.TopLeftFarm.GREENHOUSE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("greenhouse"))));
        map.getTile(FarmElementsPosition.TopLeftFarm.QUARRY.getY(),
                FarmElementsPosition.TopLeftFarm.QUARRY.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.TopLeftFarm.LAKE_1.getY(),
                FarmElementsPosition.TopLeftFarm.LAKE_1.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
        map.getTile(FarmElementsPosition.TopLeftFarm.LAKE_2.getY(),
                FarmElementsPosition.TopLeftFarm.LAKE_2.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
    }
    public static void designTopRight(GameMap map) {
        map.getTile(FarmElementsPosition.TopRightFarm.COTTAGE.getY(),
                FarmElementsPosition.TopRightFarm.COTTAGE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage"))));
        map.getTile(FarmElementsPosition.TopRightFarm.GREENHOUSE.getY(),
                FarmElementsPosition.TopRightFarm.GREENHOUSE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("greenhouse"))));
        map.getTile(FarmElementsPosition.TopRightFarm.QUARRY_1.getY(),
                FarmElementsPosition.TopRightFarm.QUARRY_1.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.TopRightFarm.QUARRY_2.getY(),
                FarmElementsPosition.TopRightFarm.QUARRY_2.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.TopRightFarm.LAKE.getY(),
                FarmElementsPosition.TopRightFarm.LAKE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
    }
    public static void designBottomLeft(GameMap map) {
        map.getTile(FarmElementsPosition.BottomLeftFarm.COTTAGE.getY(),
                FarmElementsPosition.BottomLeftFarm.COTTAGE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage"))));
        map.getTile(FarmElementsPosition.BottomLeftFarm.GREENHOUSE.getY(),
                FarmElementsPosition.BottomLeftFarm.GREENHOUSE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("greenhouse"))));
        map.getTile(FarmElementsPosition.BottomLeftFarm.QUARRY_1.getY(),
                FarmElementsPosition.BottomLeftFarm.QUARRY_1.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.BottomLeftFarm.QUARRY_2.getY(),
                FarmElementsPosition.BottomLeftFarm.QUARRY_2.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.BottomLeftFarm.LAKE.getY(),
                FarmElementsPosition.BottomLeftFarm.LAKE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
    }
    public static void designBottomRight(GameMap map) {
        map.getTile(FarmElementsPosition.BottomRightFarm.COTTAGE.getY(),
                FarmElementsPosition.BottomRightFarm.COTTAGE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage"))));
        map.getTile(FarmElementsPosition.BottomRightFarm.GREENHOUSE.getY(),
                FarmElementsPosition.BottomRightFarm.GREENHOUSE.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("greenhouse"))));
        map.getTile(FarmElementsPosition.BottomRightFarm.QUARRY.getY(),
                FarmElementsPosition.BottomRightFarm.QUARRY.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
        map.getTile(FarmElementsPosition.BottomRightFarm.LAKE_1.getY(),
                FarmElementsPosition.BottomRightFarm.LAKE_1.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
        map.getTile(FarmElementsPosition.BottomRightFarm.LAKE_2.getY(),
                FarmElementsPosition.BottomRightFarm.LAKE_2.getX()).setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));

    }

    public static void addColor(GameMap map) {
        int[] yStarts = {0, 60};
        int[] xStarts = {0, 60};

        for (Integer yStart : yStarts) {
            for (Integer xStart : xStarts) {
                for (int y = yStart; y < yStart + MapSizes.FARM_ROWS.getSize(); y++) {
                    for (int x = xStart; x < xStart + MapSizes.FARM_COLS.getSize(); x++) {
                        Tile tile = map.getTile(y, x);
                        tile.setForGroundColor(AnsiColors.BLACK);
                        tile.setBackGroundColor(AnsiColors.LIGHT_GREEN);

                        if (tile.getItem().getDefinition().getId() == ItemIDs.lake) {
                            tile.setBackGroundColor(AnsiColors.BLUE);
                        }
                        if (tile.getItem().getDefinition().getId() == ItemIDs.cottage) {
                            tile.setBackGroundColor(AnsiColors.BROWN);
                            tile.setForGroundColor(AnsiColors.BLACK);

                        }
                        if (tile.getItem().getDefinition().getId() == ItemIDs.quarry) {
                            tile.setBackGroundColor(AnsiColors.BLACK);
                            tile.setForGroundColor(AnsiColors.WHITE);
                        }
                        if (tile.getItem().getDefinition().getId() == ItemIDs.greenhouse){
                            tile.setBackGroundColor(AnsiColors.GREEN);

                        }
                    }
                }
            }
        }

        map.getTile(30, 59).setBackGroundColor(AnsiColors.BLACK);
    }

    public static void addBorders(GameMap map) {
        int[] X = {30, 59};
        for (int x : X) {
            for (int y = 0; y < MapSizes.MAP_ROWS.getSize(); y++) {
                Tile tile = map.getTile(y, x);
                tile.setBackGroundColor(AnsiColors.LIGHT_BLUE);
                tile.setForGroundColor(AnsiColors.LIGHT_BLUE);
            }
        }

        int[] Y = {30, 59};
        for (int y : Y) {
            for (int x = 0; x < MapSizes.MAP_COLS.getSize(); x++) {
                Tile tile = map.getTile(y, x);
                tile.setBackGroundColor(AnsiColors.LIGHT_BLUE);
                tile.setForGroundColor(AnsiColors.LIGHT_BLUE);
            }
        }
    }

    public static void add2D(GameMap map) {
        Tile[][] tiles = map.getMap();
        ArrayList<Tile> lakeTiles = new ArrayList<>();
        ArrayList<Tile> cottageTiles = new ArrayList<>();
        ArrayList<Tile> quarryTiles = new ArrayList<>();
        ArrayList<Tile> greenhouseTiles = new ArrayList<>();
        ArrayList<Tile> shopTiles = new ArrayList<>();
        ArrayList<Tile> homeTiles = new ArrayList<>();

        for (int y = 0; y < MapSizes.MAP_ROWS.getSize(); y++) {
            for (int x = 0; x < MapSizes.MAP_COLS.getSize(); x++) {
                Tile tile = tiles[y][x];
                ItemInstance item = tile.getItem();
                if (item.getDefinition().getId() == ItemIDs.lake) {
                    lakeTiles.add(tile);
                } else if (item.getDefinition().getId() == ItemIDs.cottage) {
                    cottageTiles.add(tile);
                } else if (item.getDefinition().getId() == ItemIDs.quarry) {
                    quarryTiles.add(tile);
                } else if (item.getDefinition().getId() == ItemIDs.greenhouse) {
                    greenhouseTiles.add(tile);
                } else if (item.getDefinition().getId() == ItemIDs.shop) {
                    shopTiles.add(tile);
                } else if (item.getDefinition().getId() == ItemIDs.home) {
                    homeTiles.add(tile);
                }
            }
        }

        for (Tile tile : lakeTiles) drawLake(tile, map);
        for (Tile tile : cottageTiles) drawCottage(tile, map);
        for (Tile tile : quarryTiles) drawQuarry(tile, map);
        for (Tile tile : greenhouseTiles) drawGreenHouse(tile, map);
        for (Tile tile : shopTiles) drawShop(tile, map);
        for (Tile tile : homeTiles) drawHome(tile, map);
    }

    private static void drawLake(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> lakeTiles = new ArrayList<>();

        for (int i = y - 2; i <= y + 2; i++) {
            for (int j = x - 2; j <= x + 2; j++) {
                lakeTiles.add(map.getTile(i, j));
            }
        }

        lakeTiles.add(map.getTile(y - 2, x));
        lakeTiles.add(map.getTile(y - 2, x + 1));
        lakeTiles.add(map.getTile(y - 3, x + 2));
        lakeTiles.add(map.getTile(y - 1, x + 3));
        lakeTiles.add(map.getTile(y + 1, x + 3));
        lakeTiles.add(map.getTile(y, x + 3));
        lakeTiles.add(map.getTile(y + 3, x - 3));
        lakeTiles.add(map.getTile(y + 3, x - 3));
        lakeTiles.add(map.getTile(y + 3, x - 2));
        lakeTiles.add(map.getTile(y - 2, x - 3));
        lakeTiles.add(map.getTile(y - 1, x - 2));


        for (Tile lakeTile : lakeTiles) {
            lakeTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("lake"))));
            lakeTile.setForGroundColor(AnsiColors.BLUE);
            lakeTile.setBackGroundColor(AnsiColors.BLUE);
        }
    }
    private static void drawCottage(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> cottageTiles = new ArrayList<>();

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                cottageTiles.add(map.getTile(i, j));
            }
        }

        for (Tile cottageTile: cottageTiles) {
            cottageTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("cottage"))));
            cottageTile.setForGroundColor(AnsiColors.BROWN);
            cottageTile.setBackGroundColor(AnsiColors.BROWN);
        }
    }
    private static void drawQuarry(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> quarryTiles = new ArrayList<>();

        for (int i = x - 2; i <= x + 2; i++) {
            quarryTiles.add(map.getTile(y, i));
        }

        for (Tile cottageTile: quarryTiles) {
            cottageTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("quarry"))));
            cottageTile.setForGroundColor(AnsiColors.GREY);
            cottageTile.setBackGroundColor(AnsiColors.GREY);
        }
    }
    private static void drawGreenHouse(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> greenhouseTiles = new ArrayList<>();

        for (int i = y - 2; i <= y + 2; i++) {
            for (int j = x - 3; j <= x + 3; j++) {
                greenhouseTiles.add(map.getTile(i, j));
            }
        }

        for (Tile cottageTile: greenhouseTiles) {
            cottageTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("greenhouse"))));
            cottageTile.setForGroundColor(AnsiColors.GREEN);
            cottageTile.setBackGroundColor(AnsiColors.GREEN);
        }
    }
    private static void drawShop(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> shopTiles = new ArrayList<>();

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                shopTiles.add(map.getTile(i, j));
            }
        }

        for (Tile cottageTile: shopTiles) {
            cottageTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("shop"))));
            cottageTile.setForGroundColor(AnsiColors.YELLOW);
            cottageTile.setBackGroundColor(AnsiColors.YELLOW);
        }
    }
    private static void drawHome(Tile tile, GameMap map) {
        int y = (int) tile.getPosition().getY();
        int x = (int) tile.getPosition().getX();

        ArrayList<Tile> homeTiles = new ArrayList<>();

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                homeTiles.add(map.getTile(i, j));
            }
        }

        for (Tile cottageTile: homeTiles) {
            cottageTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("home"))));
            cottageTile.setForGroundColor(AnsiColors.BLUE);
            cottageTile.setBackGroundColor(AnsiColors.BLUE);
        }

    }
}
