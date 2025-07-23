package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.models.App;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.CookingMenu;
import com.untildawn.models.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CookingMenuController {
    CookingMenu view;

    public CookingMenuController(CookingMenu view) {
        this.view = view;
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }

    public String eatFood(Game game, String foodName) {
        Player player = game.getCurrentPlayer();
        resetAbilities(player);
        if (App.getItemDefinitionByName(foodName) == null) {
            return "This isn't available in the game.";
        }
        ItemDefinition itemDefinition = App.getItemDefinitionByName(foodName);
        if (player.getInventory().hasItem(itemDefinition.getId())) {
            int foodEnergy = getFoodEnergy(itemDefinition);


            if (itemDefinition.getId().toString().equalsIgnoreCase("dish_O_the_sea")) {
                player.getAbilities().increaseFishing(150);
                player.setFishingEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("seaform_pudding")) {
                player.getAbilities().increaseFishing(150);
                player.setFishingEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("miners_treat")) {
                player.getAbilities().increaseMining(150);
                player.setMiningEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("survival_burger")) {
                player.getAbilities().increaseNature(150);
                player.setForagingEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("pancakes")) {
                player.getAbilities().increaseNature(150);
                player.setForagingEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("red_plate")) {
                player.setEnergyLimit(250);
                player.setMaxEnergyEnable(true);
            } else if (itemDefinition.getId().toString().equalsIgnoreCase("triple_shot_espresso")) {
                player.setEnergyLimit(300);
                player.setMaxEnergyEnable(true);
            }
            player.increaseEnergy(foodEnergy);
            return "You ate " + itemDefinition.getDisplayName() + ".";
        } else {
            return "You don't have " + itemDefinition.getDisplayName() + " in your inventory.";
        }
    }

    public String cook(Game game, String itemName) {
        Player player = game.getCurrentPlayer();
        if (player.getEnergy() - 3 <= 0) {
            return "You can't cook because u will ghash.";
        }
        if (!player.getInventory().hasCapacity()) {
            return "Your backpack is full.";
        } else if (itemName.equalsIgnoreCase("Fried Egg")) {
            ItemDefinition definition = App.getItemDefinition("fried_egg");
            return cookFood(game, definition, "fried_egg");
        } else if (itemName.equalsIgnoreCase("Baked Fish")) {
            ItemDefinition definition = App.getItemDefinition("baked_fish");
            return cookFood(game, definition, "baked_fish");
        } else if (itemName.equalsIgnoreCase("Salad")) {
            ItemDefinition definition = App.getItemDefinition("salad");
            return cookFood(game, definition, "salad");
        } else if (itemName.equalsIgnoreCase("Omelet")) {
            if (player.getInventory().hasItem(App.getItemDefinition("omelet_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("omelet");
                return cookFood(game, definition, "omelet");
            } else {
                return "You don't have the recipe to cook omelet.";
            }
        } else if (itemName.equalsIgnoreCase("Pumpkin Pie")) {
            ItemDefinition definition = App.getItemDefinition("pumpkin_pie");
            return cookFood(game, definition, "pumpkin_pie");
        } else if (itemName.equalsIgnoreCase("spaghetti")) {
            ItemDefinition definition = App.getItemDefinition("spaghetti");
            return cookFood(game, definition, "spaghetti");
        } else if (itemName.equalsIgnoreCase("pizza")) {
            if (player.getInventory().hasItem(App.getItemDefinition("pizza_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("pizza");
                return cookFood(game, definition, "pizza");
            } else {
                return "You don't have the recipe to cook pizza.";
            }
        } else if (itemName.equalsIgnoreCase("tortilla")) {
            if (player.getInventory().hasItem(App.getItemDefinition("tortilla_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("tortilla");
                return cookFood(game, definition, "tortilla");
            } else {
                return "You don't have the recipe to cook tortilla.";
            }
        } else if (itemName.equalsIgnoreCase("maki roll")) {
            if (player.getInventory().hasItem(App.getItemDefinition("maki_roll_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("maki_roll");
                return cookFood(game, definition, "maki_roll");
            } else {
                return "You don't have the recipe to cook maki roll.";
            }
        } else if (itemName.equalsIgnoreCase("triple shot espresso")) {
            if (player.getInventory().hasItem(App.getItemDefinition("triple_shot_espresso_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("triple_shot_espresso");
                return cookFood(game, definition, "triple_shot_espresso");
            } else {
                return "You don't have the recipe to cook triple shot espresso.";
            }
        } else if (itemName.equalsIgnoreCase("cookie")) {
            if (player.getInventory().hasItem(App.getItemDefinition("cookie_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("cookie");
                return cookFood(game, definition, "cookie");
            } else {
                return "You don't have the recipe to cook cookie.";
            }
        } else if (itemName.equalsIgnoreCase("hash browns")) {
            if (player.getInventory().hasItem(App.getItemDefinition("hash_browns_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("hash_brown");
                return cookFood(game, definition, "hash_brown");
            } else {
                return "You don't have the recipe to cook hash browns.";
            }
        } else if (itemName.equalsIgnoreCase("pancakes")) {
            if (player.getInventory().hasItem(App.getItemDefinition("pancakes_recipe").getId())) {
                ItemDefinition definition = App.getItemDefinition("pancakes");
                return cookFood(game, definition, "pancakes");
            } else {
                return "You don't have the recipe to cook pancakes.";
            }
        } else if (itemName.equalsIgnoreCase("fruit salad")) {
            ItemDefinition definition = App.getItemDefinition("fruit_salad");
            return cookFood(game, definition, "fruit_salad");
        } else if (itemName.equalsIgnoreCase("red plate")) {
            ItemDefinition definition = App.getItemDefinition("red_plate");
            return cookFood(game, definition, "red_plate");
        } else if (itemName.equalsIgnoreCase("bread")) {
            ItemDefinition definition = App.getItemDefinition("bread");
            return cookFood(game, definition, "bread");
        } else if (itemName.equalsIgnoreCase("salmon dinner")) {
            if (player.getInventory().hasItem(App.getItemDefinition("salmon_dinner").getId())) {
                ItemDefinition definition = App.getItemDefinition("salmon_dinner");
                return cookFood(game, definition, "salmon_dinner");
            } else {
                return "You don't have the recipe to cook salmon dinner.";
            }
        } else if (itemName.equalsIgnoreCase("vegetable medley")) {
            ItemDefinition definition = App.getItemDefinition("vegetable_medley");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("foraging");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getNatureAbility()) < foragingLevel) {
                return "Your foraging level isn't enough.";
            }
            return cookFood(game, definition, "vegetable_medley");
        } else if (itemName.equalsIgnoreCase("farmer's lunch")) {
            ItemDefinition definition = App.getItemDefinition("farmers_lunch");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("farming");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFarmingAbility()) < foragingLevel) {
                return "Your farming level isn't enough.";
            }
            return cookFood(game, definition, "farmers_lunch");
        } else if (itemName.equalsIgnoreCase("survival burger")) {
            ItemDefinition definition = App.getItemDefinition("survival_burger");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("foraging");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getNatureAbility()) < foragingLevel) {
                return "Your foraging level isn't enough.";
            }
            return cookFood(game, definition, "survival_burger");
        } else if (itemName.equalsIgnoreCase("dish o the sea")) {
            ItemDefinition definition = App.getItemDefinition("dish_O_the_sea");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("fishing");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFishingAbility()) < foragingLevel) {
                return "Your fishing level isn't enough.";
            }
            return cookFood(game, definition, "dish_o_the_sea");
        } else if (itemName.equalsIgnoreCase("seaform pudding")) {
            ItemDefinition definition = App.getItemDefinition("seaform_pudding");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("fishing");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getFishingAbility()) < foragingLevel) {
                return "Your fishing level isn't enough.";
            }
            return cookFood(game, definition, "seaform_pudding");
        } else if (itemName.equalsIgnoreCase("miner's treat")) {
            ItemDefinition definition = App.getItemDefinition("miners_treat");
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            int foragingLevel = 0;
            if (sourceObj instanceof Map<?, ?> sourceMap) {
                Object farming = sourceMap.get("mining");
                if (farming instanceof Integer) {
                    foragingLevel = (int) farming;
                }
            }
            if (game.getCurrentPlayer().getAbilities().getAbilityLevel(game.getCurrentPlayer().getAbilities().getMiningAbility()) < foragingLevel) {
                return "Your mining level isn't enough.";
            }
            return cookFood(game, definition, "miners_treat");
        } else {
            return "This food isn't available in the game.";
        }
    }

    private static String cookFood(Game game, ItemDefinition definition, String id) {
        game.getCurrentPlayer().reduceEnergyWhenCrafting(3);
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

    public String putRef(String name, Game game) {

        Player player = game.getCurrentPlayer();
        if (App.getItemDefinitionByName(name) == null) {
            return "Item not found.";
        }
        ItemDefinition itemDefinition = App.getItemDefinitionByName(name);
        if (!player.getRefrigerator().hasCapacity()) {
            return "Your fridge is full.";
        }
        if (!player.getInventory().hasItem(itemDefinition.getId())) {
            return "You don't have " + itemDefinition.getDisplayName() + ".";
        } else if ((itemDefinition.getType().toString().equalsIgnoreCase("food") || itemDefinition.getType().toString().equalsIgnoreCase("shop_recipe") || itemDefinition.getType().toString().equalsIgnoreCase("all_crops") || itemDefinition.getType().toString().equalsIgnoreCase("foraging_crops"))) {
            int amount = player.getInventory().getItemAmount(itemDefinition.getId());
            player.getInventory().trashItemAll(itemDefinition.getId());
            for (int i = 0; i < amount; i++) {
                player.getRefrigerator().addItem(new ItemInstance(itemDefinition));
            }
            return "You placed " + itemDefinition.getDisplayName() + " in the fridge.";
        } else {
            return "This item isn't edible , You can't put in the fridge.";
        }
    }

    public String pickRef(String name, Game game) {
        Player player = game.getCurrentPlayer();
        if (App.getItemDefinitionByName(name) == null) {
            return "Item not found.";
        }
        ItemDefinition itemDefinition = App.getItemDefinitionByName(name);
        if (player.getRefrigerator().hasItem(itemDefinition.getId(), 1)) {
            int amount = player.getRefrigerator().getItemAmount(itemDefinition.getId());
            player.getRefrigerator().trashItemAll(itemDefinition.getId());
            for (int i = 0; i < amount; i++) {
                player.getInventory().addItem(new ItemInstance(itemDefinition));
            }
            return "You picked " + itemDefinition.getDisplayName() + " from the fridge.";
        } else {
            return "This item isn't available in your fridge.";
        }
    }

    private StringBuilder getIngredientsDescriptionForStarter(ItemDefinition definition) {
        StringBuilder result = new StringBuilder();

        if (definition.getType().toString().equalsIgnoreCase("food")) {
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();

            // Check if source contains only "starter"
            Object sourceObj = baseAttributes.get(ItemAttributes.source);
            if (sourceObj instanceof Map<?, ?> sourceMap && (sourceMap.containsKey("starter") || sourceMap.containsKey("foraging") || sourceMap.containsKey("farming") || sourceMap.containsKey("mining") || sourceMap.containsKey("fishing"))) {
                // Show ingredients if present
                result.append(definition.getDisplayName()).append("\n");
                Object ingredientsObj = baseAttributes.get(ItemAttributes.ingredients);
                if (ingredientsObj instanceof Map<?, ?> ingredientsMap) {
                    result.append("Ingredients:\n");
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

    private StringBuilder getIngredientsDescription(ItemDefinition definition) {
        StringBuilder result = new StringBuilder();
        if (definition.getType().toString().equalsIgnoreCase("shop_recipe")) {
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();
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
        return result;
    }

    public String printRecipes(Game game) {
        Player player = game.getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (ItemDefinition item : App.getItemDefinitions()) {
            result.append(getIngredientsDescriptionForStarter(item));
        }
        for (ItemDefinition item : App.getItemDefinitions()) {
            if (player.getInventory().hasItem(item.getId())) {
                result.append(getIngredientsDescription(item));
            }
        }
        return result.toString();
    }

    private Integer getFoodEnergy(ItemDefinition definition) {
        // Check if item type is food
        if (definition.getType().toString().equalsIgnoreCase("food")) {
            Map<ItemAttributes, Object> baseAttributes = definition.getBaseAttributes();

            // Check if energy attribute exists
            if (baseAttributes.containsKey(ItemAttributes.energy)) {
                Object energyObj = baseAttributes.get(ItemAttributes.energy);
                if (energyObj instanceof Integer energy) {
                    return energy;
                }
            }
        }
        return null; // Not a food item or no energy field
    }

    private void resetAbilities(Player player) {
        if (player.isFarmingEnable()) {
            player.getAbilities().increaseFarming(-100);
        } else if (player.isFishingEnable()) {
            player.getAbilities().increaseFishing(-100);
        } else if (player.isForagingEnable()) {
            player.getAbilities().increaseNature(-100);
        } else if (player.isMiningEnable()) {
            player.getAbilities().increaseMining(-100);
        } else if (player.isMaxEnergyEnable()) {
            player.setEnergyLimit(200);
        }
        player.setFarmingEnable(false);
        player.setFishingEnable(false);
        player.setForagingEnable(false);
        player.setMiningEnable(false);
        player.setMaxEnergyEnable(false);
    }
}

