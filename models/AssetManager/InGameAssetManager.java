package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

public class InGameAssetManager implements GameAssetManager {
    private static Skin skin;
    private static InGameAssetManager inGameAssetManager;
    private Animation<Texture> characterAnimation = buildCharacterAnimation("character1", "walk");
    private Texture characterTexture = characterAnimation.getKeyFrames()[0];
    private static Texture woodenBoard = new Texture(Gdx.files.internal("Images/woodenBoard.png"));
    private static final Texture faintedPlayer = new Texture(Gdx.files.internal("character1/Faint1.png"));
    private InGameAssetManager() {
    }

    public static InGameAssetManager getInGameAssetManager() {
        if (inGameAssetManager == null) {
            inGameAssetManager = new InGameAssetManager();
        }
        return inGameAssetManager;
    }

    public static Skin getSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        }
        return skin;
    }

    public static Texture getFaintedPlayer() {
        return faintedPlayer;
    }



    private Animation<Texture> buildCharacterAnimation(String characterName, String type) {

        String pathToFolder = String.format("assets/%s/%s", characterName, type);
        int numberOfFrames = countFiles(pathToFolder);

        Texture[] textureArray = new Texture[numberOfFrames];

        for (int i = 1; i <= numberOfFrames; i++) {
            String rawPath = String.format("%s/%s/sam_%d.png", characterName, type, i);
            Texture texture = new Texture(rawPath);
            textureArray[i - 1] = texture;
        }

        return new Animation<>(0.1f, textureArray);
    }

    public Animation<Texture> getCharacterAnimation() {
        return characterAnimation;
    }

    public Texture getCharacterTexture() {
        return characterTexture;
    }

    private int countFiles(String path) {

        File folder = new File(path);


        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            int fileCount = 0;
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileCount++;
                }
            }
            return fileCount;
        } else {
            return 0;
        }
    }

    public static Texture getWoodenBoard() {
        return woodenBoard;
    }
}
