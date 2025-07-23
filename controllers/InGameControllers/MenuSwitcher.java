package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.MapElements.Shop;
import com.untildawn.models.Players.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MenuSwitcher {


    public String switchMenu(Matcher matcher) {
        String menu = matcher.group("menu");
        switch (menu.toLowerCase()) {
            case "action", "action menu", "1" -> {
                App.setCurrentMenu(Menus.InGameMenus.ACTION_MENU);
                return "you are now in action menu!\n";
            }
            case "cooking", "cooking menu", "2" -> {
                App.setCurrentMenu(Menus.InGameMenus.COOKING_MENU);
                return "you are now in cooking menu!\n";
            }
            case "crafting", "crafting menu", "3" -> {
                App.setCurrentMenu(Menus.InGameMenus.CRAFTING_MENU);
                return "you are now in crafting menu!\n";
            }
            case "inventory", "inventory menu", "4" -> {
                App.setCurrentMenu(Menus.InGameMenus.INVENTORY_MENU);
                return "you are now in inventory menu!\n";
            }
            case "home", "home menu", "5" -> {
                App.setCurrentMenu(Menus.InGameMenus.HOME_MENU);
                return "you are now in home menu!\n";
            }
            case "shop", "shop menu", "6" -> {
                return setShop();
            }
            case "trade", "trade menu", "7" -> {
                App.setCurrentMenu(Menus.InGameMenus.TRADE_MENU);
                return "you are now in trade menu!\n";
            }
            case "exit", "exit menu", "8" -> {
                App.setCurrentMenu(Menus.InGameMenus.EXIT_MENU);
                return "you are now in exit menu!\n";
            }
            default -> {
                return "Invalid menu!\n";
            }
        }
    }


    private String setShop() {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        int playerY = (int) player.getPosition().getY();
        int playerX = (int) player.getPosition().getX();

        ArrayList<Shop> shops = game.getShops();

        for (Shop shop : shops) {
            int shopY = (int) shop.getPosition().getY();
            int shopX = (int) shop.getPosition().getX();

            if (shopY >= playerY - 1 && shopY <= playerY + 1
                && shopX >= playerX - 1 && shopX <= playerX + 1) {
                ShopMenuController.setShop(shop); // TODO: must be changed
                App.setCurrentMenu(Menus.InGameMenus.SHOP_MENU);
                return String.format("You are now in %s menu.", shop.getShopName());
            }
        }

        return "You are not close to any shop.";
    }
}
