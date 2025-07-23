package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FarmingController {
    ActionMenuView view;

    public FarmingController(ActionMenuView view) {
        this.view = view;
    }

    public void plant(String seed, String dir, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Tile tile = getTileByDir(dir);
        if (tile == null) {
            this.view.showMessage("Tile doesn't exist.");
            return;
        }
        plantSeedInTile(seed, tile);
    }

    private void plantSeedInTile(String seedName, Tile tile) {
        Game currentGame = App.getCurrentGame();
        PlayerMap currentPlayerMap = currentGame.getPlayerMap(App.getCurrentGame().getCurrentPlayer());
        PlayerMap currentPlayerSpouseMap = currentGame.getPlayerMap(App.getCurrentGame().getCurrentPlayer().getSpouse());

        if (!currentPlayerMap.hasTile(tile) && !currentPlayerSpouseMap.hasTile(tile)) {
            this.view.showMessage("You can't plant in this tile!");
            return;
        }

        if (!tile.isEmpty()) {
            this.view.showMessage("Tile is not empty.");
            return;
        }

        if (!tile.isPlowed()) {
            this.view.showMessage("Tile is not plowed.");
            return;
        }

        // find seed
        ItemDefinition seedDefinition = getSeedByName(seedName);
        if (seedDefinition == null) {
            this.view.showMessage("Seed not found!");
            return;
        }

        Player currentPlayer = currentGame.getCurrentPlayer();
        if (!currentPlayer.getInventory().hasItem(seedDefinition.getId())) {
            this.view.showMessage("You don't have this seed.");
            return;
        }

        ItemInstance plant;

        if (seedDefinition.getId() == ItemIDs.mixed_seeds) {
            plant = new ItemInstance(Objects.requireNonNull(getPlantByMixedSeed(seedDefinition)));
        } else {
            plant = new ItemInstance(Objects.requireNonNull(getPlantBySeed(seedDefinition)));
        }

        tile.setItem(plant);
        tile.setDayPassedFromPlant(0);

        // decrease energy
        currentPlayer.decreaseEnergy(5);

        // increase ability
        currentPlayer.getAbilities().increaseFarmingAbility();
        this.view.showMessage(String.format("You have planted a %s successfully.", plant.getDefinition().getDisplayName()));

        // check can become giant
        if (((String) plant.getDefinition().getAttribute(ItemAttributes.canBecomeGiant)).equalsIgnoreCase("false")) {
            return;
        }
        int tileY = (int) tile.getPosition().getY();
        int tileX = (int) tile.getPosition().getX();
        GameMap map = currentGame.getGameMap();

        ArrayList<Tile> square;
        int[] X = {-1, 1};
        int[] Y = {-1, 1};
        for (int y : Y) {
            for (int x : X) {
                square = getSquare(tile, map.getTile(tileY + y, tileX + x));
                if (canBecomeGiant(square, plant.getDefinition())) {
                    makeGiantPlant(square);
                    System.out.println("You've just made a giant plant!");
                }
            }
        }
    }

    private ArrayList<Tile> getSquare(Tile center, Tile corner) {
        int centerY = (int) center.getPosition().getY();
        int centerX = (int) center.getPosition().getX();
        int cornerY = (int) corner.getPosition().getY();
        int cornerX = (int) corner.getPosition().getX();

        GameMap map = App.getCurrentGame().getGameMap();

        ArrayList<Tile> tiles = new ArrayList<>();

        if ((cornerY < centerY) &&
            (cornerX < centerX)) {
            for (int y = cornerY; y <= centerY; y++) {
                for (int x = cornerX; x <= centerX; x++) {
                    tiles.add(map.getTile(y, x));
                }
            }
        } else if ((cornerY < centerY) &&
                   (cornerX > centerX)) {
            for (int y = cornerY; y <= centerY; y++) {
                for (int x = centerX; x <= cornerX; x++) {
                    tiles.add(map.getTile(y, x));
                }
            }
        } else if ((cornerY > centerY) &&
                    (cornerX < centerX)) {
            for (int y = centerY; y <= cornerY; y++) {
                for (int x = cornerX; x <= centerX; x++) {
                    tiles.add(map.getTile(y, x));
                }
            }
        } else if ((cornerY > centerY) &&
                    (cornerX > centerX)) {
            for (int y = centerY; y <= cornerY; y++) {
                for (int x = centerX; x <= cornerX; x++) {
                    tiles.add(map.getTile(y, x));
                }
            }
        }

        return tiles;
    }

    private boolean canBecomeGiant(ArrayList<Tile> tiles, ItemDefinition plant) {
        for (Tile tile : tiles) {
            if (tile == null) return false;
            if (tile.getItem().getDefinition() != plant) return false;
        }
        return true;
    }

    private void makeGiantPlant(ArrayList<Tile> tiles) {
        int maxDaysPassedFromPlanting = 0;
        for (Tile tile : tiles) {
            tile.setGiantPlant(true);
            tile.setGiantGroup(tiles);
            if (tile.getDayPassedFromPlant() > maxDaysPassedFromPlanting) {
                maxDaysPassedFromPlanting = tile.getDayPassedFromPlant();
            }
        }

        for (Tile tile : tiles) tile.setDayPassedFromPlant(maxDaysPassedFromPlanting);
    }

    private Tile getTileByDir(String dir) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        int tileY, tileX;
        switch (dir.toLowerCase()) {
            case "up":
                tileY = (int) currentPlayer.getPosition().getY() - 1;
                tileX = (int) currentPlayer.getPosition().getX();
                break;
            case "down":
                tileY = (int) currentPlayer.getPosition().getY() + 1;
                tileX = (int) currentPlayer.getPosition().getX();
                break;
            case "left":
                tileY = (int) currentPlayer.getPosition().getY();
                tileX = (int) currentPlayer.getPosition().getX() - 1;
                break;
            case "right":
                tileY = (int) currentPlayer.getPosition().getY();
                tileX = (int) currentPlayer.getPosition().getX() + 1;
                break;
            case "up right":
                tileY = (int) currentPlayer.getPosition().getY() - 1;
                tileX = (int) currentPlayer.getPosition().getX() + 1;
                break;
            case "up left":
                tileY = (int) currentPlayer.getPosition().getY() - 1;
                tileX = (int) currentPlayer.getPosition().getX() - 1;
                break;
            case "down right":
                tileY = (int) currentPlayer.getPosition().getY() + 1;
                tileX = (int) currentPlayer.getPosition().getX() + 1;
                break;
            case "down left":
                tileY = (int) currentPlayer.getPosition().getY() + 1;
                tileX = (int) currentPlayer.getPosition().getX() - 1;
                break;
            default:
                this.view.showMessage("Please enter a valid direction: up, down, left and right");
                return null;
        }

        Tile tile;
        try {
            tile = App.getCurrentGame().getGameMap().getTile(tileY, tileX);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return tile;
    }

    public void showPlant(String yStr, String xStr, Game game) {
        int y, x;
        try {
            y = Integer.parseInt(yStr);
            x = Integer.parseInt(xStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid position.");
            return;
        }

        GameMap map = App.getCurrentGame().getGameMap();
        Tile tile = map.getTile(y, x);
        if (tile == null) {
            this.view.showMessage("Tile doesn't exist");
            return;
        }

        PlayerMap currentPlayerMap = game.getPlayerMap(game.getCurrentPlayer());
        PlayerMap currentPlayerSpouseMap = game.getPlayerMap(game.getCurrentPlayer().getSpouse());

        if (!currentPlayerMap.hasTile(tile) && !currentPlayerSpouseMap.hasTile(tile)) {
            this.view.showMessage("You don't have access to this tile.");
            return;
        }

        ItemInstance item = tile.getItem();
        ItemDefinition itemDefinition = item.getDefinition();
        String name;
        String isWateredToday;
        String isFertilized;
        int timeRemains = -1;

       if (item.getDefinition().getType() == ItemType.all_crops) {
            name = item.getDefinition().getDisplayName();
            isWateredToday = tile.isWatered() ? "is watered today" : "is not watered today";
            isFertilized = tile.isFertilized() ? "is fertilized" : "is not fertilized";
            timeRemains = ((int) item.getAttribute(ItemAttributes.totalHarvestTime)) - tile.getDayPassedFromPlant();
        } else {
            this.view.showMessage("Tile doesn't have any plant!");
            return;
        }

        String output = "Tile plant description:\n" +
                "Name: " + name + "\n" +
                "Source: " + itemDefinition.getAttribute(ItemAttributes.source) + "\n" +
                "Stages: " + ((ArrayList<Integer>) itemDefinition.getAttribute(ItemAttributes.stages))
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-")) + "\n" +
                "Total Harvest Time: " + itemDefinition.getAttribute(ItemAttributes.totalHarvestTime) + "\n" +
                "One Time: " + itemDefinition.getAttribute(ItemAttributes.oneTime) + "\n" +
                "Regrowth Time: " + (((int) itemDefinition.getAttribute(ItemAttributes.regrowthTime) == -1
                ? ""
                : (itemDefinition.getAttribute(ItemAttributes.regrowthTime)) + "\n") +
                "Base sell price: " + itemDefinition.getAttribute(ItemAttributes.baseSellPrice) + "\n" +
                "Is Edible: " + itemDefinition.getAttribute(ItemAttributes.isEdible) + "\n" +
                "Can Become Giant: " + itemDefinition.getAttribute(ItemAttributes.canBecomeGiant) + "\n" +
                (timeRemains <= 0 ? "is ready to harvest!" : timeRemains + " days remains to harvest.") + "\n" +
                isWateredToday + "\n" + isFertilized + "\n");
        this.view.showMessage(output);
    }

    public void fertilize(String fertilizer, String dir) {
        Game game = App.getCurrentGame();

        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Tile tile = getTileByDir(dir);
        if (tile == null) {
            this.view.showMessage("Tile doesn't exist.");
            return;
        }

        ItemDefinition fertilizerDefinition = App.getItemDefinition(fertilizer);
        if (fertilizerDefinition == null) {
            this.view.showMessage("Fertilizer doesn't exist.");
            return;
        }
        if (!((fertilizerDefinition.getId() == ItemIDs.speed_gro)
            || (fertilizerDefinition.getId() == ItemIDs.grass_starter)
            || (fertilizerDefinition.getId() == ItemIDs.basic_retaining_soil)
            || (fertilizerDefinition.getId() == ItemIDs.quality_retaining_soil)
            || (fertilizerDefinition.getId() == ItemIDs.deluxe_retaining_soil))) {
            this.view.showMessage("This is not a fertilizer!");
            return;
        }
        fertilizeTileWithFertilizer(tile, fertilizerDefinition);
    }

    private void fertilizeTileWithFertilizer(Tile tile, ItemDefinition fertilizerDefinition) {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        PlayerMap currentPlayerMap = game.getPlayerMap(App.getCurrentGame().getCurrentPlayer());
        PlayerMap currentPlayerSpouseMap= game.getPlayerMap(App.getCurrentGame().getCurrentPlayer().getSpouse());

        if (!currentPlayerMap.hasTile(tile) && !currentPlayerSpouseMap.hasTile(tile)) {
            this.view.showMessage("You can't fertilize this tile.");
            return;
        }

        ItemInstance tileItem = tile.getItem();
        if (tileItem.getDefinition().getType() != ItemType.all_crops) {
            this.view.showMessage("Tile doesn't have any plant to fertilize.");
            return;
        }

        if (tile.isFertilized()) {
            this.view.showMessage("Tile is fertilized already.");
            return;
        }

        Inventory inventory = player.getInventory();
        if (!inventory.hasItem(fertilizerDefinition.getId())) {
            this.view.showMessage("You don't have this fertilizer.");
            return;
        }

        ItemInstance fertilizer = inventory.getItem(fertilizerDefinition.getId());
        if (fertilizer == null) {
            this.view.showMessage("Unknown error. FarmingController -> line 354");
            return;
        }


        tile.setFertilized(true);
        if (tile.hasGiantPlant()) {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setFertilized(true);
            }
        }

        if (fertilizer.getDefinition().getId() == ItemIDs.deluxe_retaining_soil) {
            tile.setWatered(true);
        }

        this.view.showMessage("You have fertilized the plant successfully.");
        player.decreaseEnergy(5);
        player.getAbilities().increaseFarmingAbility();

        if (tile.hasGiantPlant()) {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setFertilized(true);
            }
        }
    }

    private ItemDefinition getSeedByName(String name) {
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if (itemDefinition.getType() == ItemType.foraging_seeds) {
                if (name.equalsIgnoreCase(itemDefinition.getDisplayName())) {
                    return itemDefinition;
                }
            }
        }
        return null;
    }


    private ItemDefinition getPlantBySeed(ItemDefinition seed) {
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if((itemDefinition.getType() == ItemType.all_crops) &&
               ((String)itemDefinition.getAttribute(ItemAttributes.source)).equalsIgnoreCase(seed.getId().name())) {
                return itemDefinition;
            }
        }
        return null;
    }

    private ItemDefinition getPlantByMixedSeed(ItemDefinition mixedSeed) {
        Game game = App.getCurrentGame();
        String currentSeason = game.getDateTime().getSeason().getSeason();

        ItemDefinition[] sprintOptions = {
                App.getItemDefinition("cauliflower"),
                App.getItemDefinition("parsnip"),
                App.getItemDefinition("potato"),
                App.getItemDefinition("blue_jazz"),
                App.getItemDefinition("tulip")
        };
        ItemDefinition[] summerOptions = {
                App.getItemDefinition("corn"),
                App.getItemDefinition("hot_pepper"),
                App.getItemDefinition("radish"),
                App.getItemDefinition("wheat"),
                App.getItemDefinition("poppy"),
                App.getItemDefinition("sunflower"),
                App.getItemDefinition("summer_spangle")
        };
        ItemDefinition[] fallOptions = {
                App.getItemDefinition("artichoke"),
                App.getItemDefinition("corn"),
                App.getItemDefinition("eggplant"),
                App.getItemDefinition("pumpkin"),
                App.getItemDefinition("sunflower"),
                App.getItemDefinition("fairy_rose"),
        };
        ItemDefinition[] winterOptions = {
                App.getItemDefinition("powdermelon")
        };

        int randomNumber;
        switch (currentSeason) {
            case "spring":
                randomNumber = GenerateRandomNumber.generateRandomNumber(0, sprintOptions.length - 1);
                return sprintOptions[randomNumber];
            case "summer":
                randomNumber = GenerateRandomNumber.generateRandomNumber(0, summerOptions.length - 1);
                return summerOptions[randomNumber];
            case "fall":
                randomNumber = GenerateRandomNumber.generateRandomNumber(0, fallOptions.length - 1);
                return fallOptions[randomNumber];
            case "winter":
                randomNumber = GenerateRandomNumber.generateRandomNumber(0, winterOptions.length - 1);
                return winterOptions[randomNumber];
            default:
                this.view.showMessage("Unknown error. FarminContoller -> line 450");
                return null;
        }
    }

    public void howMuchWater(){
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        for(Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()){
            ArrayList<ItemInstance> items = entry.getValue();
            for (ItemInstance item : items) {
                if(item.getDefinition().getId().name().contains("watering_can")){
                    int water = (int) item.getAttribute(ItemAttributes.durability);
                    view.showMessage("You have " + water + " in your watering can");
                    return;
                }
            }
        }
    }
}
