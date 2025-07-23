package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.InGameMenuCommands.MenuSwitcherCommands;
import com.untildawn.controllers.InGameControllers.MenuSwitcher;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class MenuSwitcherView implements Screen, AppMenu {
    public void handleInput(Scanner sc) {
        printMenus();
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (MenuSwitcherCommands command : MenuSwitcherCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
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


    public String prompt(String message) {
        return "";
    }

    private static void executeCommand(MenuSwitcherCommands command, Matcher matcher, String input) {
        MenuSwitcher controller = new MenuSwitcher();
        switch (command) {
            case MENU_ENTER -> System.out.println(controller.switchMenu(matcher));
        }
    }
    public void printMenus() {

        System.out.println("1. Action Menu\n"
                + "2. Cooking Menu\n"
                + "3. Crafting Menu\n"
                + "4. Inventory Menu\n"
                + "5. Home Menu\n"
                + "6. Shop Menu\n"
                + "7. Trade Menu\n"
                + "8. Exit Menu\n");
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
