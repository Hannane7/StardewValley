package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.GameMenuCommands;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.GameMenuController;
import com.untildawn.models.AssetManager.AvatarAssetManager;
import com.untildawn.models.AssetManager.PreGameAssetManager;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuView implements Screen {
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Texture backgroundTexture;
    private String errorMessage;
    private Label gameMenuTitle;
    private GameMenuController controller;
    private TextButton newGameButton;
    private TextButton exitButton;
    private TextButton loadGameButton;
    private boolean isNewGameClicked = true;
    private TextField usernameField;
    private TextButton addPlayerButton;
    private TextButton backButton;
    private TextButton selectMapButton;
    private boolean isInMapSelection = false;
    private Texture map1;
    private Texture map2;
    private Texture map3;
    private Texture map4;

    public GameMenuView(GameMenuController controller) {
        this.controller = controller;
        controller.setView(this);
        table = new Table();
        font = new BitmapFont();
        newGameButton = new TextButton("New Game", PreGameAssetManager.getSkin());
        exitButton = new TextButton("Exit", PreGameAssetManager.getSkin());
        loadGameButton = new TextButton("Load Game", PreGameAssetManager.getSkin());
        gameMenuTitle = new Label("Game Menu", PreGameAssetManager.getSkin());
        backgroundTexture = PreGameAssetManager.getGameMenuBG();
        usernameField = new TextField("", PreGameAssetManager.getSkin());
        addPlayerButton = new TextButton("Add Player", PreGameAssetManager.getSkin());
        backButton = new TextButton("Back", PreGameAssetManager.getSkin());
        selectMapButton = new TextButton("Select Maps", PreGameAssetManager.getSkin());
        map1 = AvatarAssetManager.getAbigail();
        map2 = AvatarAssetManager.getAbigail();
        map3 = AvatarAssetManager.getAbigail();
        map4 = AvatarAssetManager.getAbigail();
    }


    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameMenuTitle.setFontScale(1.9f);
        this.font.setColor(Color.WHITE);
        stage.setKeyboardFocus(table);
        table.setFillParent(true);
        table.clear();
        table.reset();
        table.center();
        if (isNewGameClicked) {
            usernameField.setMessageText("enter the player username");
            table.add(usernameField).width(600).padBottom(32f).height(80);
            table.row();
            table.add(addPlayerButton).width(600).padBottom(32f).height(80);
            table.row();
            if (controller.getGamePlayers().size() >= 4) {
                table.add(selectMapButton).width(600).padBottom(32f).height(80);
                table.row();
            }
            table.add(backButton).width(600).padBottom(32f).height(80);

        } else if (isInMapSelection) {
            table.clearChildren();
            stage.clear();

            Label label = new Label("Map Selection", PreGameAssetManager.getSkin());
            label.setFontScale(2f);
            label.setColor(Color.GREEN);
            table.add(label);
            // Create Image actors for each map
            Image imageMap1 = new Image(map1);
            Image imageMap2 = new Image(map2);
            Image imageMap3 = new Image(map3);
            Image imageMap4 = new Image(map4);

            TextButton selectMap1 = new TextButton("Select Map 1", PreGameAssetManager.getSkin());
            TextButton selectMap2 = new TextButton("Select Map 2", PreGameAssetManager.getSkin());
            TextButton selectMap3 = new TextButton("Select Map 3", PreGameAssetManager.getSkin());
            TextButton selectMap4 = new TextButton("Select Map 4", PreGameAssetManager.getSkin());

            int imageWidth = 300;
            int imageHeight = 200;
            int padding = 100;

            imageMap1.setBounds(padding, Gdx.graphics.getHeight() - imageHeight - padding, imageWidth, imageHeight);
            selectMap1.setSize(imageWidth, 50);
            selectMap1.setPosition(padding, imageMap1.getY() - 60);

            imageMap2.setBounds(Gdx.graphics.getWidth() - imageWidth - padding,
                Gdx.graphics.getHeight() - imageHeight - padding, imageWidth, imageHeight);
            selectMap2.setSize(imageWidth, 50);
            selectMap2.setPosition(imageMap2.getX(), imageMap2.getY() - 60);

            imageMap3.setBounds(padding, padding + 100, imageWidth, imageHeight);
            selectMap3.setSize(imageWidth, 50);
            selectMap3.setPosition(padding, padding + 40);

            imageMap4.setBounds(Gdx.graphics.getWidth() - imageWidth - padding, padding + 100, imageWidth, imageHeight);
            selectMap4.setSize(imageWidth, 50);
            selectMap4.setPosition(imageMap4.getX(), padding + 40);

            stage.addActor(imageMap1);
            stage.addActor(selectMap1);

            stage.addActor(imageMap2);
            stage.addActor(selectMap2);

            stage.addActor(imageMap3);
            stage.addActor(selectMap3);

            stage.addActor(imageMap4);
            stage.addActor(selectMap4);
            selectMap1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.createMapSelectListener(1);
                }
            });
            selectMap2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.createMapSelectListener(2);
                }
            });
            selectMap3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.createMapSelectListener(3);
                }
            });
            selectMap4.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.createMapSelectListener(4);
                }
            });
        } else {
            table.add(newGameButton).width(600).padBottom(32f).height(80);
            table.row();
            table.add(loadGameButton).width(600).padBottom(32f).height(80);
            table.row();
            table.add(exitButton).width(600).padBottom(32f).height(80);
            table.row();
        }

        stage.addActor(table);
        controller.setListener();

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1f);

        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (isInMapSelection)
            setErrorMessage("Player " + controller.getGamePlayers().get(controller.getCounter()).getName() + ": ");
        font.getData().setScale(2f);
        if (errorMessage != null && !errorMessage.isEmpty()) {
            font.draw(Main.getBatch(), errorMessage, Gdx.graphics.getWidth() / 2 - errorMessage.length() * 5, Gdx.graphics.getHeight() - 50);
        }
        Main.getBatch().end();
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public void setNewGameClicked(boolean newGameClicked) {
        isNewGameClicked = newGameClicked;
        isInMapSelection = false;
        show();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextButton getAddPlayerButton() {
        return addPlayerButton;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                GameMenuView.this.errorMessage = null;
            }
        }, 3);
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public boolean isNewGameClicked() {
        return isNewGameClicked;
    }

    public TextButton getSelectMapButton() {
        return selectMapButton;
    }

    public boolean isInMapSelection() {
        return isInMapSelection;
    }

    public void setInMapSelection(boolean inMapSelection) {
        isInMapSelection = inMapSelection;
        isNewGameClicked = false;
        show();
    }



}
