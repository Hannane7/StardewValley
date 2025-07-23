package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Main;
import com.untildawn.controllers.InGameControllers.GameControllers.GameController;
import com.untildawn.controllers.InGameControllers.ToolController;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.InGameAssetManager;
import com.untildawn.models.AssetManager.InventoryAssetManager;
import com.untildawn.models.AssetManager.ToolAssetManager;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.MessageCenter.InGameMessage;
import com.untildawn.models.Players.Player;

import java.util.Objects;

public class GameView implements InputProcessor, Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private Player player;
    private GameController controller;
    private Stage uiStage;
    private Table inventoryTable;
    private boolean isInventoryOpen = false;
    private Table fullInventoryTable;
    private Table toolTable;
    private InGameMessage message;
    private TimeWeatherView timeWeatherHud;

    public GameView(GameController controller) {
        camera = new OrthographicCamera();
        viewport = new FillViewport(600, 400, camera);

        shapeRenderer = new ShapeRenderer();

        this.controller = controller;
        controller.setView(this);

        game = App.getCurrentGame();
        player = game.getCurrentPlayer();
        uiStage = new Stage(new ScreenViewport());

        map = game.getMap();
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        inventoryTable = new Table();
        fullInventoryTable = new Table();
        toolTable = new Table();
        message = new InGameMessage(uiStage, InGameAssetManager.getSkin());
        System.out.println(App.getCurrentGame().getCurrentPlayer().getCottagePosition());
        timeWeatherHud = new TimeWeatherView(Main.getBatch(), InGameAssetManager.getSkin());
    }

    private void updateCamera() {
        Player player = game.getCurrentPlayer();
        float playerX = player.getPosition().getX();
        float playerY = player.getPosition().getY();
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();
        camera.position.set(player.getPosition().getX(), player.getPosition().getY(), 0);
        clampCameraToMap();
        camera.update();
    }

    private void clampCameraToMap() {
        MapProperties props = map.getProperties();
        int mapWidthInTiles = props.get("width", Integer.class);
        int mapHeightInTiles = props.get("height", Integer.class);
        int tilePixelWidth = props.get("tilewidth", Integer.class);
        int tilePixelHeight = props.get("tileheight", Integer.class);

        float mapWidth = mapWidthInTiles * tilePixelWidth;
        float mapHeight = mapHeightInTiles * tilePixelHeight;

        float halfCamWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float halfCamHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float minX = halfCamWidth;
        float minY = halfCamHeight;
        float maxX = mapWidth - halfCamWidth;
        float maxY = mapHeight - halfCamHeight;

        camera.position.x = MathUtils.clamp(camera.position.x, minX, maxX);
        camera.position.y = MathUtils.clamp(camera.position.y, minY, maxY);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        updateCamera();
        timeWeatherHud.update(game);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        Main.getBatch().begin();
        if(player.isFainted()) player.setSprite(new Sprite(InGameAssetManager.getFaintedPlayer()));
        else player.setSprite(new Sprite(InGameAssetManager.getInGameAssetManager().getCharacterTexture()));
        player.getSprite().setPosition(player.getPosition().getX(), player.getPosition().getY());

        player.getSprite().draw(Main.getBatch());
        player.getCollisionRect().move(player.getPosition().getX(), player.getPosition().getY());
        if (player.getItemInHandSprite() != null) {
            player.getItemInHandSprite().setPosition(player.getPosition().getX(), player.getPosition().getY());
            player.getItemInHandSprite().draw(Main.getBatch());
        }
        uiStage.act(delta);
        uiStage.draw();

        Main.getBatch().end();
        uiStage.act(delta);
        uiStage.draw();

        timeWeatherHud.draw();


    }

    @Override
    public boolean keyDown(int keycode) {
        // چیت‌کد برای جلو بردن یک ساعت
        if (keycode == Input.Keys.P) {
            game.getDateTime().updateTimeByHour(1);
            System.out.println("Time advanced by 1 hour. Current time: " + game.getDateTime().getHour());
            return true; // ورودی پردازش شد
        }

        // چیت‌کد برای جلو بردن یک روز
        if (keycode == Input.Keys.O) {
            // متد updateByDay از قبل در منطق بازی شما وجود داشت
            game.updateByDay(true); // isCheat = true
            System.out.println("Day advanced. Current day: " + game.getDateTime().getDay() + " of " + game.getDateTime().getSeason().name());
            return true; // ورودی پردازش شد
        }

        // چیت‌کد برای افزایش انرژی (کلید فلش بالا)
        if (keycode == Input.Keys.UP) {
            Player player = game.getCurrentPlayer();
            player.increaseEnergy(20);
            return true;
        }

        // چیت‌کد برای کاهش انرژی (کلید فلش پایین)
        if (keycode == Input.Keys.DOWN) {
            Player player = game.getCurrentPlayer();
            player.decreaseEnergy(20);
            return true;
        }


        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Player player = game.getCurrentPlayer();
        ItemInstance currentItem = player.getCurrentItem();
        if (button == Input.Buttons.LEFT && currentItem != null &&
            currentItem.getDefinition().getType().equals(ItemType.tool)) {
            ToolController toolController = controller.getToolController();

            Vector3 worldCoords = new Vector3(screenX, screenY, 0);
            camera.unproject(worldCoords);

            MapProperties props = map.getProperties();
            int tileWidth = props.get("tilewidth", Integer.class);
            int tileHeight = props.get("tileheight", Integer.class);

            int tileX = (int) (worldCoords.x / tileWidth);
            int tileY = (int) (worldCoords.y / tileHeight);
            Tile selectedTile = game.getGameMap().getTile(tileY, tileX);
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

            TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
            toolController.applyTool(currentItem, selectedTile, player, game, cell);
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        controller.getPlayerController().handleWeaponRotation(i, i1);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true); // <-- این خط را برای uiStage اضافه کنید (مهم)
        timeWeatherHud.resize(width, height);
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
        map.dispose();
        mapRenderer.dispose();
        shapeRenderer.dispose();
        uiStage.dispose();
        timeWeatherHud.dispose();
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public Table getInventoryTable() {
        return inventoryTable;
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public Table getFullInventoryTable() {
        return fullInventoryTable;
    }

    public void setIsInventoryOpen(boolean isInventoryOpen) {
        this.isInventoryOpen = isInventoryOpen;
    }

    public Table getToolTable() {
        return toolTable;
    }

    public void setToolTable(Table toolTable) {
        this.toolTable = toolTable;
    }

    public InGameMessage getMessage() {
        return message;
    }
}
