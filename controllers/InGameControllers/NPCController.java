package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.NPCs.NPC;
import com.untildawn.models.NPCs.NPCRelation;
import com.untildawn.models.NPCs.Quest;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;


import java.util.ArrayList;

public class NPCController {
    ActionMenuView view;

    public NPCController(ActionMenuView view) {
        this.view = view;
    }

    public void meetNPC(String NPCName) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();

        NPC npc = game.getNPC(NPCName);
        if (npc == null) {
            this.view.showMessage("NPC doesn't exist.");
            return;
        }

        if (!isNPCClose(npc)) {
            this.view.showMessage(String.format("You are not close enough to talk with %s", NPCName));
            return;
        }

        if (NPCName.equalsIgnoreCase("Kian")) {
            this.view.showMessage("Bashe dash Ali mizanam!");
            return;
        }

        String NPCRespond = DialogController.getDialog(NPCName, "StartingConversation"); // should be changed
        this.view.showMessage(NPCRespond);
        if (NPCRespond.equals("NPC not found.")) return;

        // increase friendship points
        NPCRelation relation = game.getRelation(currentPlayer, npc);
        if (relation.isFirstMeetInDay()) relation.setFriendShipPoints(relation.getFriendShipPoints() + 20);
        relation.setFirstMeetInDay(false);
        this.view.showMessage(String.format("Your friendship points with %s is now %d",
                NPCName, relation.getFriendShipPoints()));
    }

    public void giftNPC(String NPCName, String item) {
        ItemDefinition itemDefinition = App.getItemDefinition(item);
        if (itemDefinition == null) {
            this.view.showMessage("Item doesn't exist.");
            return;
        }

        Player player = App.getCurrentGame().getCurrentPlayer();
        Inventory inventory = player.getInventory();

        if (!inventory.hasItem(itemDefinition.getId())) {
            this.view.showMessage("You don't have this item.");
            return;
        }

        if (itemDefinition.getType() == ItemType.tool) {
            this.view.showMessage("You can't give your tool to NPC.");
            return;
        }

        Game game = App.getCurrentGame();
        NPC npc = game.getNPC(NPCName);
        if (npc == null) {
            this.view.showMessage("NPC doesn't exist.");
            return;
        }

        if (!isNPCClose(npc)) {
            this.view.showMessage(String.format("You are not close enough to give the gift to %s", NPCName));
            return;
        }

        if (NPCName.equalsIgnoreCase("Kian")) {
            this.view.showMessage("Bashe dash Ali mizanam!");
            return;
        }


        // delete item from inventory
        inventory.trashItem(itemDefinition.getId(), 1);
        // show dialog
        String NPCRespond;
        int points = 0;
        if (npc.hasFavorite(itemDefinition)) {
            NPCRespond = DialogController.getDialog(NPCName, "ThanksForGivingFavoriteGifts");
            points += 200;
        } else {
            NPCRespond = DialogController.getDialog(NPCName, "ThanksForGivingUnFavoriteGifts");
        }
        this.view.showMessage(NPCRespond);

        // increase friendship points
        NPCRelation relation = game.getRelation(player, npc);
        if (relation.isFirstGiftInDay()) {
            points += 50;
        }
        relation.setFriendShipPoints(relation.getFriendShipPoints() + points);
        this.view.showMessage(String.format("Your friendship points with %s is now %d",
                NPCName, relation.getFriendShipPoints()));
    }

    public void friendshipNPCList() {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();

        StringBuilder output = new StringBuilder();

        for (NPC npc : game.getNPCs()) {
            NPCRelation relation = game.getRelation(currentPlayer, npc);
            output.append(currentPlayer.getName()).append(" & ").append(npc.getName()).append("\n");
            output.append("Friendship points: ").append(relation.getFriendShipPoints())
                    .append(" | ")
                    .append("Friendship level: ").append(relation.getFriendShipLevel()).append("\n");
            output.append("-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void questsList() {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Quest> playerQuests = currentPlayer.getActiveQuests();

        StringBuilder output = new StringBuilder();
        int counter = 1;
        for (Quest quest : playerQuests) {
            output.append("Quest number ").append(counter++).append("\n");
            output.append("For ").append(quest.getNpc().getName()).append("\n");
            output.append(String.format("You have to give %d %s to %s\n",
                    quest.getRequestAmount(), quest.getRequest().getDisplayName(), quest.getNpc().getName()));
            output.append("-------------------------------------\n");
        }

        this.view.showMessage(output.toString());
    }

    public void getQuestFromNPC(String NPCName) {
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();

        NPC npc = game.getNPC(NPCName);
        if (npc == null) {
            this.view.showMessage("NPC doesn't exist.");
            return;
        }

        if (!isNPCClose(npc)) {
            this.view.showMessage(String.format("You are not close enough to get the quest from %s", NPCName));
            return;
        }

        if (NPCName.equalsIgnoreCase("Kian")) {
            this.view.showMessage("Bashe dash Ali mizanam!");
            return;
        }

        NPCRelation relation = game.getRelation(currentPlayer, npc);
        int availableQuestNumber = relation.getAvailableQuestNumber();

        if (availableQuestNumber == 2 && relation.getFriendShipLevel() == 0) {
            this.view.showMessage("You have to be on friendship level 1 to get the quest number 2.");
            return;
        }

        if (availableQuestNumber == 3 && relation.getDaysToLastQuest() > 0) {
            this.view.showMessage(String.format("Not any available quests. %d days to next quest.",
                    relation.getDaysToLastQuest()));
            return;
        }

        Quest newQuest = npc.getQuest(availableQuestNumber);

        if (newQuest == null) {
            this.view.showMessage("Not any available quest.");
            return;
        }


        currentPlayer.addQuest(newQuest);

        String output = "New quest added successfully:\n" +
                String.format("You should give %d %s to %s\n",
                        newQuest.getRequestAmount(), newQuest.getRequest().getDisplayName(), NPCName);
        this.view.showMessage(output);
    }

    public void questFinish(String numberStr) {
        int number;
        try {
            number = Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            this.view.showMessage("Please enter a valid index.");
            return;
        }
        Game game = App.getCurrentGame();
        Player currentPlayer = game.getCurrentPlayer();
        Inventory inventory = currentPlayer.getInventory();

        Quest quest = currentPlayer.getActiveQuest(number);

        if (quest == null) {
            this.view.showMessage("You don't have the quest.");
            return;
        }

        NPC npc = quest.getNpc();
        ItemDefinition itemDefinition = quest.getRequest();
        int amount = quest.getRequestAmount();

        if (npc == null) {
            this.view.showMessage("NPC doesn't exist.");
            return;
        }

        if (!isNPCClose(npc)) {
            this.view.showMessage(String.format("You are not close enough to get the quest from %s", npc.getName()));
            return;
        }

        if (npc.getName().equalsIgnoreCase("Kian")) {
            this.view.showMessage("Bashe dash Ali mizanam!");
            return;
        }

        if (amount > inventory.getItemAmount(itemDefinition.getId())) {
            this.view.showMessage(String.format("You don't have enough %s to complete this quest.",
                    itemDefinition.getDisplayName()));
            return;
        }

        String NPCRespond = DialogController.getDialog(npc.getName(), "CompletingQuest");
        this.view.showMessage(NPCRespond);

        int requestedAmount = quest.getRequestAmount();
        ArrayList<ItemInstance> itemInstance = inventory.getItem(itemDefinition.getId(), requestedAmount);

        int rewardAmount = quest.getRewardAmount();
        ItemDefinition rewardItemDefinition = quest.getReward();

        NPCRelation relation = game.getRelation(currentPlayer, npc);

        if (relation.getFriendShipLevel() >= 2) {
            rewardAmount *= 2;
        }

        currentPlayer.finishQuest(quest);

        if (relation.getAvailableQuestNumber() == 2) {
            relation.setDaysToLastQuest(npc.getDaysToLastQuest());
        }

        relation.setAvailableQuestNumber(relation.getAvailableQuestNumber() + 1);

        if (rewardItemDefinition.getId() == ItemIDs.gold_coin) {
            currentPlayer.getWallet().increaseCoin(rewardAmount);
        }

        if (rewardItemDefinition.getId() == ItemIDs.friendship_level) {
            int newPoints;
            int level = relation.getFriendShipLevel();
            if (level == 3) {
                newPoints = 799;
            } else if (level < 3) {
                newPoints = (level + 1) * 200;
            } else {
                this.view.showMessage("Unknown error, NPCController -> line 230");
                return;
            }

            relation.setFriendShipPoints(newPoints);

            this.view.showMessage(String.format("Your friendship level with %s is now %d.",
                    npc.getName(), relation.getFriendShipLevel()));

            return;
        }

        for (int i = 0; i < rewardAmount; i++) {
            inventory.addItem(new ItemInstance(rewardItemDefinition));
        }
        this.view.showMessage(String.format("%s has given you %d %s.",
                npc.getName(), rewardAmount, rewardItemDefinition.getDisplayName()));
        ;
    }

    private boolean isNPCClose(NPC npc) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        int playerY = (int) player.getPosition().getY();
        int playerX = (int) player.getPosition().getX();
        int NPcY = (int) npc.getPosition().getY();
        int NPcX = (int) npc.getPosition().getX();

        if (!(NPcY >= playerY - 1 && NPcY <= playerY + 1
                && NPcX >= playerX - 1 && NPcX <= playerX + 1)) {
            return false;
        }
        return true;
    }


}
