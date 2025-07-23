package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.CraftingMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CraftingMenuController {
    CraftingMenu view;

    public CraftingMenuController(CraftingMenu view) {
        this.view = view;
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }

    public String craftingCraft(String itemName, Game game) {
        Player player = game.getCurrentPlayer();
        if(player.getEnergy() -2 <= 0){
            return "You can't craft because u will ghash.";
        }

        if (!player.getInventory().hasCapacity()) {
            return "Your backpack is full.";
        } else if (itemName.equalsIgnoreCase("Cherry Bomb")) {
            ItemDefinition definition = App.getItemDefinition("cherry_bomb");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int miningLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object mining = sourceMap.get("mining");
                if (mining instanceof Integer) {
                    miningLevel = (int) mining;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getMiningAbility()) < miningLevel) {
                return "Your mining level isn't enough.";
            }
            return createItem(game, definition, "cherry_bomb");
        } else if (itemName.equalsIgnoreCase("Bomb")) {
            ItemDefinition definition = App.getItemDefinition("bomb");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int miningLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object mining = sourceMap.get("mining");
                if (mining instanceof Integer) {
                    miningLevel = (int) mining;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getMiningAbility()) < miningLevel) {
                return "Your mining level isn't enough.";
            }
            return createItem(game, definition, "bomb");
        } else if (itemName.equalsIgnoreCase("Mega Bomb")) {
            ItemDefinition definition = App.getItemDefinition("mega_bomb");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int miningLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object mining = sourceMap.get("mining");
                if (mining instanceof Integer) {
                    miningLevel = (int) mining;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getMiningAbility()) < miningLevel) {
                return "Your mining level isn't enough.";
            }
            return createItem(game, definition, "mega_bomb");
        } else if (itemName.equalsIgnoreCase("Sprinkler")) {
            ItemDefinition definition = App.getItemDefinition("sprinkler");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "sprinkler");
        } else if (itemName.equalsIgnoreCase("Quality Sprinkler")) {
            ItemDefinition definition = App.getItemDefinition("quality_sprinkler");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "quality_sprinkler");
        } else if (itemName.equalsIgnoreCase("Iridium Sprinkler")) {
            ItemDefinition definition = App.getItemDefinition("iridium_sprinkler");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "iridium_sprinkler");
        } else if (itemName.equalsIgnoreCase("Charcoal Klin")) {
            ItemDefinition definition = App.getItemDefinition("charcoal_klin");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object foraging = sourceMap.get("foraging");
                if (foraging instanceof Integer) {
                    foragingLevel = (int) foraging;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getNatureAbility()) < foragingLevel) {
                return "Your foraging level isn't enough.";
            }
            return createItem(game, definition, "charcoal_klin");
        } else if (itemName.equalsIgnoreCase("Furnace")) {
            ItemDefinition definition = App.getItemDefinition("furnace");
            return createItem(game, definition, "furnace");
        } else if (itemName.equalsIgnoreCase("Scarecrow")) {
            ItemDefinition definition = App.getItemDefinition("scarecrow");
            return createItem(game, definition, "scarecrow");
        } else if (itemName.equalsIgnoreCase("Deluxe Scarecrow")) {
            ItemDefinition definition = App.getItemDefinition("deluxe_scarecrow");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "deluxe_scarecrow");
        } else if (itemName.equalsIgnoreCase("Bee House")) {
            ItemDefinition definition = App.getItemDefinition("bee_house");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "bee_house");
        } else if (itemName.equalsIgnoreCase("Cheese Press")) {
            ItemDefinition definition = App.getItemDefinition("cheese_press");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "cheese_press");
        } else if (itemName.equalsIgnoreCase("Keg")) {
            ItemDefinition definition = App.getItemDefinition("keg");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "keg");
        } else if (itemName.equalsIgnoreCase("Loom")) {
            ItemDefinition definition = App.getItemDefinition("loom");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "loom");
        } else if (itemName.equalsIgnoreCase("Oil Maker")) {
            ItemDefinition definition = App.getItemDefinition("oil_maker");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "oil_maker");
        } else if (itemName.equalsIgnoreCase("Preserves Jar")) {
            ItemDefinition definition = App.getItemDefinition("preserves_jar");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int farmingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    farmingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < farmingLevel) {
                return "Your farming level isn't enough.";
            }
            return createItem(game, definition, "preserves_jar");
        } else if (itemName.equalsIgnoreCase("Mayonnaise Machine")) {
            ItemDefinition definition = App.getItemDefinition("mayonnaise_machine");
            return createItem(game, definition, "mayonnaise_machine");
        } else if (itemName.equalsIgnoreCase("Dehydrator")) {
            ItemDefinition definition = App.getItemDefinition("dehydrator");
            if (player.getInventory().hasItem(App.getItemDefinition("dehydrator_recipe").getId())) {
                return createItem(game, definition, "dehydrator");
            } else {
                return "You don't have the recipe to create dehydrator.";
            }

        } else if (itemName.equalsIgnoreCase("Grass Starter")) {
            ItemDefinition definition = App.getItemDefinition("grass_starter");
            if (player.getInventory().hasItem(App.getItemDefinition("grass_starter_recipe").getId())) {
                return createItem(game, definition, "grass_starter");
            }
            return "You don't have the recipe to create grass starter.";
        } else if (itemName.equalsIgnoreCase("Fish Smoker")) {
            ItemDefinition definition = App.getItemDefinition("fish_smoker");
            if (player.getInventory().hasItem(App.getItemDefinition("fish_smoker_recipe").getId())) {
                return createItem(game, definition, "fish_smoker");
            } else {
                return "You don't have the recipe to create fish smoker.";
            }
        } else if (itemName.equalsIgnoreCase("Mystic Tree Seed")) {
            ItemDefinition definition = App.getItemDefinition("mystic_tree_seed");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object foraging = sourceMap.get("foraging");
                if (foraging instanceof Integer) {
                    foragingLevel = (int) foraging;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getNatureAbility()) < foragingLevel) {
                return "Your foraging level isn't enough.";
            }
            return createItem(game, definition, "mystic_tree_seed");
        } else {
            return "This crafting isn't available in the game.";
        }
    }

    public String cheatAddItem(String itemName, String Count, Game game) {
        if(!Count.matches("^\\d+$")){
            return "invalid number.";
        }
        int count = Integer.parseInt(Count);
        if ((App.getItemDefinitionByName(itemName)) == null) {
            return "Item not found.";
        }
        ItemIDs id = App.getItemDefinitionByName(itemName).getId();
        Player player = game.getCurrentPlayer();
        if (!player.getInventory().hasCapacity()) {
            return "Your backpack is full.";
        } else {
            for (int i = 0; i < count; i++) {
                ItemInstance item = new ItemInstance(App.getItemDefinition(id.toString()));
                player.getInventory().addItem(item);
            }
            return count + " x " + itemName + " added to backpack.";
        }
    }

    public String placeItem(String direction, Game game, String name) {// Example input
        if (!direction.matches("^(up|up right|right|down right|down|down left|left|up left)$")) {
            return ("Invalid direction");
        }
        if(App.getItemDefinitionByName(name) == null){
            return "Invalid name.";
        }
        ItemDefinition item = App.getItemDefinitionByName(name);
        if (!game.getCurrentPlayer().getInventory().hasItem(item.getId())) {
            return "You don't have " + name;
        } else {
            Player player = game.getCurrentPlayer();
            switch (direction) {
                case "up":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX()).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX());
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";
                    }
                case "up right":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX() + 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX() + 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "up left":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX() - 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() - 1, (int) player.getPosition().getX() - 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "right":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY(), (int) player.getPosition().getX() + 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY(), (int) player.getPosition().getX() + 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "down right":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX() + 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX() + 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "down":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX()).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX());
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "down left":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX() - 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY() + 1, (int) player.getPosition().getX() - 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
                case "left":
                    if (!game.getGameMap().getTile((int) player.getPosition().getY(), (int) player.getPosition().getX() - 1).
                        getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
                        return "You can't place the item.";
                    } else {
                        Tile tile = game.getGameMap().getTile((int) player.getPosition().getY(), (int)  player.getPosition().getX() - 1);
                        tile.setItem(new ItemInstance(App.getItemDefinitionByName(name)));
                        tile.getItem().setDroppedByPlayer(true);
                        game.getCurrentPlayer().getInventory().trashItem(item.getId(), 1);
                        int tileY = (int) tile.getPosition().getY();
                        int tileX = (int) tile.getPosition().getX();
                        if (item.getId() == ItemIDs.scarecrow) {
                            for (int y = tileY - 8; y <= tileY + 8; y++) {
                                for (int x = tileX - 8; x <= tileX + 8; x++) {
                                    //                 game.getGameMap().getTile(y, x).setProtectedByCrow(true);
                                }
                            }
                        } else if (item.getId() == ItemIDs.deluxe_scarecrow) {
                            for (int y = tileY - 12; y <= tileY + 12; y++) {
                                for (int x = tileX - 12; x <= tileX + 12; x++) {
                                    //                  game.getGameMap().getTile(y, x).setProtectedBy(true);
                                }
                            }
                        }
                        return "You placed the " + name + " on the map.";

                    }
            }


        }
        return "";
    }

    public String printRecipes(Game game) {
        Player player = game.getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (ItemDefinition item : App.getItemDefinitions()) {
            result.append(getIngredientsDescription(item));
        }
        for(ItemDefinition item:App.getItemDefinitions()){
            if(player.getInventory().hasItem(item.getId())){
                result.append(getIngredientsDescriptionRecipe(item));
            }
        }
        return result.toString();
    }
    private StringBuilder getIngredientsDescription(ItemDefinition definition) {
        StringBuilder result = new StringBuilder();

        // Check type is crafting or shopRecipe
        String type = definition.getType().toString().toLowerCase();
        if (type.equals("crafting") ) {
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();

            // Check if source exists and is NOT from shop
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            boolean sourceIsNotShop = true;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                sourceIsNotShop = (!sourceMap.containsKey("Pierre's General Store") && !sourceMap.containsKey("Fish Shop"));
            }

            // Proceed only if source is not from shop
            if (sourceIsNotShop) {
                Object ingredientsObj = baseAttributes.get(ItemAttributes.ingredients);
                if (ingredientsObj instanceof Map<?, ?> ingredientsMap) {
                    result.append("Ingredients for ").append(definition.getDisplayName()).append(":\n");
                    for (Map.Entry<?, ?> entry : ingredientsMap.entrySet()) {
                        String ingredientId = (String) entry.getKey();
                        Integer amount = (Integer) entry.getValue();
                        result.append("- ").append(ingredientId).append(": ").append(amount).append("\n");
                    }
                }
            }
        }

        return result;
    }
    private StringBuilder getIngredientsDescriptionRecipe(ItemDefinition definition) {
        StringBuilder result = new StringBuilder();

        // Check type is crafting or shopRecipe
        String type = definition.getType().toString().toLowerCase();
        if (type.equals("shop_recipe") ) {
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();

            // Check if source exists and is NOT from shop
            boolean sourceIsNotShop = true;

            // Proceed only if source is not from shop
            if (sourceIsNotShop) {
                Object ingredientsObj = baseAttributes.get(ItemAttributes.ingredients);
                if (ingredientsObj instanceof Map<?, ?> ingredientsMap) {
                    result.append("Ingredients for ").append(definition.getDisplayName()).append(":\n");
                    for (Map.Entry<?, ?> entry : ingredientsMap.entrySet()) {
                        String ingredientId = (String) entry.getKey();
                        Integer amount = (Integer) entry.getValue();
                        result.append("- ").append(ingredientId).append(": ").append(amount).append("\n");
                    }
                }
            }
        }

        return result;
    }


    //    private String createItem(Game game, ItemDefinition definition, String id) {
    //        game.getCurrentPlayer().reduceEnergyWhenCrafting(2);
    //        Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
    //        Object ingredientsObj = baseAttributes.get(ItemAttributes.ingredients);
    //        if (ingredientsObj instanceof Map<?, ?>) {
    //            Map<?, ?> ingredientsMap = (Map<?, ?>) ingredientsObj;
    //
    //            // Now safely cast to Map<String, Integer> if you're sure
    //            for (Map.Entry<?, ?> entry : ingredientsMap.entrySet()) {
    //                String ingredientName = (String) entry.getKey();
    //                Integer quantity = (Integer) entry.getValue();
    //                Inventory inventory = game.getCurrentPlayer().getInventory();
    //                for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry1 : inventory.getItems().entrySet()) {
    //                    for(ItemInstance item :entry1.getValue()) {
    //                        Integer value = item.getAttribute();
    //                        if (item.getDefinition().getDisplayName().equalsIgnoreCase(ingredientName)) {
    //                            if (value < quantity) {
    //                                return "You don't have enough " + item.getDefinition().getDisplayName() + ".";
    //                            }
    //                            inventory.dropItem(id, quantity);
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        ItemInstance item = new ItemInstance(App.getItemDefinition(id));
    //        game.getCurrentPlayer().getInventory().addItem(item);
    //        return id + " crafted successfully.";
    //    }
    private String createItem(Game game, ItemDefinition definition, String id) {
        game.getCurrentPlayer().reduceEnergyWhenCrafting(2);
        Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
        Object ingredientsObj = baseAttributes.get(ItemAttributes.ingredients);

        if (ingredientsObj instanceof Map<?, ?> ingredientsMap) {
            Inventory inventory = game.getCurrentPlayer().getInventory();

            // First, check if the player has enough of all ingredients
            for (Map.Entry<?, ?> entry : ingredientsMap.entrySet()) {
                String ingredientId = (String) entry.getKey();
                int requiredQty = (Integer) entry.getValue();
                int availableQty = 0;

                for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> invEntry : inventory.getItems().entrySet()) {
                    for (ItemInstance item : invEntry.getValue()) {
                        if (item.getDefinition().getId().toString().equalsIgnoreCase(ingredientId)) {
                            availableQty++;
                        }
                    }
                }

                if (availableQty < requiredQty) {
                    return "You don't have enough of " + ingredientId + ". Required: " + requiredQty + ", but you have: " + availableQty;
                }
            }

            // Then, remove the required ingredients from inventory
            for (Map.Entry<?, ?> entry : ingredientsMap.entrySet()) {
                String ingredientId = (String) entry.getKey();
                int toRemove = (Integer) entry.getValue();

                for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> invEntry : inventory.getItems().entrySet()) {
                    Iterator<ItemInstance> iterator = invEntry.getValue().iterator();
                    while (iterator.hasNext() && toRemove > 0) {
                        ItemInstance item = iterator.next();
                        if (item.getDefinition().getId().toString().equalsIgnoreCase(ingredientId)) {
                            iterator.remove();
                            toRemove--;
                        }
                    }
                }
            }
        }

        // Add the crafted item
        ItemInstance craftedItem = new ItemInstance(App.getItemDefinition(id));
        game.getCurrentPlayer().getInventory().addItem(craftedItem);
        return id + " crafted successfully.";
    }


}

