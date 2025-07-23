package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Gift;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.PlayerRelation;
import com.untildawn.models.Players.Wallet;
import com.untildawn.views.InGameMenus.ActionMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


import java.util.ArrayList;

public class PlayerRelationController {

//    private static final Logger log = LoggerFactory.getLogger(PlayerRelationController.class); // what is this?!
    private ActionMenuView view;

    public PlayerRelationController(ActionMenuView view) {
        this.view = view;
    }

    public void friendships() {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Player> players = game.getPlayers();

        StringBuilder output = new StringBuilder();
        output.append("Your friendships:\n\n");

        for (Player player : players) {
            if (player != currentPlayer) {
                PlayerRelation relation = game.getPlayerRelation(player, currentPlayer);
                output.append(String.format("%s: XP: %d | level: %d",
                        player.getName(), relation.getXp(), relation.getFriendshipLevel()));

                if (relation.areMarried()) {
                    output.append(" | married\n");
                } else {
                    output.append("\n");
                }
            }
        }

        this.view.showMessage(output.toString());
    }

    public void talk(String username, String message) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You are talking with yourself!");
            return;
        }

        if (!arePlayersClose(currentPlayer, targetPlayer)) {
            this.view.showMessage("You are not close enough to talk with " + targetPlayer.getName());
            return;
        }

        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);

        // change XP
//        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
//        int xpDelta = sentimentAnalyzer.getXpDelta(message);
        relation.setXp(relation.getXp() + 20);
        this.view.showMessage(String.format("Your friendship XP with %s is now %d."
        , targetPlayer.getName(), relation.getXp()));

        // add talk to talk history
        relation.addTalk(message, currentPlayer);
    }

    public void talkHistory(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You cannot view talk history with yourself.");
            return;
        }

        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);

        // fetch talk history
        ArrayList<String> talkHistory = relation.getTalkHistory();

        // prepare the output
        StringBuilder output = new StringBuilder();
        output.append(String.format("Your talk history with %s:\n\n", targetPlayer.getName()));

        // loop in talk history
        for (String talk : talkHistory) {
            output.append(talk).append("\n");
        }

        this.view.showMessage(output.toString());
    }

    public void gift(String username, String item, String amountStr) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);
        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);

        int amount;

        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid number.");
            return;
        }

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("Gifting something to yourself is ok but not it this game!");
            return;
        }

        if (!arePlayersClose(currentPlayer, targetPlayer)) {
            this.view.showMessage("You are not close enough to gift the item to " + targetPlayer.getName());
            return;
        }

        if (relation.getFriendshipLevel() == 0) {
            this.view.showMessage("You must be on friendship level 1 to send gift.");
            return;
        }

        // get players' inventory
        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();

        // check the item
        ItemDefinition giftItemDefinition = App.getItemDefinition(item);
        if (giftItemDefinition == null) {
            this.view.showMessage("Item doesn't exist.");
            return;
        }

        if (giftItemDefinition.getType() == ItemType.tool) {
            this.view.showMessage("You can't gift your tools.");
            return;
        }

        // check the item in inventory
        if (amount > currentPlayerInventory.getItemAmount(giftItemDefinition.getId())) {
            this.view.showMessage(String.format("You don't have enough %s to gift.",
                    giftItemDefinition.getDisplayName()));
            return;
        }

        // check the receiver's inventory capacity
        if (!targetPlayerInventory.hasCapacity()
            && !targetPlayerInventory.hasItem(giftItemDefinition.getId())) {
            this.view.showMessage("Player's inventory doesn't have enough capacity.");
            return;
        }

        // fetch items from donor's inventory
        ArrayList<ItemInstance> giftItems = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            giftItems.add(currentPlayerInventory.getItem(giftItemDefinition.getId()));
        }

        System.out.println("testing:");
        for (ItemInstance x : giftItems) System.out.println(x.getDefinition().getDisplayName());

        if (giftItems.isEmpty()) {
            this.view.showMessage("Unknown error. PlayerRelationController -> line 197");
            return;
        }

        // add items to receiver's inventory
        for (ItemInstance giftItem : giftItems) {
            boolean successfullyAdded = targetPlayerInventory.addItem(giftItem);
            if (!successfullyAdded) {
                this.view.showMessage("Error occurred.");
                return;
            }
        }
        // save the gift in gift history
        Gift newGift = new Gift(targetPlayer, currentPlayer, giftItems);
        targetPlayer.addGift(newGift);
        relation.addGift(newGift);

        // send a message to the targetPlayer
        String message = String.format("Player %s has gifted you %d %s. Please rate this gift if you are interested."
                                        , currentPlayer.getName(), amount, giftItemDefinition.getDisplayName());
        targetPlayer.addMessage(message);
        this.view.showMessage(String.format("Your gift has been successfully sent to %s", targetPlayer.getName()));
    }

    public void giftList() {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        ArrayList<Gift> gifts = player.getGiftsReceived();

        if (gifts.isEmpty()) {
            this.view.showMessage("You didn't receive any gift yet.");
            return;
        }

        // prepare the output
        StringBuilder output = new StringBuilder();
        output.append("Your received gifts:\n\n");

        // loop in gifts
        int counter = 1;
        for (Gift gift : gifts) {
            String donorName = gift.getDonor().getName();
            String itemName = gift.getItemName();
            int itemAmount = gift.getItemAmount();
            int score = gift.getRate();

            output.append(counter++)
                  .append(". ")
                  .append(String.format("from %s, %d number of %s. Your score to it: %s",
                    donorName, itemAmount, itemName, score == -1 ? "not rated" : score))
                  .append("\n");

        }

        this.view.showMessage(output.toString());
    }

    public void giftRate(String giftNumberStr, String rateStr) {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();

        int giftNumber, rate;

        try {
            giftNumber = Integer.parseInt(giftNumberStr);
            rate = Integer.parseInt(rateStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid number.");
            return;
        }

        ArrayList<Gift> gifts = player.getGiftsReceived();
        if (giftNumber < 1 || giftNumber > gifts.size()) {
            this.view.showMessage("Gift not found.");
            return;
        }

        if (rate < 1 || rate > 5) {
            this.view.showMessage("Rate must be between 1 and 5");
            return;
        }

        Gift gift = gifts.get(giftNumber - 1);
        if (gift == null) {
            this.view.showMessage("Error occurred.");
            return;
        }

        if (!gift.setRate(rate)) {
            this.view.showMessage("You have rated this gift before!");
            return;
        }

        int friendshipXP = (rate - 3) * 30 + 15;
        PlayerRelation relation = game.getPlayerRelation(
                gift.getDonor(), gift.getReceiver());
        relation.setXp(relation.getXp() + friendshipXP);

        this.view.showMessage("You have successfully rated this gift.");

        Player donor = gift.getDonor();
        donor.addMessage(String.format("%s has rated %d to your gift (%d of %s) . your friendship XP is now %d.",
                player.getName(), rate, gift.getItemAmount(), gift.getItemName(), relation.getXp()));
    }

    public void giftHistory(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You haven't given yourself a gift!");
            return;
        }

        PlayerRelation relations = game.getPlayerRelation(currentPlayer, targetPlayer);
        ArrayList<Gift> gifts = relations.getGifs();

        StringBuilder output = new StringBuilder();
        output.append(String.format("Your gifts history with %s:\n\n", targetPlayer.getName()));

        if (gifts.isEmpty()) {
            this.view.showMessage("You didn't give any gift to each other yet.");
            return;
        }

        for (Gift gift : gifts) {
            String donorName = gift.getDonor().getName();
            String receiverName = gift.getReceiver().getName();
            String itemName = gift.getItemName();
            int itemAmount = gift.getItemAmount();
            String rate = gift.getRate() != -1 ? Integer.toString(gift.getRate()) : "not rated.";

            output.append(String.format("From %s to %s: %d number of %s.\n",
                    donorName, receiverName, itemAmount, itemName));
            output.append("Receiver's rate: ").append(rate);
            output.append("\n-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void hug(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You are so kind with yourself!");
            return;
        }

        if (!arePlayersClose(currentPlayer, targetPlayer)) {
            this.view.showMessage(String.format("You are not close enough to %s.", targetPlayer.getName()));
            return;
        }


        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);

        if (relation.getNumberOfHugsInDay() <= 3) {
            relation.setXp(relation.getXp() + 60);
        } else {
            this.view.showMessage(String.format("You are over hugging %s!", targetPlayer.getName()));
            return;
        }

        try {
            TerminalAnimation.loadingAnimation("Hugging");
        } catch (InterruptedException e) {
            this.view.showMessage("Problem hugging the player.");
            return;
        }

        this.view.showMessage(String.format("Your friendship XP is now %d.", relation.getXp()));
        relation.increaseNumberOfHugsInDay();
    }

    public void flower(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You are so kind with yourself!");
            return;
        }

        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);
        if (relation.getFriendshipLevel() < 2) {
            this.view.showMessage("At least friendship level 2 needed to gift a flower.");
            return;
        }

        if (!arePlayersClose(currentPlayer, targetPlayer)) {
            this.view.showMessage(String.format("You are not close enough to %s.", targetPlayer.getName()));
            return;
        }

        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();

        if (!currentPlayerInventory.hasItem(ItemIDs.flower)) { // TODO
            this.view.showMessage("You don't have flower. Maybe next time!");
            return;
        }

        if (!targetPlayerInventory.hasCapacity()
            && !targetPlayerInventory.hasItem(ItemIDs.flower)) { // TODO
            this.view.showMessage(String.format("%s's backpack does not have enough capacity!",
                    targetPlayer.getName()));
            return;
        }

        // give the flower
        ItemInstance flower = currentPlayerInventory.getItem(ItemIDs.flower); // TODO
        targetPlayerInventory.addItem(flower);

        // send a message to receiver
        String message = String.format("Player %s has given you a flower!", currentPlayer.getName());
        targetPlayer.addMessage(message);

        if (relation.getFriendshipLevel() == 2 && relation.getXp() == 599) {
            relation.setXp(600);
            this.view.showMessage("Your are now on friendship level 3!");
        } else {
            relation.setXp(relation.getXp() + 80);
            this.view.showMessage(String.format("Your friendship XP is now %d.", relation.getXp()));
        }

        relation.setFlowerGifted(true);

        try {
            TerminalAnimation.loadingAnimation("Giving the flower");
        } catch (InterruptedException e) {
            this.view.showMessage("Problem giving the flower.");
        }
    }

    public void askMarriage(String username) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Player targetPlayer = game.getPlayerByUsername(username);

        if (targetPlayer == null) {
            this.view.showMessage("Player not found.");
            return;
        }

        if (targetPlayer == currentPlayer) {
            this.view.showMessage("You can't marry yourself… yet.");
            return;
        }

        if (currentPlayer.getGender() == Gender.OTHER) {
            this.view.showMessage("Others can not marry!");
            return;
        }

        if (currentPlayer.getGender() == Gender.FEMALE) {
            this.view.showMessage("Sorry, but you need to be Male to propose!");
            return;
        }
        if (targetPlayer.getGender() != Gender.FEMALE) {
            this.view.showMessage("We do not support LGBT yet.");
            return;
        }

        PlayerRelation relation = game.getPlayerRelation(currentPlayer, targetPlayer);
        if (relation.areMarried()) {
            this.view.showMessage("Your are proposing your wife!");
            return;
        }

        if (relation.getFriendshipLevel() < 3) {
            this.view.showMessage("You need to be on friendship level 3 to be able to propose.");
            return;
        }

        if (!arePlayersClose(currentPlayer, targetPlayer)) {
            this.view.showMessage(String.format("You are not close enough to %s.", targetPlayer.getName()));
            return;
        }


        Inventory currentPlayerInventory = currentPlayer.getInventory();
        Inventory targetPlayerInventory = targetPlayer.getInventory();

        if (!currentPlayerInventory.hasItem(ItemIDs.wedding_ring)) {
            this.view.showMessage("You even don't have a ring! Please think twice.");
            return;
        }

        if (!targetPlayerInventory.hasCapacity()
            && !targetPlayerInventory.hasItem(ItemIDs.wedding_ring)) {
            this.view.showMessage(String.format("%s's backpack doesn't have capacity.", targetPlayer.getName())); // TODO: it's a joke!
            return;
        }

        // change the turn to the target player and get him/her respond
        game.setTurn(targetPlayer);
        String respond = this.view.prompt(String.format("%s has proposed you. (accept | reject)", currentPlayer.getName()));

        if (respond.equalsIgnoreCase("accept")) {
            relation = game.getPlayerRelation(currentPlayer, targetPlayer);
            relation.setAreMarried(true);
            Wallet newWallet = new Wallet(
                    currentPlayer.getWallet().getCoin() + targetPlayer.getWallet().getCoin());
            currentPlayer.setWallet(newWallet);
            targetPlayer.setWallet(newWallet);

            ItemInstance ring = currentPlayerInventory.getItem(ItemIDs.wedding_ring);
            targetPlayerInventory.addItem(ring);

            relation.setXp(1000);
            currentPlayer.setSpouse(targetPlayer);
            targetPlayer.setSpouse(currentPlayer);

            this.view.showMessage("congratulations on your marriage!");

        } else {
            relation.setXp(0);
            currentPlayer.setRejectedDays(7);
            this.view.showMessage("We are sorry, just try to move on, maybe the next time...");
        }

        game.setTurn(currentPlayer);
    }

    private boolean arePlayersClose(Player player1, Player player2) {
        int y1 = (int) player1.getPosition().getY();
        int x1 = (int) player1.getPosition().getX();
        int y2 = (int) player2.getPosition().getY();
        int x2 = (int) player2.getPosition().getX();

        if (y1 >= y2 - 1 && y1 <= y2 + 1
            && x1 >= x2 - 1 && x1 <= x2 + 1) {
            return true;
        }
        return false;
    }
}
