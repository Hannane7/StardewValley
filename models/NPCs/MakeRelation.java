package com.untildawn.models.NPCs;

import com.untildawn.models.Game;
import com.untildawn.models.Players.Player;


import java.util.ArrayList;

public class MakeRelation {
    public static void makeRelations(Game game) {
        ArrayList<Player> players = game.getPlayers();
        ArrayList<NPC> NPCs = game.getNPCs();
        ArrayList<NPCRelation>
                relations = new ArrayList<>();

        for (Player player : players) {
            for (NPC npc : NPCs) {
                NPCRelation relation = new NPCRelation(player, npc);
                relations.add(relation);
            }
        }

        game.setRelations(relations);
    }
}
