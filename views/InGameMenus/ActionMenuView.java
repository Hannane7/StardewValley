package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.InGameMenuCommands.ActionMenuCommands;
import com.untildawn.controllers.InGameControllers.*;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class ActionMenuView implements Screen, AppMenu {
    private Scanner scanner;

    @Override
    public void handleInput(Scanner sc) {
        this.scanner = sc;
        String input = scanner.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (ActionMenuCommands command : ActionMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(ActionMenuCommands command, Matcher matcher, String input) {
        Game game = App.getCurrentGame();
        ActionMenuController actionController = new ActionMenuController(this);
        FarmingController farmingController = new FarmingController(this);
        AnimalController animalController = new AnimalController(this);
        NPCController npcController = new NPCController(this);
        PlayerRelationController playerRelationController = new PlayerRelationController(this);
        ToolController toolController = new ToolController(this);

        switch (command) {
            case SWITCH_MENU:
                actionController.changeMenu();
                break;
            case NEXT_TURN:
                actionController.nextTurn();
                break;
            case TIME:
                System.out.println(game.getDateTime().getHour());
                break;
            case DATE:
                System.out.println("season: " + game.getDateTime().getSeason().toString().toLowerCase()
                        + "\nday: " + game.getDateTime().getDay());
                break;
            case DATE_TIME:
                System.out.println("season: " + game.getDateTime().getSeason().toString().toLowerCase()
                        + "\nday: " + game.getDateTime().getDay()
                        + "\nday of week: " + game.getDateTime().getDayOfWeek().name().toLowerCase()
                        + "\nhour: " + game.getDateTime().getHour());
                break;
            case DAY_OF_THE_WEEK:
                System.out.println(game.getDateTime().getDayOfWeek().toString().toLowerCase());
                break;
            case CHEAT_ADVANCE_TIME:
                actionController.cheatAdvanceTime(matcher, game);
                break;
            case CHEAT_ADVANCE_DATE:
                actionController.cheatAdvanceDate(matcher, game);
                break;
            case SEASON:
                System.out.println(game.getDateTime().getSeason().toString().toLowerCase());
                break;
            case CHEAT_THOR:
                break;
            case WEATHER:
                System.out.println(game.getWeather().getCurrentWeather().toString().toLowerCase());
                break;
            case WEATHER_FORECAST:
                actionController.weatherForecast(game);
                break;
            case CHEAT_WEATHER_SET:
                actionController.cheatWeather(matcher, game);
                break;
            case GREENHOUSE_BUILD:
                actionController.buildGreenhouse();
                break;
            case WALK:
                actionController.walk(matcher.group("y"), matcher.group("x"));
                break;
            case PRINT_MAP:
                actionController.printMap(matcher.group("x"), matcher.group("y"), matcher.group("size"));
                break;
            case HELP_READING_MAP:
                actionController.helpReadingMap();
                break;
            case ENERGY_SHOW:
                System.out.println("Energy: " + game.getCurrentPlayer().getEnergy());
                break;
            case ENERGY_SET:
                actionController.cheatSetEnergy(matcher, game);
                break;
            case ENERGY_REFILL:
                actionController.cheatRefillTurnEnergy();
                break;
            case ENERGY_UNLIMITED:
                actionController.energyUnlimited(game);
                break;
            case TOOLS_EQUIP:
                break;
            case TOOLS_SHOW_CURRENT:
                break;
            case TOOLS_SHOW_AVAILABLE:
                break;
            case TOOLS_UPGRADE:
                break;
            case TOOLS_USE:
                break;
            case CRAFT_INFO:
                actionController.craftInfo(matcher, game);
                break;
            case PLANT:
                farmingController.plant(matcher.group("seed"), matcher.group("direction"), game);
                break;
            case SHOW_PLANT:
                farmingController.showPlant(matcher.group("y"), matcher.group("x"), game);
                break;
            case FERTILIZE:
                farmingController.fertilize(matcher.group("fertilizer"), matcher.group("direction"));
                break;
            case HOW_MUCH_WATER:
                farmingController.howMuchWater();
                break;
            case CRAFTING_SHOW_RECIPES:
                break;
            case CRAFTING_CRAFT:
                break;
            case PLACE_ITEM:
                break;
            case CHEAT_ADD_ITEM:
                break;
            case COOKING_REFRIGERATOR:
                break;
            case COOKING_SHOW_RECIPES:
                break;
            case COOKING_PREPARE:
                break;
            case EAT:
                break;
            case BUILD:
                animalController.buildBarnOrCoop(matcher, game);
                break;
            case BUY_ANIMAL:
                animalController.buyAnimal(matcher, game);
                break;
            case PET:
                animalController.pet(matcher, game);
                break;
            case ANIMALS:
                animalController.showAnimals(game);
                break;
            case SHEPHERD_ANIMALS:
                animalController.shepHerd(matcher, game);
                break;
            case FEED_HAY:
                animalController.feedHay(matcher, game);
                break;
            case CHEAT_SET_FRIENDSHIP:
                animalController.setAnimalFriendShip(matcher, game);
                break;
            case PRODUCES:
                animalController.animalProducts(game);
                break;
            case COLLECT_PRODUCE:
                animalController.collectAnimalProduct(matcher, game);
                break;
            case SELL_ANIMAL:
                animalController.sellAnimal(matcher, game);
                break;
            case FISHING:
                animalController.fishing(matcher, game);
                break;
            case ARTISAN_USE:
                actionController.artisanUse(matcher, game, input);
                break;
            case ARTISAN_GET:
                actionController.artisanGet(matcher, game);
                break;
            case MEET_NPC:
                npcController.meetNPC(matcher.group("npcName"));
                break;
            case GIFT_NPC:
                npcController.giftNPC(matcher.group("npcName"), matcher.group("item"));
                break;
            case FRIENDSHIP_NPC_LIST:
                npcController.friendshipNPCList();
                break;
            case QUESTS_LIST:
                npcController.questsList();
                break;
            case GET_QUEST:
                npcController.getQuestFromNPC(matcher.group("npcName"));
                break;
            case QUESTS_FINISH:
                npcController.questFinish(matcher.group("index"));
                break;
            case FRIENDSHIPS:
                playerRelationController.friendships();
                break;
            case TALK:
                playerRelationController.talk(matcher.group("username"), matcher.group("message"));
                break;
            case TALK_HISTORY:
                playerRelationController.talkHistory(matcher.group("username"));
                break;
            case GIFT:
                playerRelationController.gift(
                        matcher.group("username"), matcher.group("item"), matcher.group("amount"));
                break;
            case GIFT_LIST:
                playerRelationController.giftList();
                break;
            case GIFT_RATE:
                playerRelationController.giftRate(matcher.group("giftNumber"), matcher.group("rate"));
                break;
            case GIFT_HISTORY:
                playerRelationController.giftHistory(matcher.group("username"));
                break;
            case HUG:
                playerRelationController.hug(matcher.group("username"));
                break;
            case FLOWER:
                playerRelationController.flower(matcher.group("username"));
                break;
            case ASK_MARRIAGE:
                playerRelationController.askMarriage(matcher.group("username"));
                break;
            case CHEAT_INFINITY_INVENTORY:
                actionController.cheatInfinityInventory();
                break;
            case SHOW_MONEY:
                System.out.println(game.getCurrentPlayer().getWallet().getCoin());
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
