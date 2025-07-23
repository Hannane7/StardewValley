package com.untildawn.controllers.UpdateControllers;

import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.NPCs.NPC;
import com.untildawn.models.NPCs.NPCRelation;
import com.untildawn.models.Players.Player;

import java.util.ArrayList;

public class UpdateNPC {
    public static void updateDaysToLastQuest(Game game) {
        ArrayList<NPCRelation> relations = game.getRelations();
        for (NPCRelation relation : relations) {
            relation.setDaysToLastQuest(relation.getDaysToLastQuest() - 1);
        }
    }

    public static void getGifts(Game game) {
        ArrayList<NPCRelation> NPCRelations = game.getRelations();
        for (NPCRelation npcRelation : NPCRelations) {
            if (npcRelation.getFriendShipLevel() >= 3) {
                Player player = npcRelation.getPlayer();
                NPC npc = npcRelation.getNpc();
                ArrayList<ItemDefinition> items = npc.getFavorites();
                ItemDefinition randomItem = items.get(GenerateRandomNumber.generateRandomNumber(0, items.size() - 1));
                player.getInventory().addItem(new ItemInstance(randomItem));
                if (!player.getInventory().hasCapacity() &&
                    !player.getInventory().hasItem(randomItem.getId())) {
                    continue;
                }
                player.addMessage(String.format("NPC %s has gifted you %s", npc.getName(), randomItem.getDisplayName()));
            }
        }
    }
}
