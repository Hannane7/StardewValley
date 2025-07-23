package com.untildawn.controllers.InGameControllers.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.untildawn.Main;
import com.untildawn.models.App;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.Players.Player;

public class PlayerController {
    private Player player;

    public PlayerController() {
        player = App.getCurrentGame().getCurrentPlayer();
    }

    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        float dx = 0, dy = 0;
        float speed = player.getSpeed();

        if (Gdx. input.isKeyPressed(Input.Keys.A)) dx -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed;

        movePlayer(dx, dy);
    }

    private void movePlayer(float dx, float dy) {
        Position pos = player.getPosition();
        pos.setX(pos.getX() + dx);
        pos.setY(pos.getY() + dy);
    }

    public boolean canMoveTo(float newX, float newY) {

        return true;
    }

    public void handleWeaponRotation(int screenX, int screenY) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (player.getCurrentItem() == null) return;
        ItemInstance item = player.getCurrentItem();
        Texture texture = item.getTexture();
        player.setItemInHandSprite(new Sprite(texture));

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(screenY - weaponCenterY, screenX - weaponCenterX);

        player.getItemInHandSprite().setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }
}

