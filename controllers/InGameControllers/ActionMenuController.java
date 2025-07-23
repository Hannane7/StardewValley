package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemDisplay;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.controllers.InGameControllers.walk.Walk;
import com.untildawn.controllers.UpdateControllers.ArtisanUpdate;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.NPCs.NPC;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.PlayerRelation;
import com.untildawn.models.Players.Wallet;
import com.untildawn.views.InGameMenus.ActionMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class ActionMenuController {
    ActionMenuView view;

    public ActionMenuController(ActionMenuView view) {
        this.view = view;
    }

    public void walk(String yString, String xString) {
        Game currentGame = App.getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();

        if (!currentGame.isPlayerActive(currentPlayer)) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        int y, x;
        try {
            y = Integer.parseInt(yString);
            x = Integer.parseInt(xString);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please Enter valid Y and X");
            return;
        }
        // check whether the player can go to the destination or not

        GameMap map = currentGame.getGameMap();
        int currentPlayerY = (int) currentPlayer.getPosition().getY();
        int currentPlayerX = (int) currentPlayer.getPosition().getX();
        Tile start = map.getTile(currentPlayerY, currentPlayerX);

        Tile goal;
        try {
            goal = map.getTile(y, x);
        } catch (ArrayIndexOutOfBoundsException e) {
            this.view.showMessage("Please enter a valid position.");
            return;
        }

        for (Player player : currentGame.getPlayers()) {
            PlayerMap playerMap = currentGame.getPlayerMap(player);
            PlayerRelation relation = currentGame.getPlayerRelation(currentPlayer, player);
            if (player != currentPlayer
                    && !relation.areMarried()
                    && playerMap.hasTile(goal)) {
                this.view.showMessage("You don't have access to this place.");
                return;
            }
        }

        List<Tile> path = Walk.findPath(start, goal, map);
        if (path == null) {
            this.view.showMessage("No path found.");
            return;
        }

        int energyCost = Walk.calculateWalkEnergyCost(path);

        String input = this.view.prompt(String.format("The best path's energy cost is %d. (Your current energy: %d)\nDo you want to go to the destination?\n" +
                "1. Yes\n" +
                "2. No\n" +
                "3. Walk until my energy runs out.", energyCost, currentPlayer.getEnergy()));
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid number");
            return;
        }

        int playerEnergy = currentPlayer.getEnergy();

        if (number == 1) {
            if (playerEnergy < energyCost) {
                this.view.showMessage("You don't have enough energy.");
            } else {
                try {
                    TerminalAnimation.loadingAnimation("Walking");
                } catch (InterruptedException e) {
                    this.view.showMessage("Problem walking to the destination. Please try again.");
                    return;
                }
                Walk.walkToDestination(energyCost, y, x);
                this.view.showMessage("You are now in destination!");
            }
        } else if (number == 2) {
            this.view.showMessage("Cancelled.");
        } else if (number == 3) {
            Position finalPosition = Walk.walkUntilEnergyRunsOut(path);
            this.view.showMessage(String.format("You final position is y = %d | x = %d", finalPosition.getY(), finalPosition.getX()));
        } else {
            this.view.showMessage("Invalid command");
        }
    }

    public void buildGreenhouse() {
        Game currentGame = App.getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();
        if (!currentGame.isPlayerActive(currentPlayer)) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Inventory playerInventory = currentPlayer.getInventory();

        int playerWood = playerInventory.getItemAmount(ItemIDs.wood);
        int playerCoin = currentPlayer.getWallet().getCoin();

        if (playerWood < 500) {
            view.showMessage("You don't have enough wood!");
            return;
        } else if (playerCoin < 1000) {
            view.showMessage("You don't have enough coin!");
            return;
        } else {
            currentGame.getPlayerMap(currentPlayer).getGreenHouse().repair();

            Wallet wallet = currentPlayer.getWallet();
            wallet.setCoin(wallet.getCoin() - 1000);
            currentPlayer.getInventory().trashItem(ItemIDs.wood, 500);
        }
        view.showMessage("Greenhouse has been repaired!");
    }

    public void nextTurn() {
        Game currentGame = App.getCurrentGame();
        currentGame.getCurrentPlayer().setEnergyPerTurn(50);
        Player nextPlayer = currentGame.getNextPlayer();
        if (nextPlayer == currentGame.getPlayers().get(0)) {
            currentGame.updateByHour(false);
        }
        if(currentGame.hasAllPlayersFainted()) {
            currentGame.getDateTime().updateTimeByDay(1);
            view.showMessage("All players have fainted\nDay is now " + currentGame.getDateTime().getDay() + "!");
            return;
        }
        currentGame.setCurrentPlayer(nextPlayer);
        while (nextPlayer.isFainted()) {
            nextPlayer = currentGame.getNextPlayer();
        }
        view.showMessage(nextPlayer.getName() + "'s turn!");
    }

    public void cheatAdvanceTime(Matcher matcher, Game game) {
        String timeStr = matcher.group("hours");
        int time;
        try {
            time = Integer.parseInt(timeStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid hour!");
            return;
        }
        if (time < 0) {
            this.view.showMessage("time must be a positive integer!");
        }
        int newHour = game.getDateTime().updateTimeByHour(time);
        ArtisanUpdate.artisanWithHour(time);
        this.view.showMessage("time is now " + newHour + "!");
        for (int i = 0; i < time; i++) {
            game.updateByHour(true);
        }
        game.getDateTime().setHour(newHour);
    }

    public void cheatAdvanceDate(Matcher matcher, Game game) {
        String timeStr = matcher.group("day");
        int time;
        try {
            time = Integer.parseInt(timeStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid day!");
            return;
        }
        if (time < 0) {
            this.view.showMessage("time must be a positive integer!");
        }
        ArtisanUpdate.artisanWithDay(time);
        int newDay = game.getDateTime().getDay() + time;
        view.showMessage("day is now " + newDay % 21 + "!");
        for (int i = 0; i < time; i++) {
            game.updateByDay(true);
        }
    }

    public void weatherForecast(Game game) {
        view.showMessage("Tomorrow's weather is " + game.getTomorrowWeather().
                getCurrentWeather().name().toLowerCase() + "!");
    }

    public void cheatWeather(Matcher matcher, Game game) {
        String weather = matcher.group("type");
        for (WeatherStates value : WeatherStates.values()) {
            if (weather.equalsIgnoreCase(value.name())) {
                game.getTomorrowWeather().setCurrentWeather(value);
                view.showMessage("Tomorrow's weather has been set to " + value.name().toLowerCase() + "!");
                return;
            }
        }
        view.showMessage("Please enter a valid weather!");
    }

    public void printMap(String xStr, String yStr, String sizeStr) {
        Game game = App.getCurrentGame();
        int x, y, size;
        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid position!");
            return;
        }
        try {
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid size!");
            return;
        }
        Position position = new Position(y, x);
        getMapBySize(game, position, size);
    }

    public void getMapBySize(Game game, Position position, int size) {
        GameMap map = game.getGameMap();

        // show entire map
        String[][] mapArray = new String[2 * size][2 * size];
        for (int i = (int) position.getY() - size; i < position.getY() + size; i++) {
            for (int j = (int) position.getX() - size; j < position.getX() + size; j++) {
                try {
                    Tile tile = map.getTile(i, j);
                    ItemType type = tile.getItem().getDefinition().getType();
                    String symbol = ItemDisplay.getDisplayByType(type);
                    mapArray[i - (int) position.getY() + size][j - (int) position.getX() + size] =
                            AnsiColors.wrap(symbol + " ", tile.getForGroundColor(), tile.getBackGroundColor());
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }


        // show players
        ArrayList<Player> players = App.getCurrentGame().getPlayers();
        int playerNumber = 1;
        for (Player player : players) {
            int playerY = (int) player.getPosition().getY();
            int playerX = (int) player.getPosition().getX();

            if (playerY >= position.getY() - size && playerY < position.getY() + size) {
                if (playerX >= position.getX() - size && playerX < position.getX() + size) {
                    mapArray[playerY - (int) position.getY() + size][playerX - (int) position.getX() + size] =
                            AnsiColors.wrap(playerNumber + " ", AnsiColors.BLACK, AnsiColors.ORANGE);

                }
            }
            playerNumber++;
        }

        // show NPCs
        for (NPC npc : game.getNPCs()) {
            int NPcY = (int) npc.getPosition().getY();
            int NPcX = (int) npc.getPosition().getX();

            if (NPcY >= position.getY() - size && NPcY < position.getY() + size) {
                if (NPcX >= position.getX() - size && NPcX < position.getX() + size) {
                    mapArray[NPcY - (int) position.getY() + size][NPcX - (int) position.getX() + size] =
                            AnsiColors.wrap(npc.getName().toCharArray()[0] + " ", AnsiColors.BLACK, AnsiColors.PINK);

                }
                if (npc.getName().equalsIgnoreCase("Kian")) {
                    mapArray[NPcY - (int) position.getY() + size][NPcX - (int) position.getX() + size] =
                            AnsiColors.wrap(npc.getName().toCharArray()[0] + " ", AnsiColors.BLACK, AnsiColors.WHITE);
                }
            }
            playerNumber++;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 2 * size; i++) {
            for (int j = 0; j < 2 * size; j++) {
                output.append(mapArray[i][j]);
            }
            output.append("\n");
        }
        view.showMessage(output.toString());
    }

    public void helpReadingMap() {
        for (ItemDisplay value : ItemDisplay.values()) {
            view.showMessage(value.name() + " : " + value.getDisplay());
        }
    }

    public void cheatSetEnergy(Matcher matcher, Game game) {
        String energyStr = matcher.group("value");
        int energy;
        try {
            energy = Integer.parseInt(energyStr);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter a valid energy amount!");
            return;
        }
        if (energy < 0) {
            view.showMessage("energy must be a positive integer!");
            return;
        }
        if (energy > 200) {
        view.showMessage("energy must be a less than 200!");

        }
        game.getCurrentPlayer().setEnergy(energy);
        if(energy == 0) game.getCurrentPlayer().setFainted(true);
        view.showMessage("your energy has been set to " + energy + "!");
    }

    public void cheatRefillTurnEnergy() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.setEnergyPerTurn(50);
        this.view.showMessage("Your turn energy is now 50!");

    }

    public void energyUnlimited(Game game) {
        game.getCurrentPlayer().setEnergy(Integer.MAX_VALUE);
        view.showMessage("your energy is now unlimited:)");
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }



    public void craftInfo(Matcher matcher, Game game) {
        String name = matcher.group("craftName");
        ItemDefinition itemDefinition = null;
        for (ItemDefinition tmp : App.getItemDefinitions()) {
            if (tmp.getDisplayName().equalsIgnoreCase(name) && tmp.getType().equals(ItemType.all_crops)) {
                itemDefinition = tmp;
            }
        }
        if (itemDefinition == null) {
            view.showMessage("please select a crop!");
            return;
        }
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(itemDefinition.getDisplayName().toLowerCase()).append("\n");
        for (Map.Entry<ItemAttributes, Object> entry : itemDefinition.getBaseAttributes().entrySet()) {
            ItemAttributes itemAttributes = entry.getKey();
            Object object = entry.getValue();
            info.append(itemAttributes.toString()).append(": ").append(object.toString()).append("\n");
        }
        view.showMessage(info.toString());
    }

    public void artisanUse(Matcher matcher, Game game, String command) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String artisanName = matcher.group("artisanName").trim().toLowerCase();
        String itemName = matcher.group("item1Name").trim().toLowerCase();
        String ingredient = "";
        if (command.contains("-i"))
            ingredient = matcher.group("ingredient").trim().toLowerCase();
        Player player = game.getCurrentPlayer();
        switch (artisanName) {
            case "bee_house":
                ArtisanController.beeHouse(itemName, player);
                break;
            case "cheese_press":
                ArtisanController.cheesePress(itemName, ingredient, player);
                break;
            case "keg":
                ArtisanController.keg(itemName, ingredient, player);
                break;
            case "dehydrator":
                ArtisanController.dehydrator(itemName, player);
                break;
            case "charcoal_kiln":
                ArtisanController.charcoalKiln(itemName, player);
                break;
            case "loom":
                ArtisanController.loom(itemName, player);
                break;
            case "mayonnaise_machine":
                ArtisanController.mayonnaiseMachine(itemName, ingredient, player);
                break;
            case "oil_maker":
                ArtisanController.oilMaker(itemName, ingredient, player);
                break;
            case "preserves_jar":
                ArtisanController.preservesJar(itemName, ingredient, player);
                break;
            case "fish_smoker":
                ArtisanController.fishSmoker(itemName, ingredient, player);
                break;
            case "furnace":
                ArtisanController.furnace(itemName, ingredient, player);
                break;
            default:
                view.showMessage("please select a valid machine!");
                break;
        }
    }

    public void artisanGet(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String machineName = matcher.group("artisanName").trim().toLowerCase();
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        for (ItemInstance itemInstance : inventory.getArtisan()) {
            if (itemInstance.getAttribute(ItemAttributes.isReady).equals(true)
                    && itemInstance.getAttribute(ItemAttributes.machine).equals(machineName)) {
                if (!inventory.hasCapacity()) {
                    view.showMessage("Your back pack is full!");
                    break;
                } else {
                    inventory.addItem(itemInstance);
                    inventory.getArtisan().remove(itemInstance);
                }
            }
        }
    }

    public void cheatInfinityInventory() {
        Inventory inventory = App.getCurrentGame().getCurrentPlayer().getInventory();
        for (int i = 0; i < 1000; i++) {
            for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
                inventory.addItem(new ItemInstance(itemDefinition));
            }
        }

        this.view.showMessage("Your inventory has 1000 of everything!");
    }
}

