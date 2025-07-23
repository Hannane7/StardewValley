package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.SignupMenuController;
import com.untildawn.models.AssetManager.PreGameAssetManager;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;

public class SignupMenuView implements Screen, AppMenu {

    private Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final TextButton loginButton;
    private final TextButton signupButton;
    private final Label signupLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField emailField;
    private final SelectBox<String> securityQuestionBox;
    private final TextField securityAnswer;
    private final ImageTextButton maleButton;
    private final ImageTextButton femaleButton;
    private ButtonGroup<ImageTextButton> genderGroup;
    private final Table table;

    private final SignupMenuController controller;

    public SignupMenuView(SignupMenuController controller) {
        this.controller = controller;
        this.skin = PreGameAssetManager.getSkin();
        this.loginButton = new TextButton("I've already had an account", skin, "naked");
        this.signupButton = new TextButton("SIGNUP", skin);
        this.signupLabel = new Label("SIGNUP", skin, "title");
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Enter your username");
        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Enter your password");
        this.emailField = new TextField("", skin);
        this.emailField.setMessageText("Enter your email address");
        this.securityQuestionBox = new SelectBox<>(skin);
        this.securityAnswer = new TextField("", skin);
        this.securityAnswer.setMessageText("Enter your answer");
        this.maleButton = new ImageTextButton("MALE", skin, "radio");
        this.femaleButton = new ImageTextButton("FEMALE", skin, "radio");
        this.genderGroup = new ButtonGroup<>();
        this.table = new Table();

        this.backgroundTexture = new Texture("images/backgrounds/MenusBG.png");

        controller.setView(this);
        controller.handleButtons();
    }

    private void fillSecurityQuestionBox(SelectBox<String> securityQuestionBox) {
        Array<String> questions = new Array<>();
        questions.add("What was the name of your first pet?");
        questions.add("What is your mother's maiden name?");
        questions.add("What was the name of your elementary school?");
        questions.add("In what city were you born?");
        questions.add("What is your favorite food?");
        questions.add("What is the name of your best childhood friend?");
        questions.add("What is your favorite movie?");
        questions.add("What is your father's middle name?");
        questions.add("What was the make of your first car?");
        questions.add("What was the name of your first teacher?");

        securityQuestionBox.setItems(questions);
        securityQuestionBox.setPosition(100, 100);
    }

    private void fillTable(Table table) {
        table.setFillParent(true);
        table.center();
        table.add(signupLabel);
        table.row().pad(50, 5, 10, 5);
        table.add(usernameField).width(800).height(80);
        table.row().pad(10, 5, 10, 5);
        table.add(passwordField).width(800).height(80);
        table.row().pad(10, 5, 20, 5);
        table.add(emailField).width(800).height(80);
        table.row().pad(20, 5, 20, 5);
        table.add(securityQuestionBox).width(700).height(50);
        table.row().pad(20, 5, 30, 5);
        table.add(securityAnswer).width(600).height(80);
        table.row();
        table.add(maleButton).pad(10);
        table.row();
        table.add(femaleButton).pad(10);
        table.row().pad(30, 5, 30, 5);
        table.add(signupButton).width(200).height(60);
        table.row().pad(30, 5, 10, 5);
        table.add(loginButton).width(150).height(30);
    }

    private void setGenderGroup(ButtonGroup<ImageTextButton> genderGroup) {
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.setMaxCheckCount(1);
        genderGroup.setMinCheckCount(1);
        genderGroup.setUncheckLast(true);

        genderGroup.setChecked("MALE");
    }

    @Override
    public void show() {
        if (stage != null) {
            table.clear();
            stage.clear();
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        setGenderGroup(genderGroup);
        fillTable(table);
        fillSecurityQuestionBox(securityQuestionBox);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK, true);
        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
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

    public void dispose() {
        stage.dispose();
    }

    @Override
    public void showError(String error) {
        Dialog errorDialog = new Dialog("Error", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        errorDialog.text(error);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.getStyle().fontColor = Color.RED;
        errorDialog.button(closeButton);

        errorDialog.show(stage);

        errorDialog.setSize(400, 200);
        errorDialog.setPosition(
            (stage.getWidth() - errorDialog.getWidth()) / 2,
            (stage.getHeight() - errorDialog.getHeight()) / 2
        );
    }

    @Override
    public void showMessage(String message) {
        Dialog messageDialog = new Dialog("Alert", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        messageDialog.text(message);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.getStyle().fontColor = Color.RED;
        messageDialog.button(closeButton);

        messageDialog.show(stage);

        messageDialog.setSize(400, 200);
        messageDialog.setPosition(
            (stage.getWidth() - messageDialog.getWidth()) / 2,
            (stage.getHeight() - messageDialog.getHeight()) / 2
        );
    }

    @Override
    public void showMessageAndExecute(String message, Runnable onClose) {
        Dialog messageDialog = new Dialog("Alert", skin) {
            @Override
            protected void result(Object object) {
                if (onClose != null) {
                    onClose.run();
                }
            }
        };

        messageDialog.text(message);
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.getStyle().fontColor = Color.RED;

        messageDialog.button(closeButton, true);
        messageDialog.show(stage);

        messageDialog.setSize(400, 200);
        messageDialog.setPosition(
            (stage.getWidth() - messageDialog.getWidth()) / 2,
            (stage.getHeight() - messageDialog.getHeight()) / 2
        );
    }

    @Override
    public void showConfirmation(String message, final Consumer<Boolean> resultCallback) {
        Dialog dialog = new Dialog("Confirm", skin) {
            @Override
            protected void result(Object object) {
                if (object instanceof Boolean) {
                    resultCallback.accept((Boolean) object);
                }
            }
        };

        dialog.text(message);

        dialog.button("Yes", true);
        dialog.button("No", false);

        dialog.show(stage);

        dialog.setSize(400, 200);
        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2,
            (stage.getHeight() - dialog.getHeight()) / 2
        );
    }

    @Override
    public void handleInput(Scanner sc) {

    }

    public TextButton getSignupButton() {
        return signupButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public ImageTextButton getMaleButton() {
        return maleButton;
    }

    public ImageTextButton getFemaleButton() {
        return femaleButton;
    }

    public TextField getSecurityAnswer() {
        return securityAnswer;
    }

    public SelectBox<String> getSecurityQuestionBox() {
        return securityQuestionBox;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }
}
