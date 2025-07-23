package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PreGameAssetManager {
    private static Skin skin;
    private static final Texture MenusBG = new Texture(Gdx.files.internal("Images/backgrounds/MenusBG.png"));
    private static final Texture gameMenuBG = new Texture(Gdx.files.internal("Images/backgrounds/GameMenuBG.png"));

    public static final AssetManager manager = new AssetManager();
    private static Animation<TextureRegion> brownBirdAnimation;
    private static Animation<TextureRegion> blueBirdAnimation;
    private static Animation<TextureRegion> whiteBirdAnimation;
    private static Animation<TextureRegion> redBirdAnimation;

    public static Skin getSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        }
        return skin;
    }

    public static Texture getMenusBG() {
        return MenusBG;
    }

    public static Texture getGameMenuBG() {
        return gameMenuBG;
    }

    // These are the correct paths you'll use everywhere
    private static final String BROWN_BIRD_1 = "Images/BirdAnimation/birds-2.png";
    private static final String BROWN_BIRD_2 = "Images/BirdAnimation/birds-3.png";
    private static final String BLUE_BIRD_1 = "Images/BirdAnimation/birds-12.png";
    private static final String BLUE_BIRD_2 = "Images/BirdAnimation/birds-13.png";
    private static final String WHITE_BIRD_1 = "Images/BirdAnimation/birds-22.png";
    private static final String WHITE_BIRD_2 = "Images/BirdAnimation/birds-23.png";
    private static final String RED_BIRD_1 = "Images/BirdAnimation/birds-32.png";
    private static final String RED_BIRD_2 = "Images/BirdAnimation/birds-33.png";

    public static void load() {
        manager.load(BROWN_BIRD_1, Texture.class);
        manager.load(BROWN_BIRD_2, Texture.class);
        manager.load(BLUE_BIRD_1, Texture.class);
        manager.load(BLUE_BIRD_2, Texture.class);
        manager.load(WHITE_BIRD_1, Texture.class);
        manager.load(WHITE_BIRD_2, Texture.class);
        manager.load(RED_BIRD_1, Texture.class);
        manager.load(RED_BIRD_2, Texture.class);
    }

    public static void createAnimations() {
        TextureRegion[] brownFrames = new TextureRegion[2];
        brownFrames[0] = new TextureRegion(manager.get(BROWN_BIRD_1, Texture.class));
        brownFrames[1] = new TextureRegion(manager.get(BROWN_BIRD_2, Texture.class));
        brownBirdAnimation = new Animation<>(0.15f, brownFrames);

        // --- Blue Bird ---
        TextureRegion[] blueFrames = new TextureRegion[2];
        blueFrames[0] = new TextureRegion(manager.get(BLUE_BIRD_1, Texture.class));
        blueFrames[1] = new TextureRegion(manager.get(BLUE_BIRD_2, Texture.class));
        blueBirdAnimation = new Animation<>(0.15f, blueFrames);

        TextureRegion[] whiteFrames = new TextureRegion[2];
        whiteFrames[0] = new TextureRegion(manager.get(WHITE_BIRD_1, Texture.class));
        whiteFrames[1] = new TextureRegion(manager.get(WHITE_BIRD_2, Texture.class));
        whiteBirdAnimation = new Animation<>(0.15f, whiteFrames);

        TextureRegion[] redFrames = new TextureRegion[2];
        redFrames[0] = new TextureRegion(manager.get(RED_BIRD_1, Texture.class));
        redFrames[1] = new TextureRegion(manager.get(RED_BIRD_2, Texture.class));
        redBirdAnimation = new Animation<>(0.15f, redFrames);
    }

    public static Animation<TextureRegion> getBrownBirdAnimation() {
        return brownBirdAnimation;
    }

    public static Animation<TextureRegion> getBlueBirdAnimation() {
        return blueBirdAnimation;
    }

    public static Animation<TextureRegion> getWhiteBirdAnimation() {
        return whiteBirdAnimation;
    }

    public static Animation<TextureRegion> getRedBirdAnimation() {
        return redBirdAnimation;
    }
}
