package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.InGameMenuCommands.ActionMenuCommands;
import com.untildawn.controllers.InGameControllers.TradeController;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;


public class TradeMenuView implements Screen, AppMenu {

    Scanner scanner;

    @Override
    public void handleInput(Scanner sc) {
        this.scanner = sc;
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (ActionMenuCommands command : ActionMenuCommands.values()) { // TODO: change to -> TradeMenuCommands
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.print("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(ActionMenuCommands command, Matcher matcher, String input) {
        TradeController controller = new TradeController(this);

        switch (command) {
            case TRADE:
                controller.trade(matcher.group("username"));
                break;
            case TRADE_HISTORY:
                controller.tradeHistory();
                break;
            case SWITCH_MENU:
                controller.changeMenu();
                break;
        }
    }



    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
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

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
}
