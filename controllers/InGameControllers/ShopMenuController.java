package com.untildawn.controllers.InGameControllers;

//import edu.stanford.nlp.parser.lexparser.Item;
//import edu.stanford.nlp.trees.BobChrisTreeNormalizer;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Items.ShippingBin;
import com.untildawn.models.MapElements.Shop;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.Wallet;
import com.untildawn.views.InGameMenus.ShopMenuView;


import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ShopMenuController {
    private static Shop shop;
    private ShopMenuView view;
    public ShopMenuController(ShopMenuView view) {
        this.view = view;
    }

    public static void setShop(Shop shop) {
        ShopMenuController.shop = shop;
    }

    public void showAllProducts() {
        Game game = App.getCurrentGame();
        StringBuilder output = new StringBuilder();

        output.append(shop.getShopName()).append(":\n");
        for (Map.Entry<ItemDefinition, Integer> entry : shop.getShopItems().entrySet()) {

            ItemDefinition shopItem = entry.getKey();
            int stock = entry.getValue();

            int price = (int) shopItem.getAttribute(ItemAttributes.price);
            ArrayList<String> itemDiscountSeasons = (ArrayList<String>) shopItem.getAttribute(ItemAttributes.season);
            String currentSeason = game.getDateTime().getSeason().name().toLowerCase();
            if (itemDiscountSeasons != null) {
                if (itemDiscountSeasons.contains(currentSeason)) {
                    price = (price * 2) / 3;
                }
            }

            String stockStr =
                stock == 0
                ? "not available"
                : (stock == -1
                ? "unlimited"
                : Integer.toString(stock));

            output.append(String.format("Item name: %s | Stock: %s | Price: %d\n",
                    shopItem.getDisplayName(), stockStr, price));
            output.append("-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void showAllAvailableProducts() {
        Game game = App.getCurrentGame();
        StringBuilder output = new StringBuilder();

        output.append(shop.getShopName()).append(":\n");
        for (Map.Entry<ItemDefinition, Integer> entry : shop.getShopItems().entrySet()) {

            ItemDefinition shopItem = entry.getKey();
            int stock = entry.getValue();
            if (stock == 0) continue;

            int price = (int) shopItem.getAttribute(ItemAttributes.price);
            ArrayList<String> itemDiscountSeasons;
            try {
                itemDiscountSeasons = (ArrayList<String>) shopItem.getAttribute(ItemAttributes.season);
            } catch (Exception e) {
                System.out.println("Unknown error. ActionMenuController -> line 81");
                return;
            }
            String currentSeason = game.getDateTime().getSeason().name().toLowerCase();
            if (itemDiscountSeasons != null) {
                if (itemDiscountSeasons.contains(currentSeason)) {
                    price = (price * 2) / 3;
                }
            }

            output.append(String.format("Item name: %s | Stock: %s | Price: %d\n",
                    shopItem.getDisplayName(), stock == -1 ? "unlimited" : stock, price));
            output.append("-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void purchase(String productId, String countStr) {
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid number.");
            return;
        }

        if (count <= 0) {
            this.view.showMessage("Please enter a positive integer for count.");
            return;
        }

        Game game = App.getCurrentGame();
        int currentTime = game.getDateTime().getHour();
        if (!(currentTime >= shop.getStartTime() && currentTime <= shop.getEndTime())) {
            this.view.showMessage("Shop is close");
            return;
        }

        if (!shop.hasProduct(productId)) {
            this.view.showMessage("Shop doesn't have this product.");
            return;
        }

        int stock = shop.getProductStock(productId);
        if (count > stock && stock != -1) {
            this.view.showMessage("Not enough available stock.");
            return;
        }

        Player currentPlayer = game.getCurrentPlayer();
        Wallet wallet = currentPlayer.getWallet();
        ItemDefinition item = shop.getProductDefinition(productId);
        int productPrice = (int) item.getAttribute(ItemAttributes.price);

        ArrayList<String> itemDiscountSeasons = (ArrayList<String>) item.getAttribute(ItemAttributes.season);
        String currentSeason = game.getDateTime().getSeason().name().toLowerCase();
        if (itemDiscountSeasons != null) {
            if (itemDiscountSeasons.contains(currentSeason)) {
                productPrice = (productPrice * 2) / 3;
            }
        }

        int totalPrice = productPrice * count;

        if (wallet.getCoin() < totalPrice) {
            this.view.showMessage("Your can't afford this product.");
            return;
        }

        ItemDefinition productDefinition = App.getItemDefinition(productId);
        Inventory inventory = currentPlayer.getInventory();
        if (productDefinition == null) {
            this.view.showMessage("Unknown error.");
            return;
        }
        if (!inventory.hasCapacity() && !inventory.hasItem(productDefinition.getId())) {
            this.view.showMessage("Your backpack is full.");
            return;
        }

        wallet.setCoin(wallet.getCoin() - totalPrice);

        ArrayList<ItemInstance> products = new ArrayList<>();

        for (int i = 0; i < count; i++)
            products.add(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(productId))));
        for (ItemInstance product : products)
            inventory.addItem(product);

        if (stock != -1) {
            shop.setProductStock(productId, shop.getProductStock(productId) - count);
        }

        this.view.showMessage("Products are added to your inventory successfully.");
    }

    public void cheatAddDollars(String amountStr) {
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid amount.");
            return;
        }

        if (amount <= 0) {
            this.view.showMessage("PLease enter a valid amount.");
            return;
        }

        Game game = App.getCurrentGame();
        Wallet wallet = game.getCurrentPlayer().getWallet();
        wallet.setCoin(wallet.getCoin() + amount);

        this.view.showMessage("Your wallet has been charged by " + amountStr);
    }

    public void sell(String itemId, String countStr) {
        int count;
        if (!countStr.equalsIgnoreCase("all")) {
            try {
                count = Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                this.view.showMessage("Please enter a valid number.");
                return;
            }
            if (count <= 0) {
                this.view.showMessage("Please enter a valid number.");
                return;
            }
        } else {
            count = -1;
        }

        Game currentGame = App.getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();
        int playerY = (int) currentPlayer.getPosition().getY();
        int playerX = (int) currentPlayer.getPosition().getX();

        boolean isCloseToShippingBin = false;

        for (int y = playerY - 1; y <= playerY + 1; y++) {
            for (int x = playerX - 1; x <= playerX + 1; x++) {
                Tile tile = currentGame.getGameMap().getTile(y, x);
                if (tile.getItem().getDefinition().getId() == ItemIDs.shipping_bin) { // TODO
                    isCloseToShippingBin = true;
                }
            }
        }

        if (!isCloseToShippingBin) {
            this.view.showMessage("You are not close to a shipping bin.");
            return;
        }

        // check the sellability of item
        ItemDefinition itemDefinition = App.getItemDefinition(itemId);
        ItemType[] invalidTypes = {ItemType.tool, ItemType.shop, ItemType.lake, ItemType.floor, ItemType.greenhouse,
        ItemType.cottage, ItemType.tree, ItemType.quarry, ItemType.rock, ItemType.home, ItemType.friendship_level, ItemType.money};
        /*
            many of these types are not available in inventory!
         */
        for (ItemType itemType : invalidTypes) {
            assert itemDefinition != null;
            if (itemType == itemDefinition.getType()) {
                this.view.showMessage("You can't sell this type of item.");
                return;
            }
        }

        Inventory inventory = currentPlayer.getInventory();
        if (!inventory.hasItem(itemDefinition.getId())) {
            this.view.showMessage("You don't have this item.");
            return;
        }
        if (count != -1 && (inventory.getItemAmount(itemDefinition.getId()) < count)) {
            this.view.showMessage("You don't have enough numbers of this item.");
            return;
        }

        // calculate the price of the sold items
        int totalItemsAmount;
        int totalPrice = 0;
        if (count == -1) {
            totalItemsAmount = inventory.getItemAmount(itemDefinition.getId());
        } else {
            totalItemsAmount = count;
        }
        for (int i = 0; i < totalItemsAmount; i++) {
            String quality = (String) inventory.getItem(itemDefinition.getId()).getAttribute(ItemAttributes.quality);
            Integer baseSellPrice = getItemBaseSellPrice(itemDefinition);
            if (baseSellPrice == null) {
                this.view.showMessage("Unknown error. ShopMenuController -> line 246");
                return;
            }
            float coefficient;
            switch (quality.toLowerCase()) {
                case "regular":
                    coefficient = 1;
                    break;
                case "silver":
                    coefficient = 1.25f;
                    break;
                case "golden":
                    coefficient = 1.5f;
                    break;
                case "iridium":
                    coefficient = 2;
                    break;
                default:
                    this.view.showMessage("Unknown error. (ShopMenuController -> line 254)");
                    return;
            }
            totalPrice += (int) coefficient * baseSellPrice;
        }

        // set trigger for next morning -> update player's wallet
        Game game = App.getCurrentGame();
        ShippingBin bin = game.getShippingBin();
        bin.putIntoBin(currentPlayer, totalPrice);
        this.view.showMessage("Items has been added to shipping bin successfully.");
    }


    private Integer getItemBaseSellPrice(ItemDefinition itemDefinition) {
        Integer baseSellPrice = (Integer) itemDefinition.getAttribute(ItemAttributes.baseSellPrice);
        if (baseSellPrice == null) {
            baseSellPrice = (Integer) ((int) itemDefinition.getAttribute(ItemAttributes.price) / 2);
        }
        return baseSellPrice;
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }
}



