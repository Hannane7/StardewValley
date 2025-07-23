package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.InGameMenuCommands.CookingMenuCommands;
import com.untildawn.controllers.InGameControllers.CookingMenuController;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class CookingMenu implements Screen, AppMenu {
    Scanner scanner;
    @Override
    public void handleInput(Scanner sc) {
        scanner = sc;
        String input = scanner.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (CookingMenuCommands command : CookingMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(CookingMenuCommands command, Matcher matcher, String input) {
        Game game = App.getCurrentGame();
        CookingMenuController controller =new CookingMenuController(this);
        switch (command) {
            case SWITCH_MENU:
                controller.changeMenu();
                break;
            case COOKING_PREPARE :
                System.out.println(controller.cook(game, matcher.group("recipeName")));
                break;
            case COOKING_SHOW_RECIPES:
                System.out.println(controller.printRecipes(game));
                break;
            case COOKING_REFRIGERATOR_PUT:
                System.out.println(controller.putRef(matcher.group("item"), game));
                break;
            case COOKING_REFRIGERATOR_PICK:
                System.out.println(controller.pickRef(matcher.group("item"), game));
                break;
            case EAT:
                System.out.println(controller.eatFood(game, matcher.group("foodName")));
                break;
        }
    }


    public String prompt(String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        return input;
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
