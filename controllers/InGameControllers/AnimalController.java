package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.models.Animals.Animal;
import com.untildawn.models.Animals.Barn;
import com.untildawn.models.Animals.Coop;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class AnimalController {
    ActionMenuView view;

    public AnimalController(ActionMenuView view) {
        this.view = view;
    }

    public void fishing(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String fishingPole = matcher.group("fishingPole").trim().toLowerCase();
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        ItemInstance pole = null;
        try {
            pole = inventory.useItem(ItemIDs.valueOf(fishingPole));//TODO
        } catch (Exception e) {
            view.showMessage("Please enter a valid fishing pole.");
            return;
        }
        if (!isNearLake(player, game)) {
            view.showMessage("You should be near lake to start fishing!");
            return;
        }
        if (pole == null) {
            view.showMessage("You don't have " + fishingPole + "!");
            return;
        }
        int skill = player.getAbilities().getAbilityLevel(player.getAbilities().getFishingAbility());
        ArrayList<ItemDefinition> fish = new ArrayList<>();
        for (ItemDefinition item : App.getItemDefinitions()) {
            if (skill != 4) {
                if (item.getType().equals(ItemType.fish) &&
                    item.getAttribute(ItemAttributes.season).toString().
                        equals(game.getDateTime().getSeason().name().toLowerCase())) {
                    fish.add(item);
                }
            } else {
                if ((item.getType().equals(ItemType.fish)
                    || item.getType().equals(ItemType.legendary_fish)) &&
                    item.getAttribute(ItemAttributes.season).toString().
                        equals(game.getDateTime().getSeason().name().toLowerCase())) {
                    fish.add(item);
                }
            }
        }
        double R = GenerateRandomNumber.generateRandomDoubleNumber(0, 1);
        double M = getMBasedOnWeather(game);
        int x = Math.min(5, (int) (R * M * (skill + 2)));
        ArrayList<ItemDefinition> caughtFish = new ArrayList<>();
        int temp = x;
        while (temp > 0) {
            caughtFish.add(fish.get(temp - 1));
            temp--;
        }
        switch (fishingPole) {
            case "training_fishing_pole":
                int quality1 = calculateQuality(R, M, skill, 0.1);
                printFish(quality1, x, caughtFish);
                putFishInInventory(player, caughtFish, quality1);
                break;
            case "bamboo_fishing_pole":
                int quality2 = calculateQuality(R, M, skill, 0.5);
                printFish(quality2, x, caughtFish);
                putFishInInventory(player, caughtFish, quality2);
                break;
            case "fiber_glass_fishing_pole":
                int quality3 = calculateQuality(R, M, skill, 0.9);
                printFish(quality3, x, caughtFish);
                putFishInInventory(player, caughtFish, quality3);
                break;
            case "iridium_fishing_pole":
                int quality4 = calculateQuality(R, M, skill, 1.2);
                printFish(quality4, x, caughtFish);
                putFishInInventory(player, caughtFish, quality4);
                break;
            default:
                view.showMessage("Please select a valid pole!");
                break;
        }
    }

    public boolean isNearLake(Player player, Game game) {
        GameMap gameMap = game.getGameMap();
        int x = (int) player.getPosition().getX();
        int y = (int) player.getPosition().getY();
        return gameMap.getTile(y, x - 1).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y, x + 1).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y + 1, x - 1).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y + 1, x).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y + 1, x + 1).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y - 1, x - 1).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y - 1, x).getItem().getDefinition().getType().equals(ItemType.lake)
            || gameMap.getTile(y - 1, x + 1).getItem().getDefinition().getType().equals(ItemType.lake);
    }

    public double getMBasedOnWeather(Game game) {
        WeatherStates weather = game.getWeather().getCurrentWeather();
        switch (weather) {
            case SUNNY :
                return 1.5;

            case RAIN :
                return 1.2;

            case STORM :
                return 0.5;

            default :
                return 1;

        }
    }

    public int calculateQuality(double R, double M, int skill, double pole) {
        return (int) ((R * pole * (skill + 2)) / (7 - M));
    }

    public void printFish(int quality, int number, ArrayList<ItemDefinition> caughtFish) {
        view.showMessage("Quality of fish : " + quality);
        view.showMessage("Number of fish : " + number);
        for (ItemDefinition fish : caughtFish) {
            view.showMessage("Fish name: " + fish.getDisplayName().toLowerCase() + " type: " + fish.getType().name());
        }
    }

    public void putFishInInventory(Player player, ArrayList<ItemDefinition> caughtFish, int quality) {
        for (ItemDefinition fish : caughtFish) {
            ItemInstance item = new ItemInstance(fish);
            if (0.5 <= quality && quality < 0.7) {
                item.setAttribute(ItemAttributes.baseSellPrice,
                    (int) item.getAttribute(ItemAttributes.baseSellPrice) * 1.25);
                if (!player.getInventory().addItem(item)) {
                    view.showMessage("Your back pack is full!");
                } else {
                    item.setAttribute(ItemAttributes.quality, "silver");
                    player.getInventory().addItem(item);
                }
            } else if (0.7 <= quality && quality < 0.9) {
                item.setAttribute(ItemAttributes.baseSellPrice,
                    (int) item.getAttribute(ItemAttributes.baseSellPrice) * 1.5);
                if (!player.getInventory().addItem(item)) {
                    view.showMessage("Your back pack is full!");
                } else {
                    item.setAttribute(ItemAttributes.quality, "golden");
                    player.getInventory().addItem(item);
                }
            } else if (0.9 <= quality) {
                item.setAttribute(ItemAttributes.baseSellPrice,
                    (int) item.getAttribute(ItemAttributes.baseSellPrice) * 2);
                if (!player.getInventory().addItem(item)) {
                    view.showMessage("Your back pack is full!");
                } else {
                    item.setAttribute(ItemAttributes.quality, "iridium");
                    player.getInventory().addItem(item);
                }
            }
        }
        player.getAbilities().increaseFishingAbility();
        System.out.println("Fishing ablitity: " +
            player.getAbilities().getAbilityLevel(player.getAbilities().getFishingAbility()));
    }

    public void buildBarnOrCoop(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Player player = game.getCurrentPlayer();
        PlayerMap playerMap = player.getPlayerMap();
        String buildingName = matcher.group("buildingName").trim().toLowerCase();
        String yStr = matcher.group("y").trim().toLowerCase();
        String xStr = matcher.group("x").trim().toLowerCase();
        Inventory inventory = player.getInventory();
        int x, y;
        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter a valid position!");
            return;
        }
        int yShop = NPCConst.ShopPositions.CarpenterShop.getY();
        int xShop = NPCConst.ShopPositions.CarpenterShop.getX();
        int playerY = (int) player.getPosition().getY();
        int playerX = (int) player.getPosition().getX();
        if (!(xShop - 1 <= playerX && playerX <= xShop + 1
            && yShop - 1 <= playerY && playerY <= yShop + 1)) {
            view.showMessage("You should be near Carpenter's shop to buy " + buildingName + "!");
            return;
        }
        if (!(playerMap.getStartPosition().getX() <= x && x <= playerMap.getEndPosition().getX()
            && playerMap.getStartPosition().getY() <= y && y <= playerMap.getEndPosition().getY())) {
            view.showMessage("Please select a tile from your own farm!");
            return;
        }
        Tile tile = game.getGameMap().getTile(y, x);
//        if (!tile.getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
//            view.showMessage("This tile is not empty!");
//            return;
//        }
        ItemDefinition building = Objects.requireNonNull(App.getItemDefinition(buildingName));
        Object ingredient = building.getAttribute(ItemAttributes.ingredients);
        if (ingredient instanceof Map<?, ?>) {
            Map<String, Object> ingredients = (Map<String, Object>) ingredient;
//            for (Map.Entry<String, Object> entry : ingredients.entrySet()) {
//                String key = entry.getKey();
//                Object value = entry.getValue();
//                if (key.equals("wood")) {
//                    if (!inventory.hasItem(ItemIDs.wood, (int) value)) {
//                        view.showMessage("You don't have enough wood!");
//                        return;
//                    }
//                }
//                if (key.equals("stone")) {
//                    if (!inventory.hasItem(ItemIDs.stone, (int) value)) {
//                        view.showMessage("You don't have enough stone!");
//                        return;
//                    }
//                }
//            }
        } else {
            view.showMessage("This building doesn't need any source to build!");
        }
//        if (player.getWallet().getCoin() < (int) building.getAttribute(ItemAttributes.shopPrice)) {
//            view.showMessage("You don't have enough coin!");
//            return;
//        }
        ItemInstance item = new ItemInstance(building);
        tile.setItem(item);
        if (buildingName.contains("barn")) {
            playerMap.setBarn(new Barn(building, new Position(y, x)));
            player.getWallet().setCoin(player.getWallet().getCoin() - (int) building.getAttribute(ItemAttributes.shopPrice));
        } else if (buildingName.contains("coop")) {
            playerMap.setCoop(new Coop(building, new Position(y, x)));
            player.getWallet().setCoin(player.getWallet().getCoin() - (int) building.getAttribute(ItemAttributes.shopPrice));
        }

        view.showMessage("you've built " + buildingName + "!");
    }

    public void buyAnimal(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String animalStr = matcher.group("animal").trim().toLowerCase();
        String name = matcher.group("name").trim().toLowerCase();
        Player player = game.getCurrentPlayer();
        PlayerMap playerMap = player.getPlayerMap();
        int yShop = NPCConst.ShopPositions.MarnieRanch.getY();
        int xShop = NPCConst.ShopPositions.MarnieRanch.getX();
        int playerY = (int) player.getPosition().getY();
        int playerX = (int) player.getPosition().getX();
        if (!(xShop - 1 <= playerX && playerX <= xShop + 1
            && yShop - 1 <= playerY && playerY <= yShop + 1)) {
            view.showMessage("You should be near Marine's Ranch to buy " + animalStr + "!");
            return;
        }
        boolean found = false;
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if (itemDefinition.getId().name().equals(animalStr) &&
                (itemDefinition.getType().equals(ItemType.coop_animal)
                    || itemDefinition.getType().equals(ItemType.barn_animal))) {
                found = true;
                break;
            }
        }
        if (!found) {
            view.showMessage("You should enter an animal's name!");
            return;
        }
        for (Animal animal : player.getAnimals()) {
            if (animal.getName().equals(name)) {
                view.showMessage("You should enter an unique name for your animal!");
                return;
            }
        }
        ItemDefinition animalDef = Objects.requireNonNull(App.getItemDefinition(animalStr));
        if (player.getWallet().getCoin() < (int) animalDef.getAttribute(ItemAttributes.price)) {
            view.showMessage("You don't have enough coin!");
            return;
        }

        if (animalDef.getType().equals(ItemType.coop_animal)) {
            if (!playerMap.hasCoop()) {
                view.showMessage("You haven't built a coop!");
                return;
            }
            player.getWallet().setCoin(player.getWallet().getCoin() - (int) animalDef.getAttribute(ItemAttributes.price));
            Animal animal = new Animal(animalDef, name, player, playerMap.getCoop().getPosition());
            playerMap.getCoop().setAnimal(animal);
            player.setAnimal(animal);
            view.showMessage("You've bought a " + animalDef.getDisplayName().toLowerCase() + " named " + name);
        } else if (animalDef.getType().equals(ItemType.barn_animal)) {
            if (!playerMap.hasBarn()) {
                view.showMessage("You haven't built a barn!");
                return;
            }
            player.getWallet().setCoin(player.getWallet().getCoin() - (int) animalDef.getAttribute(ItemAttributes.price));
            Animal animal = new Animal(animalDef, name, player, playerMap.getBarn().getPosition());
            playerMap.getBarn().setAnimal(animal);
            player.setAnimal(animal);
            view.showMessage("You've bought a " + animalDef.getDisplayName().toLowerCase() + " named " + name);
        }
    }

    public void pet(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Player player = game.getCurrentPlayer();
        PlayerMap playerMap = player.getPlayerMap();
        String animalName = matcher.group("name").trim().toLowerCase();
        Animal animal = null;
        for (Animal animals : player.getAnimals()) {
            if (animals.getName().equals(animalName)) {
                animal = animals;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        int animalY = (int) animal.getPosition().getY();
        int animalX = (int) animal.getPosition().getX();
        int playerX = (int) player.getPosition().getX();
        int playerY = (int) player.getPosition().getY();
        if (!(animalX - 1 <= playerX && playerX <= animalX + 1
            && animalY - 1 <= playerY && playerY <= animalY + 1)) {
            view.showMessage("You should be near " + animalName + " to pet!");
            return;
        }
        animal.setPet(true);
        animal.increaseFriendShip(15);
        player.decreaseEnergy(5);
        view.showMessage("You've pet " + animalName + "!");
    }

    public void setAnimalFriendShip(Matcher matcher, Game game) {
        Player player = game.getCurrentPlayer();
        String animalName = matcher.group("animalName").trim().toLowerCase();
        String amountStr = matcher.group("amount");
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter a valid amount!");
            return;
        }
        Animal animal = null;
        for (Animal animals : player.getAnimals()) {
            if (animals.getName().equals(animalName)) {
                animal = animals;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        animal.increaseFriendShip(amount);
        view.showMessage("Your friendship has been set to " + amount + " with" + animalName + "!");
    }

    public void showAnimals(Game game) {
        Player player = game.getCurrentPlayer();
        if (player.getAnimals().isEmpty()) {
            view.showMessage("You currently have no animal!");
            return;
        }
        for (Animal animal : player.getAnimals()) {
            view.showMessage("Name : " + animal.getName() + "\nfriendship : " + animal.getFriendShip()
                + "\nisPet : " + animal.isPet() + "\nisFed : " + animal.isFed());
        }
    }

    public void shepHerd(Matcher matcher, Game game) {
        String animalName = matcher.group("animalName").trim().toLowerCase();
        String xStr = matcher.group("x").trim();
        String yStr = matcher.group("y").trim();
        Player player = game.getCurrentPlayer();
        PlayerMap playerMap = player.getPlayerMap();
        int x, y;
        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter a valid position!");
            return;
        }
        Animal animal = null;
        for (Animal playerAnimal : player.getAnimals()) {
            if (playerAnimal.getName().equals(animalName)) {
                animal = playerAnimal;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        if (y == animal.getPosition().getY() && x == animal.getPosition().getX()) {
            view.showMessage("You can't put " + animalName + " on itself!");
            return;
        }
        Tile tile = game.getGameMap().getTile(y, x);
        if (animal.getDefinition().getType().equals(ItemType.coop_animal)) {
            if (y == playerMap.getCoop().getPosition().getY() && x == playerMap.getCoop().getPosition().getX()) {
                if (animal.isOutside()) {
                    tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                    animal.setOutside(false);
                    animal.setPosition(new Position((int) playerMap.getCoop().getPosition().getY(),
                        (int) playerMap.getCoop().getPosition().getX()));
                    view.showMessage(animalName + " is now inside the coop!");
                    return;
                } else {
                    view.showMessage(animalName + " is already inside the barn!");
                    return;
                }
            }
        } else if (animal.getDefinition().getType().equals(ItemType.barn_animal)) {
            if (y == playerMap.getBarn().getPosition().getY() && x == playerMap.getBarn().getPosition().getX()) {
                if (animal.isOutside()) {
                    tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                    animal.setOutside(false);
                    animal.setPosition(new Position((int) playerMap.getBarn().getPosition().getY(),
                        (int) playerMap.getBarn().getPosition().getX()));
                    view.showMessage(animalName + " is now inside the barn!");
                    return;
                } else {
                    view.showMessage(animalName + " is already inside the barn!");
                    return;
                }
            }
        }

        if (!tile.getItem().getDefinition().getId().equals(ItemIDs.VOID)) {
            view.showMessage("You should put " + animalName + " on an empty tile!");
            return;
        }
        WeatherStates weatherStates = game.getWeather().getCurrentWeather();
        if (weatherStates.equals(WeatherStates.SNOWY)
            || weatherStates.equals(WeatherStates.RAIN)
            || weatherStates.equals(WeatherStates.STORM)) {
            view.showMessage("Animals must stat inside, the weather is fucked up(" + weatherStates.name() + ")!");
            return;
        }
        if (animal.isOutside()) {
            view.showMessage(animalName + " is now on y = " + y + ", x = " + x);
        }
        tile.setItem(animal);
        animal.setFed(true);
        animal.setPosition(new Position(y, x));
        animal.setOutside(true);
        animal.increaseFriendShip(8);
        view.showMessage(animalName + " is now outside!");
    }

    public void feedHay(Matcher matcher, Game game) {
        String animalName = matcher.group("animalName").trim().toLowerCase();
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        ItemDefinition hay = Objects.requireNonNull(App.getItemDefinition("hay"));
        Animal animal = null;
        for (Animal playerAnimal : player.getAnimals()) {
            if (playerAnimal.getName().equals(animalName)) {
                animal = playerAnimal;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        if (animal.isOutside()) {
            view.showMessage("This animal is outside!");
            return;
        }
        if (animal.isFed()) {
            view.showMessage(animalName + " has been fed today!");
            return;
        }
        if (!inventory.hasItem(hay.getId())) {
            view.showMessage("You do not have enough hay to feed " + animalName + "!");
            return;
        }
        animal.setFed(true);
        inventory.trashItem(hay.getId(), 1);
        view.showMessage("You've fed " + animalName + "!");
    }

    public void animalProducts(Game game) {//TODO add product to each animal in game flow
        Player player = game.getCurrentPlayer();
        if (player.getAnimals().isEmpty()) {
            view.showMessage("You don't have any animal in your farm!");
            return;
        }
        for (Animal animal : player.getAnimals()) {
            if(animal.getProducts().isEmpty()) {
                view.showMessage(animal.getName() + " doesn't have any product!");
                continue;
            }
            for (Map.Entry<ItemInstance, Integer> entry : animal.getProducts().entrySet()) {
                ItemInstance item = entry.getKey();
                Integer quality = entry.getValue();
                view.showMessage("Animal Name : " + animal.getName() +
                    "\nProduct Name : " + item.getDefinition().getDisplayName().toLowerCase()
                    + "\nQuality : " + quality);
            }
        }
    }

    public void collectAnimalProduct(Matcher matcher, Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Player player = game.getCurrentPlayer();
        String animalName = matcher.group("name").trim().toLowerCase();
        Animal animal = null;
        for (Animal animals : player.getAnimals()) {
            if (animals.getName().equals(animalName)) {
                animal = animals;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        if (animal.getProducts().isEmpty()) {
            view.showMessage(animalName + " doesn't have any product!");
            return;
        }
        ItemIDs id = animal.getDefinition().getId();
        if (id.equals(ItemIDs.cow) || id.equals(ItemIDs.sheep) || id.equals(ItemIDs.goat)) {
            view.showMessage("You should use milk pale to collect cow, sheep and goat products!");
            return;
        }
        if (id.equals(ItemIDs.pig) && !animal.isOutside()) {
            view.showMessage("Pig should be outside to collect its products!");
            return;
        }
        for (Map.Entry<ItemInstance, Integer> entry : animal.getProducts().entrySet()) {
            ItemInstance item = entry.getKey();
            player.getInventory().addItem(item);
            view.showMessage("You've collected " + item.getDefinition().getDisplayName().toLowerCase() + " from "
                + animalName + "!");
        }
        player.decreaseEnergy(-10);
    }

    public void sellAnimal(Matcher matcher, Game game) {
        Player player = game.getCurrentPlayer();
        String animalName = matcher.group("name").trim().toLowerCase();
        PlayerMap playerMap = player.getPlayerMap();
        Animal animal = null;
        for (Animal animals : player.getAnimals()) {
            if (animals.getName().equals(animalName)) {
                animal = animals;
            }
        }
        if (animal == null) {
            view.showMessage("This animal does not exist!");
            return;
        }
        int price = (int) ((int) animal.getAttribute(ItemAttributes.price) * ((double) animal.getFriendShip() / 1000 + 0.3));
        player.getWallet().increaseCoin(price);
        player.getAnimals().remove(animal);
        if (animal.getDefinition().getType().equals(ItemType.coop_animal)) {
            playerMap.getCoop().getAnimals().remove(animal);
        } else if (animal.getDefinition().getType().equals(ItemType.barn_animal)) {
            playerMap.getBarn().getAnimals().remove(animal);
        }
        view.showMessage("You've sold " + animalName + " for " + price + "g!");
    }

    public static void addProductToAnimal(Game game) {
        Player player = game.getCurrentPlayer();
        for (Animal animal : player.getAnimals()) {
            for (Map.Entry<ItemInstance, Integer> entry : animal.getProducts().entrySet()) {
                animal.getProducts().remove(entry.getKey());
            }
            Object product = animal.getAttribute(ItemAttributes.products);
            if (animal.isFed()) {
                if (product instanceof Map<?, ?>) {
                    double random = GenerateRandomNumber.generateRandomDoubleNumber(0.5, 1.5);
                    int x = (animal.getFriendShip() + (int) (random * 150)) / 500;
                    double rate = 1;
                    double R = GenerateRandomNumber.generateRandomDoubleNumber(0, 1);
                    double quality = ((double) animal.getFriendShip() / 1000) * (0.5 + 0.5 * R);
                    Map<String, Integer> products = (Map<String, Integer>) product;
                    int i = 1;
                    for (Map.Entry<String, Integer> entry : products.entrySet()) {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        if (products.size() == 2 && animal.getFriendShip() >= 100 && x >= 1 && i == 2) {
                            ItemDefinition itemDefinition = App.getItemDefinition(key);
                            if (itemDefinition == null) return;
                            ItemInstance itemInstance = new ItemInstance(itemDefinition);
                            rate = getQuality(key, quality, value, animal, itemInstance);
                            animal.setProduct(itemInstance, (int) (value * rate));
                        } else {
                            if (i == 1) {
                                ItemDefinition itemDefinition = App.getItemDefinition(key);
                                if (itemDefinition == null) return;
                                ItemInstance itemInstance = new ItemInstance(itemDefinition);
                                rate = getQuality(key, quality, value, animal, itemInstance);
                                animal.setProduct(itemInstance, (int) (value * rate));
                            }
                        }
                        i++;
                    }
                }
            } else animal.decreaseFriendShip(20);
            if (animal.isOutside()) animal.decreaseFriendShip(20);
            if (!animal.isPet()) animal.decreaseFriendShip(10);
            animal.setFed(false);
            animal.setPet(false);
        }
    }

    public static double getQuality(String key, double quality, int value, Animal animal, ItemInstance itemInstance) {
        if (0.5 <= quality && quality < 0.7) {
            itemInstance.setAttribute(ItemAttributes.quality, "silver");
            return 1.25;
        } else if (0.7 <= quality && quality < 0.9) {
            itemInstance.setAttribute(ItemAttributes.quality, "golden");
            return 1.5;
        } else if (0.9 <= quality) {
            itemInstance.setAttribute(ItemAttributes.quality, "iridium");
            return 2;
        }
        return 1;
    }
}
