package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.InGameMenuCommands.CraftingMenuCommands;
import com.untildawn.controllers.InGameControllers.CraftingMenuController;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.AppMenu;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class CraftingMenu implements Screen, AppMenu  {
    Scanner scanner;
    @Override
    public void handleInput(Scanner sc) {
        scanner = sc;
        String input = scanner.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (CraftingMenuCommands command : CraftingMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
    }
    private void executeCommand(CraftingMenuCommands command, Matcher matcher, String input) {
        Game game = App.getCurrentGame();
        CraftingMenuController controller =new CraftingMenuController(this);
        switch (command) {
            case CRAFTING_CRAFT:
                System.out.println(controller.craftingCraft(matcher.group("itemName"),game ));
                break;
            case CHEAT_ADD_ITEM:
                System.out.println(controller.cheatAddItem(matcher.group("itemName"), matcher.group("count"),game));
                break;
            case SWITCH_MENU:
                controller.changeMenu();
                break;
            case PLACE_ITEM:
                System.out.println(controller.placeItem(matcher.group("direction"),game, matcher.group("itemName") ));
                break;
            case CRAFTING_SHOW_RECIPES:
                System.out.println(controller.printRecipes(game));
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
