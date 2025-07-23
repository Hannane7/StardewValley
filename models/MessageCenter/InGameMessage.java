package com.untildawn.models.MessageCenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.untildawn.models.AssetManager.InGameAssetManager;

public class InGameMessage {
    private final Stage stage;
    private final Skin skin;
    private final Table messageTable;
    TextureRegionDrawable backgroundDrawable;

    public InGameMessage(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;

        messageTable = new Table();
        messageTable.top().left().pad(10f);
        messageTable.setFillParent(true);
        stage.addActor(messageTable);
        backgroundDrawable = new TextureRegionDrawable(InGameAssetManager.getWoodenBoard());
    }

    public void showMessage(String text) {
        Table messageContainer = new Table();
        messageContainer.setFillParent(true);
        stage.addActor(messageContainer);
        messageContainer.padTop(20f);

        Table topLeftTable = new Table();
        topLeftTable.top().left().padTop(30).padLeft(30);
        messageContainer.add(topLeftTable).expand().fill();

        Image bgImage = new Image(backgroundDrawable);
        bgImage.setSize(300, 100);

        Stack messageStack = new Stack();
        messageStack.add(bgImage);

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        Label messageLabel = new Label(text, style);
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);
        messageLabel.setFontScale(1.5f);

        Table labelTable = new Table();
        labelTable.add(messageLabel).width(260).center();
        messageStack.add(labelTable);

        topLeftTable.add(messageStack).size(300, 100);

        messageStack.addAction(Actions.sequence(
            Actions.delay(3f),
            Actions.fadeOut(0.5f),
            Actions.run(() -> messageContainer.remove())
        ));
    }

}

