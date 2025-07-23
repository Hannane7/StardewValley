package com.untildawn.controllers.UpdateControllers;

import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Tile;


import java.util.ArrayList;
import java.util.List;

/*
    Logins to update the foraging items of the map every morning.
 */
public class UpdateForaging {
    public static void updateForaging(Game game) {
        List<ItemDefinition> foragingMinerals = new ArrayList<>();
        List<ItemDefinition> foragingCrops = new ArrayList<>();
        List<ItemDefinition> foragingSeeds = new ArrayList<>();
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if (itemDefinition.getType() == ItemType.foraging_minerals) {
                foragingMinerals.add(itemDefinition);
            } else if (itemDefinition.getType() == ItemType.foraging_crops) {
                foragingCrops.add(itemDefinition);
            } else if (itemDefinition.getType() == ItemType.foraging_seeds) {
                foragingSeeds.add(itemDefinition);
            }
        }

        spawnRandomForaging(foragingCrops, game);
        spawnRandomForaging(foragingMinerals, game);
        spawnRandomForaging(foragingSeeds, game);
    }
    public static void spawnRandomForaging(List<ItemDefinition> foragingDefinitions, Game game) {
        List<PlayerMap> playerMaps = game.getPlayerMaps();
        for (PlayerMap playerMap : playerMaps) {
            Tile[][] tiles = playerMap.getMap();
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile tile = tiles[i][j];
                    if (tile.isEmpty()) {
                        int randomNumber = GenerateRandomNumber.generateRandomNumber(0, 99);
                        if (randomNumber == 1) {
                            int itemDefinitionNumber = GenerateRandomNumber.generateRandomNumber(0, foragingDefinitions.size() - 1);
                            ItemDefinition itemDefinition = foragingDefinitions.get(itemDefinitionNumber);
                            tile.setItem(new ItemInstance(itemDefinition));
                            tile.setForGroundColor(AnsiColors.GREEN);
                        }
                    }
                }
            }
        }
    }

    public static void deleteForaging(Game game) {
        List<PlayerMap> playerMaps = game.getPlayerMaps();
        for (PlayerMap playerMap : playerMaps) {
            Tile[][] tiles = playerMap.getMap();
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile tile = tiles[i][j];
                    ItemType type = tile.getItem().getDefinition().getType();
                    if (type == ItemType.foraging_seeds
                            || type == ItemType.foraging_crops
                            || type == ItemType.foraging_minerals) {
                        tile.setItem(new ItemInstance(App.getItemDefinition("VOID")));
                    }

                }
            }
        }
    }
}
