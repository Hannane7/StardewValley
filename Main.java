package com.untildawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.controllers.PreGameControllers.GameMenuController;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.PreGameAssetManager;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Items.ItemLoader;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.PrepareMap;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Question;
import com.untildawn.models.User;

public class Main extends Game {
    private static SpriteBatch batch;
    private static Main main;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        PreGameAssetManager.load();
        PreGameAssetManager.manager.finishLoading();
        PreGameAssetManager.createAnimations();

        User ali1 = new User("ali1", "ali1", "ali1", "ali1", Gender.OTHER, new Question("ali1", "ali1"));
        User ali2 = new User("ali2", "ali2", "ali2", "ali2", Gender.OTHER, new Question("ali2", "ali2"));
        User ali3 = new User("ali3", "ali3", "ali3", "ali3", Gender.OTHER, new Question("ali3", "ali3"));
        User ali4 = new User("ali4", "ali4", "ali4", "ali4", Gender.OTHER, new Question("ali4", "ali4"));
        App.addUser("ali1", ali1);
        App.addUser("ali2", ali2);
        App.addUser("ali3", ali3);
        App.addUser("ali4", ali4);
        ItemLoader.loadItems();
        App.setCurrentUser(ali1);
        GameMenuController gameMenuController = new GameMenuController();
        gameMenuController.getGamePlayers().add(new Player(ali1, ali1.getUsername(), ali1.getGender(), new Position(0, 0)));
        gameMenuController.getGamePlayers().add(new Player(ali2, ali2.getUsername(), ali2.getGender(), new Position(0, 0)));
        gameMenuController.getGamePlayers().add(new Player(ali3, ali4.getUsername(), ali3.getGender(), new Position(0, 0)));
        gameMenuController.getGamePlayers().add(new Player(ali4, ali4.getUsername(), ali4.getGender(), new Position(0, 0)));
        gameMenuController.setNewGameMap(PrepareMap.prepareMap());
        gameMenuController.setFarms(PrepareMap.loadPlayerMaps(gameMenuController.getNewGameMap()));
        gameMenuController.createMapSelectListener(1);
        gameMenuController.createMapSelectListener(2);
        gameMenuController.createMapSelectListener(3);
        gameMenuController.createMapSelectListener(4);
//        main.setScreen(Menus.PreGameMenus.GAME_MENU.getMenu());
//        App.setCurrentMenu(Menus.PreGameMenus.GAME_MENU);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.end();
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Main getMain() {
        return main;
    }
}
