package com.untildawn.controllers.UpdateControllers;

import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.MapConsts.MapSizes;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Tile;
import java.util.ArrayList;
import java.util.Objects;

public class spawnRandom {

    public static void spawnRandomElements() {
        spawn(60, 100, "wood");
        spawn(60, 100, "rock");
        spawn(60, 100, "fiber");
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if (itemDefinition.getType() == ItemType.tree) {
                spawn(5, 15, itemDefinition.getId().name());
            }
        }

    }
    public static void spawn(int min, int max, String id) {
        Game currentGame = App.getCurrentGame();
        ArrayList<PlayerMap> playerMaps = currentGame.getPlayerMaps();
        for (PlayerMap playerMap : playerMaps) {
            int totalNumber = GenerateRandomNumber.generateRandomNumber(min, max);
            for (int i = 0; i < totalNumber; i++) {
                Tile tile;
                do {
                    int y = GenerateRandomNumber.generateRandomNumber(0, MapSizes.FARM_ROWS.getSize() - 1);
                    int x = GenerateRandomNumber.generateRandomNumber(0, MapSizes.FARM_COLS.getSize() - 1);
                    tile = playerMap.getTile(y, x);
                } while (!tile.isEmpty());
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(id))));
            }
        }
    }
}
