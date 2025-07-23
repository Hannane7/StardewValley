package com.untildawn.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.Items.ItemDefinition;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
    Saves some important data during runtime, like current menu, current players, and ...
 */
public abstract class App {
    private static Game currentGame = null;
    private static ArrayList<ItemDefinition> itemDefinitions = new ArrayList<>();
    private static ArrayList<ItemDefinition> shopItemDefinitions = new ArrayList<>();
    private static Menu currentMenu = Menus.PreGameMenus.LOGIN_MENU; // temporary
    private static Map<String, User> users = new LinkedHashMap<>();
    private static User currentUser = null;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu menu) {
        App.currentMenu = menu;
    }

    public static void addUser(String username, User user) {
        App.users.put(username, user);
    }
    public static void setUsers(ArrayList<User> users) {
        for (User user : users) App.users.put(user.getUsername(), user);
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static void setItemDefinitions(ArrayList<ItemDefinition> itemDefinitions) {
        App.itemDefinitions = itemDefinitions;
    }

    public static ItemDefinition getItemDefinition(String itemId) {
        for (ItemDefinition itemDefinition : App.itemDefinitions) {
            if (itemDefinition.getId().name().equals(itemId)) {
                return itemDefinition;
            }
        }
        return null;
    }
    public static ItemDefinition getItemDefinitionByName(String itemName) {
        for (ItemDefinition itemDefinition : App.itemDefinitions) {
            if(itemDefinition.getDisplayName().equalsIgnoreCase(itemName)) {
                return itemDefinition;
            }
        }
        return null;
    }

    public static ArrayList<ItemDefinition> getItemDefinitions() {
        return App.itemDefinitions;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }
    public static TiledMapTile getTileByType(TiledMap map, String typeName) {
        for (TiledMapTileSet tileSet : map.getTileSets()) {
            for (TiledMapTile tile : tileSet) {
                if (tile.getProperties().containsKey("type") &&
                    tile.getProperties().get("type", String.class).equals(typeName)) {
                    return tile;
                }
            }
        }
        return null;
    }

}
