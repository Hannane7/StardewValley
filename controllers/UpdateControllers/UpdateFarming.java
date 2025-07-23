package com.untildawn.controllers.UpdateControllers;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Tile;


import java.util.ArrayList;
import java.util.Objects;

public class UpdateFarming {
    public static void updateAllCrops(Game game) {
        Tile[][] tiles = game.getGameMap().getMap();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                ItemInstance item = tile.getItem();
                if (item.getDefinition().getType() == ItemType.all_crops) {
                    if (tile.isWatered())
                        tile.setDayPassedFromPlant(tile.getDayPassedFromPlant() + 1);
                    tile.setWatered(false);
                    ArrayList<Integer> stages = (ArrayList<Integer>) item.getAttribute(ItemAttributes.stages);
                    if (stages == null) {
                        return;
                    }
                    for (int k = 0; k < stages.size(); k++) {
                        if (stages.get(k) != 0) {
                            stages.set(k, stages.get(k) - 1);
                        }
                    }
                    item.setAttribute(ItemAttributes.stages, stages);
                } else if (item.getDefinition().getType() == ItemType.tree) {
                    if (!tile.isAttackedByCrow() || tile.isWatered()) {

                        ItemInstance fruit = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(
                                (String) item.getDefinition().getAttribute(ItemAttributes.fruit)
                        )));

                        tile.addFruit(fruit);
                    }
                }
            }
        }
    }
}
