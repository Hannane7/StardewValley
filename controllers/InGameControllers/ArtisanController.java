package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.models.App;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;

import java.util.Objects;

public class ArtisanController {
    static ActionMenuView view = new ActionMenuView();

    public static void beeHouse(String itemName, Player player) {
        if (itemName.equals("honey")) {
            ItemInstance honey = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("honey")));
            player.getInventory().setArtisan(honey);
            view.showMessage("You've used bee house to make honey!");
        } else {
            view.showMessage("please enter a correct item or ingredient!");
        }
    }

    public static void cheesePress(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "cheese":
                if (ingredient.equalsIgnoreCase("milk")) {
                    addArtisanToInventory(player.getInventory(), "milk",
                        "cheese", ItemIDs.milk, 230, "cheese_press");
                } else if (ingredient.equalsIgnoreCase("large_milk")) {
                    addArtisanToInventory(player.getInventory(), "large_milk",
                        "cheese", ItemIDs.large_milk, 345, "cheese_press");
                }
                break;
            case "goat_cheese":
                if (ingredient.equalsIgnoreCase("goat_milk")) {
                    addArtisanToInventory(player.getInventory(), "goat_milk",
                        "goat_cheese", ItemIDs.goat_milk, 400, "cheese_press");
                } else if (ingredient.equalsIgnoreCase("large_goat_milk")) {
                    addArtisanToInventory(player.getInventory(), "large_goat_milk",
                        "goat_cheese", ItemIDs.large_goat_milk, 600, "cheese_press");
                }
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void keg(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "beer":
                addArtisanToInventory(player.getInventory(), "wheat",
                    "beer", ItemIDs.wheat, 200, "keg");
                break;
            case "vinegar":
                addArtisanToInventory(player.getInventory(), "rice",
                    "vinegar", ItemIDs.rice, 100, "keg");
                break;
            case "coffee":
                addArtisanToInventory(player.getInventory(), "coffee_bean",
                    "coffee", ItemIDs.coffee_bean, 150, "keg");
                break;
            case "juice":
                ItemDefinition ingredientDef1 = Objects.requireNonNull(App.getItemDefinition(ingredient));//TODO
                addArtisanToInventory(player.getInventory(), ingredient, "juice", ingredientDef1.getId(),
                    (int) (2.25 * (int) ingredientDef1.getAttribute(ItemAttributes.baseSellPrice)),
                    2 * (int) ingredientDef1.getAttribute(ItemAttributes.energy), "keg");
                break;
            case "mead":
                addArtisanToInventory(player.getInventory(), "honey",
                    "mead", ItemIDs.honey, 300, "keg");
                break;
            case "pale_ale":
                addArtisanToInventory(player.getInventory(), "hops",
                    "pale_ale", ItemIDs.hops, 300, "keg");
                break;
            case "wine":
                ItemDefinition ingredientDef2 = Objects.requireNonNull(App.getItemDefinition(ingredient));
                addArtisanToInventory(player.getInventory(), ingredient, "wine", ingredientDef2.getId(),
                    3 * (int) ingredientDef2.getAttribute(ItemAttributes.baseSellPrice),
                    (int) (1.75 * (int) ingredientDef2.getAttribute(ItemAttributes.energy)), "keg");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void dehydrator(String itemName, Player player) {
        switch (itemName) {
            case "dried_mushroom":
                break;
            case "dried_fruit":
                break;
            case "raisins":
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void charcoalKiln(String itemName, Player player) {
        switch (itemName) {
            case "coal":
                addArtisanToInventory(player.getInventory(), "wood",
                    "coal", ItemIDs.wood, 50, "charcoal_kiln");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void loom(String itemName, Player player) {
        switch (itemName) {
            case "cloth":
                addArtisanToInventory(player.getInventory(), "wool",
                    "cloth", ItemIDs.wool, 470, "loom");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void mayonnaiseMachine(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "mayonnaise":
                if (ingredient.equalsIgnoreCase("egg")) {
                    addArtisanToInventory(player.getInventory(), "egg",
                        "mayonnaise", ItemIDs.egg, 190, "mayonnaise_machine");
                } else if (ingredient.equalsIgnoreCase("large_egg")) {
                    addArtisanToInventory(player.getInventory(), "large_egg",
                        "mayonnaise", ItemIDs.large_egg, 237, "mayonnaise_machine");
                }
                break;
            case "duck_mayonnaise"://TODO
                addArtisanToInventory(player.getInventory(), "duck_egg",
                    "duck_mayonnaise", ItemIDs.duck_egg, 37, "mayonnaise_machine");
                break;
            case "dinosaur_mayonnaise":
                addArtisanToInventory(player.getInventory(), "dinosaur_egg",
                    "dinosaur_mayonnaise", ItemIDs.dinosaur_egg, 800, "mayonnaise_machine");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void oilMaker(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "truffle_oil":
                addArtisanToInventory(player.getInventory(), "truffle",
                    "truffle_oil", ItemIDs.truffle, 1065, "oil_maker");
                break;
            case "oil":
                if (ingredient.equalsIgnoreCase("corn")) {
                    addArtisanToInventory(player.getInventory(), "corn",
                        "oil", ItemIDs.corn, 100, "oil_maker");
                } else if (ingredient.equalsIgnoreCase("sunflower_seeds")) {
                    addArtisanToInventory(player.getInventory(), "sunflower_seeds",
                        "oil", ItemIDs.sunflower_seeds, 100, "oil_maker");
                } else if (ingredient.equalsIgnoreCase("sunflower")) {
                    addArtisanToInventory(player.getInventory(), "sunflower",
                        "oil", ItemIDs.sunflower, 100, "oil_maker");
                }
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void preservesJar(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "pickles":
                ItemDefinition ingredientDef1 = Objects.requireNonNull(App.getItemDefinition(ingredient));
                addArtisanToInventory(player.getInventory(), ingredient, "pickles", ingredientDef1.getId(),
                    2 * (int) ingredientDef1.getAttribute(ItemAttributes.baseSellPrice) + 50,
                    (int) (1.75 * (int) ingredientDef1.getAttribute(ItemAttributes.energy)), "preserves_jar");
                break;
            case "jelly":
                ItemDefinition ingredientDef2 = Objects.requireNonNull(App.getItemDefinition(ingredient));
                addArtisanToInventory(player.getInventory(), ingredient, "jelly", ingredientDef2.getId(),
                    2 * (int) ingredientDef2.getAttribute(ItemAttributes.baseSellPrice) + 50,
                    2 * (int) ingredientDef2.getAttribute(ItemAttributes.energy), "preserves_jar");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void fishSmoker(String itemName, String ingredient, Player player) {
        switch (itemName) {
            case "smoked_fish":
                if (ingredient.contains("coal")) {
                    view.showMessage("please enter only the fish name!");
                    return;
                }
                ItemDefinition ingredientDef2 = Objects.requireNonNull(App.getItemDefinition(ingredient));
                addArtisanToInventory(player.getInventory(), ingredient, "smoked_fish", ingredientDef2.getId(),
                    2 * (int) ingredientDef2.getAttribute(ItemAttributes.baseSellPrice),
                    (int) (1.5 * (int) ingredientDef2.getAttribute(ItemAttributes.energy)), "fish_smoker");
                break;
            default:
                view.showMessage("please enter a correct item or ingredient!");
                break;
        }
    }

    public static void furnace(String itemName, String ingredient, Player player) {
        if (ingredient.contains("coal")) {
            view.showMessage("please enter only the bar name!");
            return;
        }
        ItemDefinition artisan = Objects.requireNonNull(App.getItemDefinition(itemName));
        ItemDefinition ingredientDef = Objects.requireNonNull(App.getItemDefinition(ingredient));
        addArtisanToInventory(player.getInventory(), ingredient, artisan.getId().name(), ingredientDef.getId(),
            10 * (int) ingredientDef.getAttribute(ItemAttributes.baseSellPrice), "furnace");
    }

    public static boolean isInInventory(Inventory inventory, ItemDefinition item) {
        if (!inventory.getItems().containsKey(item.getId())) {
            view.showMessage("You don't have enough " + item.getDisplayName().toLowerCase() + "!");
            return false;
        }
        return true;
    }

    public static void addArtisanToInventory(Inventory inventory, String ingredientId,
                                             String artisanId, ItemIDs ingredient, int price, String machineName) {
        if (!isInInventory(inventory, Objects.requireNonNull(App.getItemDefinition(ingredientId)))) return;
        ItemInstance item = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(artisanId)));
        inventory.setArtisan(item);
        inventory.trashItem(ingredient, 1);
        item.setAttribute(ItemAttributes.price, price);
        view.showMessage("You've used " + machineName + " to make " + artisanId + "!");
    }

    public static void addArtisanToInventory(Inventory inventory, String ingredientId, String artisanId,
                                             ItemIDs ingredient, int price, int energy, String machineName) {
        if (!isInInventory(inventory, Objects.requireNonNull(App.getItemDefinition(ingredientId)))) return;
        ItemInstance item = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(artisanId)));
        inventory.setArtisan(item);
        inventory.trashItem(ingredient, 1);
        item.setAttribute(ItemAttributes.price, price);
        item.setAttribute(ItemAttributes.energy, energy);
        view.showMessage("You've used " + machineName + " to make " + artisanId + "!");
    }
}
