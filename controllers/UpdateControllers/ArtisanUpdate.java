package com.untildawn.controllers.UpdateControllers;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.models.App;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;

import java.util.ArrayList;

public class ArtisanUpdate {
    static ActionMenuView view = new ActionMenuView();

    public static void artisanWithHour(int hourPassed) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            ArrayList<ItemInstance> artisanToRemove = new ArrayList<>();
            ArrayList<ItemInstance> artisans = player.getInventory().getArtisan();
            for (ItemInstance artisan : artisans) {
                if (!artisan.getDefinition().hasAttribute(ItemAttributes.hour)) {
                    continue;
                }
                int hour = (int) artisan.getAttribute(ItemAttributes.hour);
                artisan.setAttribute(ItemAttributes.hour, hour - hourPassed);
                if (hour - hourPassed <= 0) {
                    view.showMessage(artisan.getDefinition().getId() + " is now ready! ");
                    artisan.setAttribute(ItemAttributes.isReady, true);
                    artisanToRemove.add(artisan);
                }
            }
            for (ItemInstance itemInstance : artisanToRemove) {
                artisans.remove(itemInstance);
            }
        }
    }

    public static void artisanWithDay(int dayPassed) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            Inventory inventory = player.getInventory();
            ArrayList<ItemInstance> artisans = inventory.getArtisan();
            ArrayList<ItemInstance> artisanToRemove = new ArrayList<>();
            for (ItemInstance artisan : artisans) {
                if (!artisan.getDefinition().hasAttribute(ItemAttributes.day)) {
                    continue;
                }
                int day = (int) artisan.getAttribute(ItemAttributes.day);
                artisan.setAttribute(ItemAttributes.day, day - dayPassed);
                if (day - dayPassed <= 0) {
                    view.showMessage(artisan.getDefinition().getId() + " is now ready! ");
                    artisan.setAttribute(ItemAttributes.isReady, true);
                    inventory.addItem(artisan);
                    artisanToRemove.add(artisan);
                }
            }
            for (ItemInstance artisan : artisanToRemove) {
                artisans.remove(artisan);
            }
        }
    }

    public static void artisanNextMorning() {

    }
}
