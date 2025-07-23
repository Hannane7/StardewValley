package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.MainMenuCommands;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.MainMenuController;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.PreGameAssetManager;
import com.untildawn.models.User;
import com.untildawn.views.AppMenu;

import java.util.Scanner;

public class MainMenuView implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private MainMenuController controller;
    private Texture backgroundTexture;
    private Array<Animation<TextureRegion>> birdAnimations;
    private final int BIRD_COUNT = 4;
    private Array<Vector2> birdPositions;
    private Array<Vector2> birdVelocities;
    private float[] birdStateTimes;
    private Random random = new Random();
    private Label titleLabel;
    private TextButton gameMenuButton;
    private TextButton profileMenuButton;
    private TextButton avatarMenuButton;
    private TextButton logoutButton;
    private TextButton exitButton;

    public MainMenuView(MainMenuController controller) {
        this.skin = PreGameAssetManager.getSkin();
        this.backgroundTexture = PreGameAssetManager.getMenusBG();
        this.controller = controller;
        controller.setView(this);
        this.stage = new Stage(new ScreenViewport());
        birdAnimations = new Array<>();
        birdAnimations.add(PreGameAssetManager.getBrownBirdAnimation());
        birdAnimations.add(PreGameAssetManager.getBlueBirdAnimation());
        birdAnimations.add(PreGameAssetManager.getWhiteBirdAnimation());
        birdAnimations.add(PreGameAssetManager.getRedBirdAnimation());
        this.birdPositions = new Array<>(BIRD_COUNT);
        this.birdVelocities = new Array<>(BIRD_COUNT);
        this.birdStateTimes = new float[BIRD_COUNT];
        titleLabel = new Label("Stardew Valley", skin, "title");
        gameMenuButton = new TextButton("Game Menu", skin);
        profileMenuButton = new TextButton("Profile Menu", skin);
        avatarMenuButton = new TextButton("Avatar Menu", skin);
        logoutButton = new TextButton("Logout", skin);
        exitButton = new TextButton("Exit Game", skin);


    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();


        table.add(titleLabel).padBottom(50);
        table.row();
        if (App.getCurrentUser().getAvatar() != null) {
            Image avatarImage = new Image(App.getCurrentUser().getAvatar());
            avatarImage.setSize(100, 100);
            table.add(avatarImage).width(100).height(100).padBottom(32f);
            table.row();
        }

        controller.setListeners();
        float buttonWidth = 300f;
        float buttonHeight = 50f;
        float pad = 10f;

        table.add(gameMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(profileMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(avatarMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(logoutButton).width(buttonWidth).height(buttonHeight).pad(pad).padTop(30);
        table.row();
        table.add(exitButton).width(buttonWidth).height(buttonHeight).pad(pad);
        stage.addActor(table);

    }

    private void initializeBirds() {
        birdPositions.clear();
        birdVelocities.clear();
        for (int i = 0; i < BIRD_COUNT; i++) {
            float startX = Gdx.graphics.getWidth() + 50 + random.nextFloat() * 200;
            float startY = Gdx.graphics.getHeight() * 0.6f + random.nextFloat() * (Gdx.graphics.getHeight() * 0.3f);
            float velocityX = -80 - random.nextFloat() * 60;

            birdPositions.add(new Vector2(startX, startY));
            birdVelocities.add(new Vector2(velocityX, 0));
            birdStateTimes[i] = random.nextFloat() * 10;
        }
    }

    private void updateBirds(float delta) {
        for (int i = 0; i < BIRD_COUNT; i++) {
            birdStateTimes[i] += delta;

            Vector2 pos = birdPositions.get(i);
            Vector2 vel = birdVelocities.get(i);
            pos.add(vel.x * delta, vel.y * delta);

            if (pos.x < -60) {
                pos.x = Gdx.graphics.getWidth() + 50;
                pos.y = Gdx.graphics.getHeight() * 0.6f + random.nextFloat() * (Gdx.graphics.getHeight() * 0.3f);
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        buildUI();
        initializeBirds();

    }

    @Override
    public void render(float delta) {
        updateBirds(delta);
        stage.act(delta);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < BIRD_COUNT; i++) {
            Animation<TextureRegion> currentAnimation = birdAnimations.get(i % birdAnimations.size);
            TextureRegion currentFrame = currentAnimation.getKeyFrame(birdStateTimes[i], true);
            Vector2 pos = birdPositions.get(i);
            Main.getBatch().draw(currentFrame, pos.x, pos.y, 64, 64);
        }
        Main.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
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

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public TextButton getAvatarMenuButton() {
        return avatarMenuButton;
    }

    public TextButton getProfileMenuButton() {
        return profileMenuButton;
    }

    public TextButton getGameMenuButton() {
        return gameMenuButton;
    }
}
