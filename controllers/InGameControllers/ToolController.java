package com.untildawn.controllers.InGameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.models.Animals.Animal;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.InventoryAssetManager;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;
import com.untildawn.views.InGameMenus.GameView;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class ToolController {
    static ActionMenuView view;
    private GameView gameView;
    private boolean isToolMenuOpen = false;

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public ToolController(ActionMenuView view) {
        ToolController.view = view;
    }

    public ToolController() {

    }

//    public void useTool(Matcher matcher) {
//        Game game = App.getCurrentGame();
//        if (!game.isPlayerActive(game.getCurrentPlayer())) {
//            gameView.getMessage().showMessage("You are ran out of energy for this turn!");
//            return;
//        }
//        String direction = matcher.group("direction").trim();
//        Player player = game.getCurrentPlayer();
//        Tile tile = player.getPlayerTile(game);
//        ItemInstance tool = player.getCurrentItem();
//        if (tool == null) {
//            gameView.getMessage().showMessage("you don't have a tool in your hand!");
//            return;
//        }
//        switch (direction) {
//            case "up" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX()), player, game);
//            case "down" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX()), player, game);
//            case "left" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY(), (int) tile.getPosition().getX() - 1), player, game);
//            case "right" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY(), (int) tile.getPosition().getX() + 1), player, game);
//            case "up left" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX() - 1), player, game);
//            case "up right" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX() + 1), player, game);
//            case "down left" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX() - 1), player, game);
//            case "down right" -> applyTool(tool, game.getGameMap().getTile
//                ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX() + 1), player, game);
//            default -> gameView.getMessage().showMessage("please select a valid direction!");
//        }
//    }

    public void applyTool(ItemInstance tool, Tile tile, Player player, Game game,
                          TiledMapTileLayer.Cell cell) {
        String name = tool.getDefinition().getDisplayName().toLowerCase();
        if (name.contains("hoe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                (player.getAbilities().getFarmingAbility()))) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getFarmingAbility(), tool, player, true, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getPlowed()) {
                gameView.getMessage().showMessage("This tile has been plowed!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                gameView.getMessage().showMessage("You can't use hoe in lake!");
                return;
            }
            if (!tile.isEmpty()) {
                gameView.getMessage().showMessage("This tile is not empty!");
                return;
            }
            if (tile.getPlowed()) {
                gameView.getMessage().showMessage("This tile has already been plowed!");
                return;
            }
            tile.setPlowed(true);

            if (cell != null) {
                TiledMapTile newTile = App.getTileByType(game.getMap(), "Dirt Hoed");
                cell.setTile(newTile);
            }
            gameView.getMessage().showMessage("You've successfully plowed the tile!");
        } else if (name.contains("pickaxe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                (player.getAbilities().getMiningAbility()))) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getMiningAbility(), tool, player, true, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                gameView.getMessage().showMessage("You can't use pickaxe in lake!");
                return;
            }
            if (tile.isEmpty() && tile.isPlowed()) {
                tile.setPlowed(false);
                TiledMapTile newTile = App.getTileByType(game.getMap(), "Grass");
                cell.setTile(newTile);
                gameView.getMessage().showMessage("This tile has been successfully unplowed!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.rock)) {
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                gameView.getMessage().showMessage("You've removed rock from this tile!");
                TiledMapTileLayer objectLayer = (TiledMapTileLayer) game.getMap().getLayers().get(1);
                objectLayer.setCell((int) tile.getPosition().getX(), (int) tile.getPosition().getY(), null);
                return;
            }
            if (!tile.getItem().isDroppedByPlayer()) {
                gameView.getMessage().showMessage("This item hasn't been dropped by player!");
                return;
            }
            player.getInventory().addItem(tile.getItem());
            if (player.getAbilities().getAbilityLevel(player.getAbilities().getMiningAbility()) >= 2)
                player.getInventory().addItem(tile.getItem());
            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
            player.getAbilities().increaseMiningAbility();
            TiledMapTileLayer objectLayer = (TiledMapTileLayer) game.getMap().getLayers().get(1);
            objectLayer.setCell((int) tile.getPosition().getX(), (int) tile.getPosition().getY(), null);

            gameView.getMessage().showMessage("Item has been successfully removed from the tile!");
        } else if (name.contains("axe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                (player.getAbilities().getNatureAbility()))) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getNatureAbility(), tool, player, true, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                gameView.getMessage().showMessage("You can't use axe in lake!");
                return;
            }
            if (tile.isEmpty()) {
                gameView.getMessage().showMessage("This tile is empty!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.wood)) {//TODO
                player.getInventory().addItem(tile.getItem());
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                gameView.getMessage().showMessage("1 wood has been successfully added to the inventory!");
                TiledMapTileLayer objectLayer = (TiledMapTileLayer) game.getMap().getLayers().get(1);
                objectLayer.setCell((int) tile.getPosition().getX(), (int) tile.getPosition().getY(), null);
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.tree)) {
                String seedId = tile.getItem().getAttribute(ItemAttributes.source).toString();
                ItemInstance seed = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(seedId)));
                player.getInventory().addItem(seed);
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("wood"))));
                //TODO
                gameView.getMessage().showMessage("You've successfully cut the tree and a " + seedId + " is added to your inventory!");
                return;
            }

            gameView.getMessage().showMessage("Use axe in a proper tile");
        } else if (name.contains("watering can")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                (player.getAbilities().getFarmingAbility()))) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getFarmingAbility(), tool, player, false, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                int x = (int) tile.getPosition().getX();
                int y = (int) tile.getPosition().getY();
                if(x - 1 <= player.getPosition().getX() && player.getPosition().getX() <= x + 1
                && y - 1 <= player.getPosition().getY() && player.getPosition().getY() <= y + 1) {
                    gameView.getMessage().showMessage("You should be near lake to take water from it!");
                    return;
                }
                addWaterToWateringCan(tool);
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.all_crops)) {
                if (tile.hasGiantPlant()) {
                    for (Tile giantTile : tile.getGiantGroup()) {
                        giantTile.setWatered(true);
                    }
                } else {
                    tile.setWatered(true);
                }
                return;
            }
            gameView.getMessage().showMessage("Use the can for lake or plants!");
        } else if (name.contains("fishing pole")) {

        } else if (name.contains("scythe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.fiber)) {
                cutFiber(tile, player);
                return;
            }
            ItemInstance item = tile.getItem();
            if (item.getDefinition().getType().equals(ItemType.all_crops)) {
                if (tile.getDayLeftFromPlant() <= tile.getDayPassedFromPlant()) {
                    if (tile.hasGiantPlant()) {
                        harvestGiantCrop(tile, player);
                    } else {
                        harvestCrop(tile, player);
                    }
                    return;
                } else {
                    gameView.getMessage().showMessage("This plant isn't harvestable yet!");
                    return;
                }
            }
            gameView.getMessage().showMessage("Use scythe for crops or fibers!");
        } else if (name.contains("milk pale")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.barn_animal)) {
                Animal animal = (Animal) tile.getItem();
                milkAnimal(tool, player, animal);
                return;
            }
            gameView.getMessage().showMessage("Use milk pale on cows, sheep or goats!");

        } else if (name.contains("shear")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                gameView.getMessage().showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getId().equals(ItemIDs.sheep)) {
                Animal animal = (Animal) tile.getItem();
                cutWool(player, animal);
            }
        } else {
            gameView.getMessage().showMessage("Please select a valid tool!");
        }
    }

    public boolean checkIfPlayerHasEnoughEnergy(Player player, ItemInstance tool, int ability) {
        if (ability == 4 && player.getEnergy() < (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost) - 1) {
            return false;
        }
        if (player.getEnergy() < (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost)) {
            return false;
        }
        return true;
    }

    public void addWaterToWateringCan(ItemInstance tool) {
        int capacity = (int) tool.getDefinition().getAttribute(ItemAttributes.capacity);
        int durability = (int) tool.getDefinition().getAttribute(ItemAttributes.durability);
        if (durability + 1 > capacity) {
            gameView.getMessage().showMessage("your can is full!");
            return;
        }
        tool.increaseDurability(durability + 1);
        int finalDurability = durability + 1;
        gameView.getMessage().showMessage("Your can has " + finalDurability + " water");
    }

    public void cutFiber(Tile tile, Player player) {
        player.getInventory().addItem(tile.getItem());
        tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
        gameView.getMessage().showMessage("You've cut fiber!");
    }

    public void harvestCrop(Tile tile, Player player) {
        player.getInventory().addItem(tile.getItem());
        ItemInstance plant = tile.getItem();
        if (plant.getDefinition().hasAttribute(ItemAttributes.oneTime)
            && !(boolean) plant.getAttribute(ItemAttributes.oneTime)) {
            tile.setItem(new ItemInstance(Objects.requireNonNull(
                App.getItemDefinition(plant.getDefinition().getId().name()))));
            tile.setDayPassedFromPlant(0);
            tile.setDayLeftFromPlant((int) plant.getAttribute(ItemAttributes.regrowthTime));
        }
        tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
        player.getAbilities().increaseFarmingAbility();
        gameView.getMessage().showMessage("You've harvested crop!");
    }

    public void harvestGiantCrop(Tile tile, Player player) {
        for (int i = 0; i < 10; i++) {
            player.getInventory().addItem(tile.getItem());
        }
        ItemInstance plant = tile.getItem();
        if (plant.getDefinition().hasAttribute(ItemAttributes.oneTime)
            && !(boolean) plant.getAttribute(ItemAttributes.oneTime)) {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setItem(new ItemInstance(Objects.requireNonNull
                    (App.getItemDefinition(plant.getDefinition().getId().name()))));
                tile.setDayPassedFromPlant(0);
                tile.setDayLeftFromPlant((int) plant.getAttribute(ItemAttributes.regrowthTime));
            }
        } else {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
            }
        }
        player.getAbilities().increaseFarmingAbility();
        gameView.getMessage().showMessage("You've harvested giant crop!");
    }

    public void milkAnimal(ItemInstance tool, Player player, Animal animal) {
        tool.setAttribute(ItemAttributes.isFull, true);
        player.getInventory().addItem
            (new ItemInstance(Objects.requireNonNull(App.getItemDefinition("milk"))));
        player.getAbilities().increaseFarmingAbility();
        animal.increaseFriendShip(5);
        gameView.getMessage().showMessage("You've milked the animal!");
    }

    public void cutWool(Player player, Animal animal) {
        animal.setAttribute(ItemAttributes.isCut, true);
        player.getInventory().addItem
            (new ItemInstance(Objects.requireNonNull(App.getItemDefinition("wool"))));
        player.getAbilities().increaseFarmingAbility();
        animal.increaseFriendShip(5);
        gameView.getMessage().showMessage("You've collected sheep wool!");
    }

    public void upgradeTool(Matcher matcher) {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        int playerX = (int) player.getPosition().getX();
        int playerY = (int) player.getPosition().getY();
        int pierreX = NPCConst.ShopPositions.PierreGeneralStore.getX();
        int pierreY = NPCConst.ShopPositions.PierreGeneralStore.getY();
        int smithX = NPCConst.ShopPositions.BlackSmith.getX();
        int smithY = NPCConst.ShopPositions.BlackSmith.getY();
        Inventory inventory = player.getInventory();
        String toolName = matcher.group("toolName").trim().toLowerCase();
        ItemIDs id;
        try {
            id = ItemIDs.valueOf(toolName);
        } catch (IllegalArgumentException e) {
            gameView.getMessage().showMessage("Invalid tool name!");
            return;
        }
        if (toolName.contains("backpack")) {
            if (!(pierreX - 1 <= playerX && playerX <= pierreX + 1
                && playerY - 1 <= playerY && playerY <= pierreY + 1)) {
                gameView.getMessage().showMessage("You should be near pierre's store to upgrade your backpack!");
                return;
            }
            if (!inventory.hasItem(id)) {
                gameView.getMessage().showMessage("This item does not exist in your inventory!");
                return;
            }
            ItemInstance backpack = inventory.getItem(id);
            if (player.getWallet().getCoin() < (int) backpack.getAttribute(ItemAttributes.upgradeCost)) {
                gameView.getMessage().showMessage("You can't afford to upgrade your backpack!");
                return;
            }
            if (toolName.contains("deluxe")) {
                gameView.getMessage().showMessage("You can't upgrade your backpack anymore!");
                return;
            }
            inventory.trashItemAll(id);
            player.getWallet().decreaseCoin((int) backpack.getAttribute(ItemAttributes.upgradeCost));
            if (toolName.contains("base")) {
                inventory.addItem(new ItemInstance(Objects.requireNonNull
                    (App.getItemDefinition("big_backpack"))));
                gameView.getMessage().showMessage("You've upgraded your backpack to large backpack!");
            } else if (toolName.contains("big")) {
                inventory.addItem(new ItemInstance(Objects.requireNonNull
                    (App.getItemDefinition("deluxe_backpack"))));
                gameView.getMessage().showMessage("You've upgraded your backpack to deluxe backpack!");
            }
        } else {
            if (!(smithX - 1 <= playerX && playerX <= smithX + 1
                && smithY - 1 <= playerY && playerY <= smithY + 1)) {
                gameView.getMessage().showMessage("You should be near blackSmith store to upgrade your tools!");
                return;
            }
            if (!inventory.hasItem(id)) {
                gameView.getMessage().showMessage("This item does not exist in your inventory!");
                return;
            }
            ItemInstance tool = inventory.getItem(id);
            if (player.getWallet().getCoin() < (int) tool.getAttribute(ItemAttributes.upgradeCost)) {
                gameView.getMessage().showMessage("You can't afford to upgrade your tool!");
                return;
            }
            if (toolName.contains("base")) {
                if (!inventory.hasItem(ItemIDs.valueOf("copper_bar"), 5)) {
                    gameView.getMessage().showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("copper_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("base", "copper");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                gameView.getMessage().showMessage("You've upgraded your tool!");

            } else if (toolName.contains("copper")) {
                if (!inventory.hasItem(ItemIDs.valueOf("iron_bar"), 5)) {
                    gameView.getMessage().showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("iron_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("copper", "iron");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                gameView.getMessage().showMessage("You've upgraded your tool!");
            } else if (toolName.contains("iron")) {
                if (!inventory.hasItem(ItemIDs.valueOf("gold_bar"), 5)) {
                    gameView.getMessage().showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("gold_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("iron", "golden");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                gameView.getMessage().showMessage("You've upgraded your tool!");
            } else if (toolName.contains("golden")) {
                if (!inventory.hasItem(ItemIDs.valueOf("iridium_bar"), 5)) {
                    gameView.getMessage().showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("iridium_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("golden", "iridium");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                gameView.getMessage().showMessage("You've upgraded your tool!");
            } else if (toolName.contains("iridium")) {
                gameView.getMessage().showMessage("You can't afford to upgrade your tool anymore!");
                return;
            }
        }
    }

    public void update() {
        checkInput();
    }


    private void checkInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) toggleToolMenu();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) ;

    }

    private void toggleToolMenu() {
        if (isToolMenuOpen) {
            isToolMenuOpen = false;
            gameView.getToolTable().clear();
            gameView.getToolTable().remove();

        } else {
            isToolMenuOpen = true;
            showToolsInInventory();
        }
    }

    private void showToolsInInventory() {
        Table toolTable = gameView.getToolTable();
        toolTable.clear();
        toolTable.setFillParent(true);
        toolTable.center();

        Player player = App.getCurrentGame().getCurrentPlayer();
        Inventory inventory = player.getInventory();
        int i = 0;
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
            i++;
            ItemInstance item = entry.getValue().get(0);
            if (!item.getDefinition().getType().equals(ItemType.tool)) continue;

            Image toolImage = new Image(item.getTexture());


            int finalI = i;
            toolImage.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    ItemInstance tool = inventory.getItemByIndex(finalI - 1);
                    player.setCurrentItem(tool);
                    gameView.getMessage().showMessage("Your current tool in now " +
                        tool.getDefinition().getDisplayName());
                }
            });

            TextureRegionDrawable slotDrawable = new TextureRegionDrawable(InventoryAssetManager.getSlot());
            Stack slotStack = new Stack();
            slotStack.setTouchable(Touchable.enabled);
            Image slotImage = new Image(slotDrawable);
            toolImage.setSize(15, 15);
            toolImage.setScaling(Scaling.none);
            slotStack.add(slotImage);
            int number = entry.getValue().size();
            setLabelForNumbers(number, slotStack);
            slotStack.add(toolImage);

            Image slotBackground = (Image) slotStack.getChildren().get(0);

            if (item.equals(player.getCurrentItem())) {
                slotBackground.setColor(1, 0, 0, 1);
            }

            toolTable.add(slotStack).size(64, 64).pad(4f);
        }
        gameView.getUiStage().addActor(toolTable);
    }

    private void setLabelForNumbers(int count, Stack slotStack) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), null);
        Label quantityLabel = new Label("x" + count, labelStyle);
        quantityLabel.setFontScale(0.8f);
        quantityLabel.setAlignment(Align.bottomRight);
        Table labelTable = new Table();
        labelTable.bottom().right().add(quantityLabel).pad(2f);
        slotStack.add(labelTable);
    }
}
