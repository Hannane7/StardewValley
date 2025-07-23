package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.PlayerRelation;
import com.untildawn.models.Players.Trade;
import com.untildawn.models.Players.Wallet;
import com.untildawn.views.InGameMenus.TradeMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;

import java.util.ArrayList;

public class TradeController {
    TradeMenuView view;

    public TradeController(TradeMenuView view) {
        this.view = view;
    }

    public void tradeHistory() {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        ArrayList<Trade> trades = player.getTrades();

        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (Trade trade : trades) {
            String seller = trade.getSeller().getName();
            String buyer = trade.getBuyer().getName();
            String itemSold = trade.getItemSold().getDisplayName();

            String itemCost = null;
            int itemCostNumber = -1;
            int price = -1;

            if (trade.getItemCost() != null) {
                itemCost = trade.getItemCost().getDisplayName();
                itemCostNumber = trade.getItemCostAmount();
            } else {
                price = trade.getPrice();
            }

            output.append("Trade ").append(counter++).append(":\n");
            output.append(String.format("Seller: %s | Buyer: %s\n", seller, buyer));
            output.append("Item: ").append(itemSold).append("\n");
            output.append("Cost: ");
            if (itemCost != null) output.append(itemCostNumber).append(" ").append(itemCost).append("\n");
            else output.append(price).append("g\n");

            output.append("-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void trade(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You can't trade with yourself!");
            return;
        }

        String type = this.view.prompt("Choose the trade type: (request / offer)");

        if (type.equalsIgnoreCase("offer")) {
            String offeredItemId = this.view.prompt("Enter the item id you want to offer:");
            ItemDefinition offeredItemDefinition = App.getItemDefinition(offeredItemId);
            if (offeredItemDefinition == null) {
                this.view.showMessage("Item doesn't exist.");
                return;
            }
            String itemAmountStr = this.view.prompt("Enter the amount:");
            int offeredItemAmount;
            try {
                offeredItemAmount = Integer.parseInt(itemAmountStr);
            } catch (NumberFormatException e) {
                this.view.showMessage("Please enter a valid number.");
                return;
            }

            String option = this.view.prompt("1. Enter price\n2. Enter item\n3. Cancel");
            int number;
            try {
                number = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                this.view.showMessage("Please enter a number");
                return;
            }

            switch (number) {
                case 1:
                    String priceStr = this.view.prompt("Enter the price");
                    int price;
                    try {
                        price = Integer.parseInt(priceStr);
                    } catch (NumberFormatException e) {
                        this.view.showMessage("Please enter a valid price.");
                        return;
                    }
                    // call func
                    handleOffer(currentPlayer, targetPlayer, offeredItemDefinition, offeredItemAmount, price);
                    break;
                case 2:
                    String requestedItemId = this.view.prompt("Enter the item:");
                    ItemDefinition requestedItemDefinition = App.getItemDefinition(requestedItemId);
                    if (requestedItemDefinition == null) {
                        this.view.showMessage("Item doesn't exist.");
                        return;
                    }
                    String requestedAmountStr = this.view.prompt("Enter the amount:");
                    int requestedAmount;
                    try {
                        requestedAmount = Integer.parseInt(requestedAmountStr);
                    } catch (NumberFormatException e) {
                        this.view.showMessage("Please enter a valid amount.");
                        return;
                    }
                    // call func
                    handleOffer(currentPlayer, targetPlayer, offeredItemDefinition,
                            offeredItemAmount, requestedItemDefinition, requestedAmount);
                    break;
                case 3:
                    break;
                default:
            }


        } else if (type.equalsIgnoreCase("request")) {
            String requestedItemId = this.view.prompt("Please enter the item id you want to request:");
            ItemDefinition requestedItemDefinition = App.getItemDefinition(requestedItemId);
            if (requestedItemDefinition == null) {
                this.view.showMessage("Item doesn't exist.");
                return;
            }
            String requestedAmountStr = this.view.prompt("Enter the amount:");
            int requestedAmount;
            try {
                requestedAmount = Integer.parseInt(requestedAmountStr);
            } catch (NumberFormatException e) {
                this.view.showMessage("Please enter a valid number.");
                return;
            }

            String option = this.view.prompt("1. Enter price\n2. Enter item\n3. Cancel");
            int number;
            try {
                number = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                this.view.showMessage("Please enter a number");
                return;
            }

            switch (number) {
                case 1:
                    String priceStr = this.view.prompt("Enter the price");
                    int price;
                    try {
                        price = Integer.parseInt(priceStr);
                    } catch (NumberFormatException e) {
                        this.view.showMessage("Please enter a valid price.");
                        return;
                    }
                    // call func
                    handleRequest(currentPlayer, targetPlayer, requestedItemDefinition, requestedAmount, price);
                    break;
                case 2:
                    String offeredItemId = this.view.prompt("Enter the item:");
                    ItemDefinition offeredItemDefinition = App.getItemDefinition(offeredItemId);
                    if (offeredItemDefinition == null) {
                        this.view.showMessage("Item doesn't exist.");
                        return;
                    }
                    String offeredAmountStr = this.view.prompt("Enter the amount:");
                    int offeredAmount;
                    try {
                        offeredAmount = Integer.parseInt(offeredAmountStr);
                    } catch (NumberFormatException e) {
                        this.view.showMessage("Please enter a valid amount.");
                        return;
                    }
                    // call func
                    handleRequest(currentPlayer, targetPlayer, requestedItemDefinition,
                            requestedAmount, offeredItemDefinition, offeredAmount);
                    break;
                case 3:
                    break;
                default:
            }


        } else {
            this.view.showMessage("Please enter a valid type.");
        }
    }


    private void handleOffer(Player currentPlayer, Player targetPlayer, ItemDefinition itemOffered, int amountOffered,
                             ItemDefinition itemRequested, int amountRequested) {
        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();

        if (amountOffered > currentPlayerInventory.getItemAmount(itemOffered.getId())) {
            this.view.showMessage(String.format("You don't have enough numbers of %s.", itemOffered));
            return;
        }

        if (!currentPlayerInventory.hasCapacity() && !currentPlayerInventory.hasItem(itemRequested.getId())) {
            this.view.showMessage("Your backpack is full.");
            return;
        }

        String message = String.format("%s respond: %s has offered you %d %s. cost: %d %s (accept / reject)",
                targetPlayer.getName(), currentPlayer.getName(),
                amountOffered, itemOffered.getDisplayName(), amountRequested, itemRequested.getDisplayName());
        // get respond and do the trade
        String respond = this.view.prompt(message);

        PlayerRelation relation = App.getCurrentGame().getPlayerRelation(currentPlayer, targetPlayer);

        if (respond.equalsIgnoreCase("accept")) {
            if (targetPlayerInventory.getItemAmount(itemRequested.getId()) < amountRequested) {
                this.view.showMessage(String.format("%s can't afford this item.", targetPlayer.getName()));
                return;
            }
            if (!targetPlayerInventory.hasCapacity() && !targetPlayerInventory.hasItem(itemOffered.getId())) {
                this.view.showMessage(String.format("%s's backpack is full.", targetPlayer.getName()));
                return;
            }
            ArrayList<ItemInstance> requestedItems = targetPlayerInventory.getItem(itemRequested.getId(), amountRequested);
            ArrayList<ItemInstance> offeredItems = currentPlayerInventory.getItem(itemOffered.getId(), amountOffered);

            try {
                TerminalAnimation.loadingAnimation(String.format("getting %s", itemRequested.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance requestedItem : requestedItems) {
                currentPlayerInventory.addItem(requestedItem);
            }
            try {
                TerminalAnimation.loadingAnimation(String.format("giving %s", itemOffered.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance offeredItem : offeredItems) {
                targetPlayerInventory.addItem(offeredItem);
            }

            this.view.showMessage("Trade has completed successfully.");
            relation.setXp(relation.getXp() + 50);

            Trade newTrade = new Trade(currentPlayer, targetPlayer, itemOffered, itemRequested, amountRequested, -1);
            currentPlayer.addTrade(newTrade);
            targetPlayer.addTrade(newTrade);

        } else if (respond.equalsIgnoreCase("reject")) {
            this.view.showMessage(String.format("%s has rejected.", targetPlayer.getName()));
            relation.setXp(relation.getXp() - 30);
        } else {
            this.view.showMessage("Invalid command.");
        }
    }

    private void handleOffer(Player currentPlayer, Player targetPlayer, ItemDefinition itemOffered, int amountOffered,
                             int priceRequested) {
        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();
        if (amountOffered > currentPlayerInventory.getItemAmount(itemOffered.getId())) {
            this.view.showMessage(String.format("You don't have enough numbers of %s.", itemOffered.getDisplayName()));
            return;
        }

        String message = String.format("%s respond: %s has offered you %d %s. price: %d (accept / reject)",
                targetPlayer.getName(), currentPlayer.getName(),
                amountOffered, itemOffered.getDisplayName(), priceRequested);

        String respond = this.view.prompt(message);

        PlayerRelation relation = App.getCurrentGame().getPlayerRelation(currentPlayer, targetPlayer);

        if (respond.equalsIgnoreCase("accept")) {
            Wallet targetPlayerWallet = targetPlayer.getWallet();
            Wallet currentPlayerWallet = currentPlayer.getWallet();

            if (targetPlayerWallet.getCoin() < priceRequested) {
                this.view.showMessage(String.format("%s can't afford this item.", targetPlayer.getName()));
                return;
            }
            if (!targetPlayerInventory.hasCapacity() && !targetPlayerInventory.hasItem(itemOffered.getId())) {
                this.view.showMessage(String.format("%s's backpack is full.", targetPlayer.getName()));
                return;
            }
            currentPlayerWallet.setCoin(currentPlayerWallet.getCoin() + priceRequested);
            targetPlayerWallet.setCoin(targetPlayerWallet.getCoin() - priceRequested);
            ArrayList<ItemInstance> offeredItems = currentPlayerInventory.getItem(itemOffered.getId(), amountOffered);
            try {
                TerminalAnimation.loadingAnimation(String.format("giving %s", itemOffered.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance offeredItem : offeredItems) {
                targetPlayerInventory.addItem(offeredItem);
            }

            this.view.showMessage("Trade has completed successfully.");
            relation.setXp(relation.getXp() + 50);

            Trade newTrade = new Trade(currentPlayer, targetPlayer, itemOffered, null, -1, priceRequested);
            currentPlayer.addTrade(newTrade);
            targetPlayer.addTrade(newTrade);


        } else if (respond.equalsIgnoreCase("reject")) {
            this.view.showMessage(String.format("%s has rejected.", targetPlayer.getName()));
            relation.setXp(relation.getXp() - 30);
        } else {
            this.view.showMessage("Invalid command.");
        }
    }
    private void handleRequest(Player currentPlayer, Player targetPlayer, ItemDefinition itemRequested, int amountRequested,
                               ItemDefinition itemOffered, int amountOffered) {
        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();
        if (amountOffered > currentPlayerInventory.getItemAmount(itemOffered.getId())) {
            this.view.showMessage(String.format("You don't have enough numbers of %s", itemOffered.getDisplayName()));
            return;
        }
        if (!currentPlayerInventory.hasCapacity() && !currentPlayerInventory.hasItem(itemRequested.getId())) {
            this.view.showMessage("Your backpack is full.");
            return;
        }
        String message = String.format("%s respond: %s has requested %d %s. Offered: %d %s (accept / reject)",
                targetPlayer.getName(), currentPlayer.getName(),
                amountRequested, itemRequested.getDisplayName(), amountOffered, itemOffered.getDisplayName());
        // get respond and do the trade
        String respond = this.view.prompt(message);

        PlayerRelation relation = App.getCurrentGame().getPlayerRelation(currentPlayer, targetPlayer);

        if (respond.equalsIgnoreCase("accept")) {
            if (targetPlayerInventory.getItemAmount(itemRequested.getId()) < amountRequested) {
                this.view.showMessage(String.format("%s doesn't have enough %s.",
                        targetPlayer.getName(), itemRequested.getDisplayName()));
                return;
            }
            if (!targetPlayerInventory.hasCapacity() && !targetPlayerInventory.hasItem(itemOffered.getId())) {
                this.view.showMessage(String.format("%s's backpack is full.", targetPlayer.getName()));
                return;
            }
            ArrayList<ItemInstance> requestedItems = targetPlayerInventory.getItem(itemRequested.getId(), amountRequested);
            ArrayList<ItemInstance> offeredItems = currentPlayerInventory.getItem(itemOffered.getId(), amountOffered);
            try {
                TerminalAnimation.loadingAnimation(String.format("getting %s", itemRequested.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance requestedItem : requestedItems) {
                currentPlayerInventory.addItem(requestedItem);
            }
            try {
                TerminalAnimation.loadingAnimation(String.format("giving %s", itemOffered.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance offeredItem : offeredItems) {
                targetPlayerInventory.addItem(offeredItem);
            }

            this.view.showMessage("Trade has completed successfully.");
            relation.setXp(relation.getXp() + 50);

            Trade newTrade = new Trade(targetPlayer, currentPlayer, itemRequested, itemOffered, amountOffered, -1);
            currentPlayer.addTrade(newTrade);
            targetPlayer.addTrade(newTrade);


        } else if (respond.equalsIgnoreCase("reject")) {
            this.view.showMessage(String.format("%s has rejected.", targetPlayer.getName()));
            relation.setXp(relation.getXp() - 30);
        } else {
            this.view.showMessage("Invalid command.");
        }
    }
    private void handleRequest(Player currentPlayer, Player targetPlayer, ItemDefinition itemRequested, int amountRequested,
                               int priceOffered) {
        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();
        if (priceOffered > currentPlayer.getWallet().getCoin()) {
            this.view.showMessage("You don't have enough money.");
            return;
        }
        if (!currentPlayerInventory.hasCapacity() && !currentPlayerInventory.hasItem(itemRequested.getId())) {
            this.view.showMessage("Your backpack is full.");
            return;
        }
        String message = String.format("%s respond: %s has requested %d %s. Offered: %dg (accept / reject)",
                targetPlayer.getName(), currentPlayer.getName(),
                amountRequested, itemRequested.getDisplayName(), priceOffered);
        String respond = this.view.prompt(message);

        PlayerRelation relation = App.getCurrentGame().getPlayerRelation(currentPlayer, targetPlayer);

        if (respond.equalsIgnoreCase("accept")) {
            Wallet currentPlayerWallet = currentPlayer.getWallet();
            Wallet targetPlayerWallet = targetPlayer.getWallet();

            currentPlayerWallet.setCoin(currentPlayerWallet.getCoin() - priceOffered);
            targetPlayerWallet.setCoin(targetPlayerWallet.getCoin() + priceOffered);
            ArrayList<ItemInstance> requestedItems = targetPlayerInventory.getItem(itemRequested.getId(), amountRequested);
            try {
                TerminalAnimation.loadingAnimation(String.format("getting %s", itemRequested.getDisplayName()));
            } catch (InterruptedException e) {
                this.view.showMessage("Unknown error.");
                return;
            }
            for (ItemInstance requestedItem : requestedItems) {
                currentPlayerInventory.addItem(requestedItem);
            }
            this.view.showMessage("Trade has completed successfully.");
            relation.setXp(relation.getXp() + 50);

            Trade newTrade = new Trade(targetPlayer, currentPlayer, itemRequested, null, -1, priceOffered);
            currentPlayer.addTrade(newTrade);
            targetPlayer.addTrade(newTrade);

        } else if (respond.equalsIgnoreCase("reject")) {
            this.view.showMessage(String.format("%s has rejected.", targetPlayer.getName()));
            relation.setXp(relation.getXp() - 30);

        } else {
            this.view.showMessage("Invalid command.");
        }
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }
}
