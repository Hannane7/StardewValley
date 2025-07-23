package com.untildawn.controllers.UpdateControllers;

import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class RandomEvents {
    public static void strikeLightning(Game game) {
        PlayerMap currentPlayerMap = game.getPlayerMap(game.getCurrentPlayer());
        Tile[][] tiles = currentPlayerMap.getMap();
        int[] tilesY = new int[3];
        int[] tilesX = new int[3];
        for (int i = 0; i < 3; i++) {
            tilesY[i] = GenerateRandomNumber.generateRandomNumber(0, 29);
            tilesX[i] = GenerateRandomNumber.generateRandomNumber(0, 29);
        }
        Tile[] tilesToStrike = new Tile[3];
        for (int i = 0; i < 3; i++) {
            tilesToStrike[i] = currentPlayerMap.getTile(tilesY[i], tilesX[i]);
        }

        for (Tile tile : tilesToStrike) {
            tile.strikeLightning();
        }
    }

    public static void strikeTile(Tile tile, Player player) {
        if (tile.getItem().getDefinition().getType().equals(ItemType.all_crops)) {
            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
            player.addMessage("Your tile(y : " + tile.getPosition().getY() +
                    ", x : " + tile.getPosition().getX() + ") has been struck by lightning and your plant is" +
                    "gone!");
        } else if (tile.getItem().getDefinition().getType().equals(ItemType.tree)
                || tile.getItem().getDefinition().getType().equals(ItemType.foraging_trees)) {
            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("coal"))));
            player.addMessage("Your tile(y : " + tile.getPosition().getY() +
                    ", x : " + tile.getPosition().getX() + ") has been struck by lightning and your tree is" +
                    "gone!");
        }
    }

    public static void crowAttack(Game game) {

        int attackRandomNumber = GenerateRandomNumber.generateRandomNumber(0, 3);
        if (attackRandomNumber != 1) return;

        Map<Player, PlayerMap> playerMaps = game.getPlayerAndPlayerMaps();

        for (Map.Entry<Player, PlayerMap> entry : playerMaps.entrySet()) {
            Player player = entry.getKey();
            PlayerMap playerMap = entry.getValue();
            Tile[][] tiles = playerMap.getMap();
            int counter = 0;
            ArrayList<Tile> tilesToAttack = new ArrayList<>();
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile.getItem().getDefinition().getType() == ItemType.all_crops
                            || tile.getItem().getDefinition().getType() == ItemType.tree) {
                        counter++;
                        tilesToAttack.add(tile);
                        if (counter == 16) {
                            int randomNumber = GenerateRandomNumber.generateRandomNumber(0, tilesToAttack.size() - 1);
                            Tile attackedTile = tilesToAttack.get(randomNumber);

                            counter = 0;
                            tilesToAttack = new ArrayList<>();
                            // check scarecrow effect
                            if (attackedTile.isProtectedByScareCrow()) {
                                continue;
                            }

                            if (attackedTile.getItem().getDefinition().getType() == ItemType.all_crops) {
                                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                                tile.setDayPassedFromPlant(0);

                                attackedTile.setAttackedByCrow(true);
                                String message = String.format("Your farm has been attacked by crows -> Tile y:%d x:%d",
                                        attackedTile.getPosition().getY(), attackedTile.getPosition().getX());
                                player.addMessage(message);
                            }
                        }
                    }
                }
            }
        }

    }
}
