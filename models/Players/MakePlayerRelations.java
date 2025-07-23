package com.untildawn.models.Players;

import com.untildawn.models.App;
import com.untildawn.models.Game;
import java.util.ArrayList;

public class MakePlayerRelations {

    public static void makePlayerRelations() {

        Game game = App.getCurrentGame();
        ArrayList<Player> players = game.getPlayers();

        ArrayList<PlayerRelation> playerRelations =  new ArrayList<>();

        for (int i = 0; i < players.size() - 1; i++) {
            for (int j = i + 1; j < players.size(); j++) {
                PlayerRelation relation = new PlayerRelation(
                        players.get(i), players.get(j));

                playerRelations.add(relation);
            }
        }

        game.setPlayerRelations(playerRelations);
    }
}
