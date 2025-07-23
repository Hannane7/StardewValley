package com.untildawn.controllers.UpdateControllers;
import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.controllers.InGameControllers.AnimalController;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ShippingBin;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import java.util.ArrayList;
import java.util.Map;

public class UpdateByDay {
    private Game game;

    public UpdateByDay(Game game) {
        this.game = game;

    }





    public void execute(boolean isCheat) {
        game.getWeather().setCurrentWeather(game.getTomorrowWeather().getCurrentWeather());
        game.setTomorrowWeather();
        waterTiles();
        emptyShippingBin();
        UpdateForaging.deleteForaging(game);
        UpdateForaging.updateForaging(game);
        setPlayersPositionAndEnergy();
        UpdateFarming.updateAllCrops(game);
        RandomEvents.crowAttack(game);
        RandomEvents.strikeLightning(game);
        UpdateShops.updateShops(game);
        UpdateNPC.updateDaysToLastQuest(game);
        UpdateNPC.getGifts(game);

        AnimalController.addProductToAnimal(game);
        UpdateFarming.updateAllCrops(game);
        RandomEvents.crowAttack(game);
        RandomEvents.strikeLightning(game);
        UpdateShops.updateShops(game);
        if (!isCheat) {
            ArtisanUpdate.artisanWithDay(1);
        }
        AnimalController.addProductToAnimal(game);
        game.getDateTime().updateTimeByDay(1);
    }


    private void emptyShippingBin() {
        ShippingBin shippingBin = game.getShippingBin();
        for (Map.Entry<Player, Integer> entry : shippingBin.getPlayerSoldItemsPrice().entrySet()) {
            Player player = entry.getKey();
            int price = shippingBin.getPriceAndSetBinEmpty(player);
            player.getWallet().increaseCoin(price);
        }
    }

    private void setPlayersPositionAndEnergy() {
        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            if (player.isFainted()) {
                player.setEnergy((int) (0.75 * player.getEnergyLimit()));
            } else {
                player.setPosition(player.getCottagePosition());
                player.setEnergy(player.getEnergyLimit());
            }
            if (player.getRejectedDays() > 0) {
                player.setEnergy(player.getEnergy() / 2);
                player.setRejectedDays(player.getRejectedDays() - 1);
            }
            player.setFainted(false);
            player.setEnergyPerTurn(50);
        }
    }
    public void waterTiles() {
        if(game.getWeather().getCurrentWeather().equals(WeatherStates.RAIN)) {
            for (Tile[] tiles : game.getGameMap().getMap()) {
                for (Tile tile : tiles) {
                    tile.setWatered(true);
                }
            }
        }
    }

}
