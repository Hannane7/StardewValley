package com.untildawn.controllers.InGameControllers.GameControllers;

import com.untildawn.controllers.InGameControllers.InventoryController;
import com.untildawn.controllers.InGameControllers.TimeWeatherEnergyController;
import com.untildawn.controllers.InGameControllers.ToolController;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.InGameMenus.GameView;
//اضافه کردن کنترلر های درون بازی
public class GameController {
    private final Game game;
    private final PlayerController playerController;
    private GameView view;
    private InventoryController inventoryController;
    private ToolController toolController;
    private TimeWeatherEnergyController tweController;



    public void setView(GameView view) {
        this.view = view;
        inventoryController.setGameView(view);
        toolController.setGameView(view);

    }

    public GameController() {
        game = App.getCurrentGame();
        playerController = new PlayerController();
        inventoryController = new InventoryController();
        toolController = new ToolController();
        tweController = new TimeWeatherEnergyController;

    }

    public void update(float delta) {
        playerController.update(delta);
        inventoryController.update();
        toolController.update();
        tweController.update();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public ToolController getToolController() {
        return toolController;
    }
}
