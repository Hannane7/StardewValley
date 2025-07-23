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
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.AvatarAssetManager;
import com.untildawn.models.AssetManager.PreGameAssetManager;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;

public class ProfileMenuView implements AppMenu, Screen {

    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Texture backgroundTexture;
    private String errorMessage;
    private Label profileTitle;
    private ProfileMenuController controller;
    private boolean isInUsernameChange;
    private boolean isInPasswordChange;
    private boolean isInNickNameChange;
    private boolean isInEmailChange;
    private boolean isInAvatarChange;
    private boolean isInUserInfo;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton changeEmail;
    private TextButton changeNickname;
    private TextButton changeAvatar;
    private TextButton UserInfo;
    private TextField usernameField;
    private TextField newPasswordField;
    private TextField oldPasswordField;
    private TextField emailField;
    private TextField nicknameField;
    private TextButton backButton;
    private TextButton submitButton;
    private TextButton randomPasswordButton;
    private ScrollPane avatarScrollPane;
    private Texture selectedAvatar;

    public ProfileMenuView(ProfileMenuController controller) {
        Skin skin = PreGameAssetManager.getSkin();
        this.controller = controller;
        controller.setView(this);
        table = new Table();
        font = new BitmapFont();
        makeThemFalse();
        changeUsername = new TextButton("Change username", skin);
        changePassword = new TextButton("Change password", skin);
        changeEmail = new TextButton("Change email", skin);
        changeNickname = new TextButton("Change nickname", skin);
        changeAvatar = new TextButton("Change avatar", skin);
        UserInfo = new TextButton("User info", skin);
        usernameField = new TextField("", skin);
        newPasswordField = new TextField("", skin);
        oldPasswordField = new TextField("", skin);
        emailField = new TextField("", skin);
        nicknameField = new TextField("", skin);
        profileTitle = new Label("Profile", skin, "title");
        backButton = new TextButton("Back", skin, "naked");
        submitButton = new TextButton("Submit", skin);
        backgroundTexture = PreGameAssetManager.getMenusBG();
        randomPasswordButton = new TextButton("Generate random password", skin);

    }

    private void setUp() {
        if (isInUsernameChange) {
            usernameField.setMessageText("enter a new username");
            table.add(usernameField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInPasswordChange) {
            newPasswordField.setMessageText("enter a new password");
            oldPasswordField.setMessageText("enter your current password");
            table.add(newPasswordField).width(600).padBottom(32f).height(80);
            table.row();
            table.add(oldPasswordField).width(600).padBottom(32f).height(80);
            table.row();
            table.add(randomPasswordButton).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInEmailChange) {
            emailField.setMessageText("enter a new email address");
            table.add(emailField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInNickNameChange) {
            nicknameField.setMessageText("enter a new nickname");
            table.add(nicknameField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        }else if (isInAvatarChange) {
            Texture[] avatars = AvatarAssetManager.getSkinTextures();
            Skin skin = PreGameAssetManager.getSkin();

            Table avatarTable = new Table();
            avatarTable.defaults().pad(10f);

            int columns = 6;
            for (int i = 0; i < avatars.length; i++) {
                final int index = i;

                Image avatarImage = new Image(avatars[i]);
                avatarImage.setSize(150, 150);
                avatarImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        selectedAvatar = avatars[index];
                    }
                });
                avatarTable.add(avatarImage).size(64, 64);
                if ((i + 1) % columns == 0) {
                    avatarTable.row();
                }

            }

            avatarScrollPane = new ScrollPane(avatarTable, skin);
            avatarScrollPane.setFadeScrollBars(false);
            avatarScrollPane.setScrollingDisabled(true, false);
            avatarScrollPane.setScrollbarsOnTop(true);
            avatarScrollPane.setScrollBarPositions(true, true);

            table.add(avatarScrollPane).width(600).height(300).padBottom(32f);
            table.row();

            addSubmitAndBackButton();
        } else {
            if(App.getCurrentUser().getAvatar() != null) {
                Image avatarImage = new Image(App.getCurrentUser().getAvatar());
                avatarImage.setSize(100, 100);
                table.add(avatarImage).width(100).height(100).padBottom(32f);
                table.row();
            }
            table.add(changeUsername).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changePassword).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changeEmail).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changeNickname).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changeAvatar).width(600).padBottom(32f).height(80);
            table.row();
            table.add(UserInfo).width(600).padBottom(32f).height(80);
            table.row();

        }
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.profileTitle.setFontScale(1.9f);
        this.font.setColor(Color.WHITE);

        stage.setKeyboardFocus(table);
        table.setFillParent(true);
        table.clear();
        table.reset();
        table.center();
        table.add(profileTitle).padBottom(20f);
        table.row();
        setUp();
        table.add(backButton).width(600).padBottom(32f).height(80);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1f);

        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (backButton.isChecked()) {
            if (isAllFalse()) {
                Main.getMain().setScreen(Menus.PreGameMenus.MAIN_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
            } else {
                makeThemFalse();
                show();
            }
            backButton.setChecked(false);
        }
        if(selectedAvatar != null && isInAvatarChange) {
            Main.getBatch().draw(selectedAvatar, Gdx.graphics.getWidth() / 2 - 75, Gdx
                .graphics.getHeight() - 200, 200, 200);
        }
        font.getData().setScale(2f);
        if (errorMessage != null && !errorMessage.isEmpty()) {
            font.draw(Main.getBatch(), errorMessage, Gdx.graphics.getWidth() / 2 - errorMessage.length() * 5, Gdx.graphics.getHeight() - 50);
        }
        Main.getBatch().end();
        stage.act(v);
        stage.draw();
        controller.handleProfileButtons();
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
        stage.dispose();
    }

    public TextButton getUserInfo() {
        return UserInfo;
    }

    public TextButton getChangeUsername() {
        return changeUsername;
    }

    public TextButton getChangePassword() {
        return changePassword;
    }

    public TextButton getChangeEmail() {
        return changeEmail;
    }

    public TextButton getChangeNickname() {
        return changeNickname;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public TextButton getRandomPasswordButton() {
        return randomPasswordButton;
    }

    public TextButton getChangeAvatar() {
        return changeAvatar;
    }

    public void setInUsernameChange(boolean inUsernameChange) {
        makeThemFalse();
        isInUsernameChange = inUsernameChange;
        show();
    }

    public void setInPasswordChange(boolean inPasswordChange) {
        makeThemFalse();
        isInPasswordChange = inPasswordChange;
        show();
    }

    public void setInNickNameChange(boolean inNickNameChange) {
        makeThemFalse();
        isInNickNameChange = inNickNameChange;
        show();
    }

    public void setInEmailChange(boolean inEmailChange) {
        makeThemFalse();
        isInEmailChange = inEmailChange;
        show();
    }

    public void setInUserInfo(boolean inUserInfo) {
        makeThemFalse();
        isInUserInfo = inUserInfo;
        show();
    }

    public void setInAvatarChange(boolean inAvatarChange) {
        makeThemFalse();
        isInAvatarChange = inAvatarChange;
        show();
    }

    public boolean isInAvatarChange() {
        return isInAvatarChange;
    }

    public boolean isInUsernameChange() {
        return isInUsernameChange;
    }

    public boolean isInPasswordChange() {
        return isInPasswordChange;
    }

    public boolean isInNickNameChange() {
        return isInNickNameChange;
    }

    public boolean isInEmailChange() {
        return isInEmailChange;
    }

    public boolean isInUserInfo() {
        return isInUserInfo;
    }

    public void makeThemFalse() {
        isInUsernameChange = false;
        isInPasswordChange = false;
        isInNickNameChange = false;
        isInEmailChange = false;
        isInUserInfo = false;
        isInAvatarChange = false;
        selectedAvatar = null;
        setErrorMessage(null);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextField getOldPasswordField() {
        return oldPasswordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ProfileMenuView.this.errorMessage = null;
            }
        }, 3);
    }


    private void addSubmitAndBackButton() {
        table.add(submitButton).width(600).padBottom(32f).height(80);
        table.row();
        table.add(backButton).width(600).padBottom(32f).height(80);
        table.row();
    }

    public Stage getStage() {
        return stage;
    }

    public Texture getSelectedAvatar() {
        return selectedAvatar;
    }

    public boolean isAllFalse() {
        return !isInUsernameChange
                && !isInPasswordChange
                && !isInNickNameChange
                && !isInEmailChange
                && !isInUserInfo
                && !isInAvatarChange;
    }

    @Override
    public void handleInput(Scanner sc) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showMessageAndExecute(String message, Runnable onClose) {

    }

    @Override
    public void showConfirmation(String message, Consumer<Boolean> resultCallback) {

    }

}
