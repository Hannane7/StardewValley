package com.untildawn.controllers.UpdateControllers;

import com.untildawn.models.Game;


public class UpdateByHour {
    private Game game;

    public UpdateByHour(Game game) {
        this.game = game;
    }

    public void execute(boolean isCheat) {
        if (game.getDateTime().getHour() == 22) {
            game.updateByDay(isCheat);
        }
        if (!isCheat) {
            game.getDateTime().updateTimeByHour(1);
            ArtisanUpdate.artisanWithHour(1);
        }
        game.getDateTime().updateTimeByHour(1);
    }
}
