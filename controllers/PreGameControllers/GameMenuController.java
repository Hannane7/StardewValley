package com.untildawn.controllers.PreGameControllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemDisplay;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.MapConsts.AnsiColors;
import com.untildawn.Enums.MapConsts.MapSizes;
import com.untildawn.Main;
import com.untildawn.controllers.UpdateControllers.UpdateForaging;
import com.untildawn.controllers.UpdateControllers.spawnRandom;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemLoader;
import com.untildawn.models.Items.ShopItemLoader;
import com.untildawn.models.MapElements.*;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.PrepareMap;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.NPCs.MakeNPC;
import com.untildawn.models.NPCs.MakeRelation;
import com.untildawn.models.NPCs.MakeShops;
import com.untildawn.models.Players.MakePlayerRelations;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Question;
import com.untildawn.models.User;
import com.untildawn.views.PreGameMenus.GameMenuView;
import com.untildawn.views.PreGameMenus.MainMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


import java.util.*;

public class GameMenuController {
    GameMenuView view;
    ArrayList<Player> gamePlayers = new ArrayList<>();
    int counter = 0;
    boolean hasLoaded = false;
    GameMap newGameMap;
    ArrayList<PlayerMap> farms;
    Map<Player, PlayerMap> playerMaps = new LinkedHashMap<>();
    Map<Integer, PlayerMap> farmsWithNumber = new LinkedHashMap<>();

    public void setView(GameMenuView view) {
        this.view = view;
    }

    public void setListener() {
        if (!hasLoaded) {
            ItemLoader.loadItems();
            hasLoaded = true;
            newGameMap = PrepareMap.prepareMap();
            farms = PrepareMap.loadPlayerMaps(newGameMap);
        }
        if (gamePlayers.isEmpty()) {
            User user = App.getCurrentUser();
            gamePlayers.add(new Player(user, user.getUsername(), user.getGender(), new Position(0, 0)));
        }
        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(Menus.PreGameMenus.MAIN_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
            }
        });
        view.getNewGameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.setNewGameClicked(true);
            }
        });
        view.getAddPlayerButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUsernameField().getText();
                getUsersForNewGame(username);
                view.show();
            }
        });
        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.setNewGameClicked(false);
                gamePlayers.clear();
            }
        });
        view.getSelectMapButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.setInMapSelection(true);
            }
        });
    }

    public String makeNewGame() {
        // load items
        ItemLoader.loadItems();
        // prepare map
        GameMap newGameMap = PrepareMap.prepareMap();
//        ArrayList<PlayerMap> farms = PrepareMap.makePlayerMaps(newGameMap);


        if (gamePlayers == null) return "New game canceled, You are now in game menu.\n";

        ArrayList<Player> gamePlayers = new ArrayList<>();

//        for (User user : gamePlayers) {
//            user.setInAnyGame(true);
//            Player newPlayer = new Player(user, user.getName(), user.getGender(), new Position(0, 0));
//            gamePlayers.add(newPlayer);
//        }
        Scanner sc = new Scanner(System.in);
        Map<Player, PlayerMap> playerMaps = getPlayerMaps(sc, gamePlayers, newGameMap, farms);
        for (Map.Entry<Player, PlayerMap> entry : playerMaps.entrySet()) {
            Player player = entry.getKey();
            PlayerMap map = entry.getValue();
//            int cottageY = (int) map.getCottage().getTile().getPosition().getY();
//            int cottageX = (int) map.getCottage().getTile().getPosition().getX();
//            Position cottagePosition = new Position(cottageY, cottageX);
//            player.setCottagePosition(cottagePosition);
//            player.setPosition(cottagePosition);
        }

        Game newGame = new Game(gamePlayers, playerMaps, gamePlayers.get(0), newGameMap);
        App.setCurrentGame(newGame);

        spawnRandom.spawnRandomElements();
        UpdateForaging.updateForaging(newGame);
        // make NPCs
        MakeNPC.makeNPC();
        // make shops
        MakeShops.makeShops();
        ShopItemLoader.loadShopItems();
        // make relations
        MakeRelation.makeRelations(newGame);
        // make player relations
        MakePlayerRelations.makePlayerRelations();

        App.setCurrentMenu(Menus.InGameMenus.ACTION_MENU);
        return "Game created successfully!\n";
    }

    private void getUsersForNewGame(String input) {
        User user = App.getUser(input);
        if (input.isEmpty()) {
            view.setErrorMessage("Please enter a valid username.\n");
        } else if (!App.userExists(input)) {
            view.setErrorMessage("User doesn't exist. Try again.\n");
        } else if (isItInGamePlayers(user)) {
            view.setErrorMessage("User " + input + " is already added!\n");
        } else if (user.isInAnyGame()) {
            view.setErrorMessage("User " + input + " is in another game right now! Try another one.\n");
        } else {
            view.setErrorMessage("User " + input + " added successfully. Enter the next username.\n");
            gamePlayers.add(new Player(user, user.getName(), user.getGender(), new Position(0, 0)));
        }
    }

    private Map<Player, PlayerMap> getPlayerMaps(Scanner sc, ArrayList<Player> players, GameMap gameMap, ArrayList<PlayerMap> farms) {
        Map<Player, PlayerMap> playerMaps = new LinkedHashMap<>();
        Map<Integer, PlayerMap> farmsWithNumber = new LinkedHashMap<>();

        int number = 1;
        for (PlayerMap playerMap : farms) {
            farmsWithNumber.put(number++, playerMap);
        }

        printMaps(farmsWithNumber);

        view.setErrorMessage("Choose your map.\n");
        int counter = 0;
        while (true) {
            view.setErrorMessage("Player %s: " + players.get(counter).getName());
            int mapNumber;
            try {
                mapNumber = Integer.parseInt(sc.nextLine());
                view.setErrorMessage("\n");
            } catch (NumberFormatException e) {
                view.setErrorMessage("\nPlease enter a valid number.\n");
                continue;
            }

            if (mapNumber < 1 || mapNumber > 4) {
                view.setErrorMessage("Please enter a number between 1 and 4.\n");
                continue;
            }

            PlayerMap playerMap = farmsWithNumber.get(mapNumber);
            if (playerMaps.containsValue(playerMap)) {
                view.setErrorMessage("Selected map is already chosen by another player.\n");
                continue;
            }

            playerMaps.put(players.get(counter), playerMap);
            players.get(counter).setPlayerMap(playerMap);
            counter++;
            if (counter == players.size()) {
                return playerMaps;
            }
        }
    }

    private static void printMaps(Map<Integer, PlayerMap> maps) {
        for (Map.Entry<Integer, PlayerMap> entry : maps.entrySet()) {
            int number = entry.getKey();
            PlayerMap map = entry.getValue();
            System.out.printf("Map number %d:\n", number);
            for (int y = 0; y < MapSizes.FARM_ROWS.getSize(); y++) {
                for (int x = 0; x < MapSizes.FARM_COLS.getSize(); x++) {
                    Tile tile = map.getTile(y, x);
                    ItemType itemType = tile.getItem().getDefinition().getType();
                    String symbol = ItemDisplay.getDisplayByType(itemType);
                    System.out.print(AnsiColors.wrap(symbol + " ", tile.getForGroundColor(), tile.getBackGroundColor()));
                }
                System.out.println();
            }
            System.out.print("\n\n");
        }
    }

    public static void changeMenu(Menu menu, String menuName) {
        try {
            TerminalAnimation.loadingAnimation("redirecting to " + menuName + " menu");
        } catch (InterruptedException e) {

        }
        App.setCurrentMenu(menu);
        System.out.println("Your are now in " + menuName);
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public void createMapSelectListener(int mapNumber) {
        int number = 1;
        for (PlayerMap playerMap : farms) {
            farmsWithNumber.put(number++, playerMap);
        }

        if (mapNumber < 1 || mapNumber > 4) {
            view.setErrorMessage("Please enter a number between 1 and 4.\n");
            return;
        }

        PlayerMap playerMap = farmsWithNumber.get(mapNumber);
        if (playerMaps.containsValue(playerMap)) {
            view.setErrorMessage("Selected map is already chosen by another player.\n");
            return;
        }

        playerMaps.put(gamePlayers.get(counter), playerMap);
        gamePlayers.get(counter).setPlayerMap(playerMap);
        counter++;
        if (counter == gamePlayers.size()) {
            for (Map.Entry<Player, PlayerMap> entry : playerMaps.entrySet()) {
                Player player = entry.getKey();
                PlayerMap map = entry.getValue();
                int cottageY = (int) map.getCottage().getTile()[2].getPosition().getY();
                int cottageX = (int) map.getCottage().getTile()[2].getPosition().getX();
                Position cottagePosition = new Position(cottageY, cottageX);
                player.setCottagePosition(cottagePosition);
                player.setPosition(cottagePosition);
            }

            Game newGame = new Game(gamePlayers, playerMaps, gamePlayers.get(0), newGameMap);
//            PrepareMap.loadMapTilesFromTmx(newGame.getMap(), newGame);
            PrepareMap.loadSecondLayer(newGame.getMap(), newGameMap);
            App.setCurrentGame(newGame);

            spawnRandom.spawnRandomElements();
            UpdateForaging.updateForaging(newGame);
            // make NPCs
            MakeNPC.makeNPC();
            // make shops
            MakeShops.makeShops();
            ShopItemLoader.loadShopItems();
            // make relations
            MakeRelation.makeRelations(newGame);
            // make player relations
            MakePlayerRelations.makePlayerRelations();
            Main.getMain().setScreen(Menus.InGameMenus.GAME_VIEW.getMenu());
            App.setCurrentMenu(Menus.InGameMenus.GAME_VIEW);

        }
    }

    private boolean isItInGamePlayers(User user) {
        for (Player gamePlayer : gamePlayers) {
            if (gamePlayer.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<PlayerMap> getFarms() {
        return farms;
    }

    public GameMap getNewGameMap() {
        return newGameMap;
    }

    public void setNewGameMap(GameMap newGameMap) {
        this.newGameMap = newGameMap;
    }

    public void setFarms(ArrayList<PlayerMap> farms) {
        this.farms = farms;
    }
}

