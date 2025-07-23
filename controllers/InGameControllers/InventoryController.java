package com.untildawn.controllers.InGameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.InGameAssetManager;
import com.untildawn.models.AssetManager.InventoryAssetManager;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.PlayerAbilities;
import com.untildawn.views.InGameMenus.GameView;
import com.untildawn.views.InGameMenus.InventoryMenu;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class InventoryController {
    private InventoryMenu view;
    private GameView gameView;
    private final int inventorySlots = 9;
    private int selectedSlotIndex = -1;
    private final ArrayList<Stack> slotStacks = new ArrayList<>(9);
    private final Inventory inventory;
    private boolean isInventoryBarCreated = false;
    private Image background;
    private boolean isJournalOpen = false;

    public InventoryController() {
        inventory = App.getCurrentGame().getCurrentPlayer().getInventory();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void update() {
        if (!isInventoryBarCreated) {
            createInventoryBar();
            isInventoryBarCreated = true;
        }
        checkInput();
        updateTaskBar();
    }

    public void createInventoryBar() {
        TextureRegionDrawable slotDrawable = new TextureRegionDrawable(InventoryAssetManager.getSlot());
        Inventory inventory = App.getCurrentGame().getCurrentPlayer().getInventory();
        Table inventoryTable = gameView.getInventoryTable();
        inventoryTable.bottom().padBottom(10);
        inventoryTable.setFillParent(true);

        for (int i = 0; i < 9; i++) {

            Stack slotStack = new Stack();
            slotStacks.add(slotStack);

            Image slotImage = new Image(slotDrawable);
            slotStack.add(slotImage);

            Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), null);
            Label quantityLabel = new Label((i + 1) + "", labelStyle);
            quantityLabel.setFontScale(0.9f);
            quantityLabel.setAlignment(Align.topLeft);

            Table labelTable = new Table();
            labelTable.top().left().add(quantityLabel).padLeft(4f);
            slotStack.add(labelTable);

            inventoryTable.add(slotStack).size(64, 64).padBottom(40);

            ItemInstance item = inventory.getItemsInTaskBar()[i];
            if (item == null) continue;
            Texture texture = item.getTexture();
            Image icon = new Image(texture);
            icon.setSize(15, 15);
            icon.setScaling(Scaling.none);
            slotStack.add(icon);
        }

        gameView.getUiStage().addActor(inventoryTable);
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }

    public void checkTrashCanLevel(ItemDefinition item, int amount) {
        int price;
        Player player = App.getCurrentGame().getCurrentPlayer();
        ItemInstance trashCan = player.getTrashCan();
        if (item.hasAttribute(ItemAttributes.price)) {
            switch (trashCan.getDefinition().getId().name()) {
                case "copper_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.15 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "iron_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.3 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "golden_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.45 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "iridium_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.6 * amount);
                    player.getWallet().increaseCoin(price);
                }
            }
        } else if (item.hasAttribute(ItemAttributes.baseSellPrice)) {
            switch (trashCan.getDefinition().getId().name()) {
                case "copper_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.15 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "iron_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.3 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "golden_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.45 * amount);
                    player.getWallet().increaseCoin(price);
                }
                case "iridium_trash_can" -> {
                    price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.6 * amount);
                    player.getWallet().increaseCoin(price);
                }
            }
        }
    }

    private void updateTaskBar() {
        ItemInstance[] taskbarItems = inventory.getItemsInTaskBar();
        Player player = App.getCurrentGame().getCurrentPlayer();

        for (int i = 0; i < 9; i++) {
            Stack stack = slotStacks.get(i);
            while (stack.getChildren().size > 2) {
                stack.removeActor(stack.getChildren().peek());
            }


            ItemInstance item = taskbarItems[i];
            if (item != null) {
                Texture texture = item.getTexture();
                Image icon = new Image(texture);
                icon.setSize(15, 15);
                icon.setScaling(Scaling.none);
                stack.add(icon);
            }
            Image slotBackground = (Image) stack.getChildren().get(0);
            if (i + 1 == selectedSlotIndex) {
                player.setCurrentItem(item);
                slotBackground.setColor(1, 0, 0, 1);
            } else {
                slotBackground.setColor(1, 1, 1, 1);
            }
        }
    }

    private void checkInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) selectedSlotIndex = 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) selectedSlotIndex = 2;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) selectedSlotIndex = 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) selectedSlotIndex = 4;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) selectedSlotIndex = 5;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) selectedSlotIndex = 6;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) selectedSlotIndex = 7;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) selectedSlotIndex = 8;
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) selectedSlotIndex = 9;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) toggleInventoryWindow();
        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && gameView.isInventoryOpen()) toggleJournal();

    }

    private void toggleInventoryWindow() {
        if (gameView.isInventoryOpen()) {
            if (gameView.getFullInventoryTable() != null) {
                gameView.getFullInventoryTable().clear();
                gameView.getFullInventoryTable().remove();
            }
            if (background != null) {
                background.remove();
            }
            gameView.setIsInventoryOpen(false);
            createInventoryBar();
        } else {
            showFullInventory();
            gameView.getInventoryTable().clear();
            gameView.setIsInventoryOpen(true);
        }
    }

    private void showFullInventory() {
        DragAndDrop dragAndDrop = new DragAndDrop();

        Inventory inventory = App.getCurrentGame().getCurrentPlayer().getInventory();
        int size = inventory.getCapacity();

        Table fullInventoryTable = gameView.getFullInventoryTable();
        fullInventoryTable.clear();
        fullInventoryTable.remove();

        setBackground(fullInventoryTable);
        addInventorySectionTabs(fullInventoryTable, InGameAssetManager.getSkin());

        TextureRegionDrawable slotDrawable = new TextureRegionDrawable(InventoryAssetManager.getSlot());

        Image trashCan;
        ItemInstance playerTrashCan = App.getCurrentGame().getCurrentPlayer().getTrashCan();
        if (playerTrashCan.getDefinition().getId().name().contains("base")) {
            trashCan = new Image(InventoryAssetManager.getTrashCan());
        } else {
            trashCan = new Image(playerTrashCan.getTexture());
        }

        Image settingIcon = new Image(InventoryAssetManager.getSettingIcon());
        settingIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setting();
            }
        });
        int i = 0;
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
            if (i >= size) break;

            Stack slotStack = new Stack();
            slotStack.setTouchable(Touchable.enabled);
            Image slotImage = new Image(slotDrawable);
            slotStack.add(slotImage);
            setLabelForNumbers(entry.getValue().size(), slotStack);

            final ItemIDs currentID = entry.getKey();
            final ItemInstance item = entry.getValue().get(0);
            setIcon(item, slotStack);
            if (item != null) {
                dragAndDrop.addSource(new DragAndDrop.Source(slotStack) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(currentID);
                        Image dragIcon = new Image(item.getTexture());
                        dragIcon.setSize(32, 32);
                        payload.setDragActor(dragIcon);
                        return payload;
                    }
                });
            }

            dragAndDrop.addTarget(new DragAndDrop.Target(trashCan) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    ItemIDs draggedID = (ItemIDs) payload.getObject();
                    System.out.println(draggedID);
                    inventory.trashItemAll(draggedID);
                    checkTrashCanLevel(Objects.requireNonNull(App.getItemDefinition(draggedID.name())),
                        inventory.getItemAmount(draggedID));
                    showFullInventory();
                }
            });

            fullInventoryTable.add(slotStack).size(64, 64).pad(4f);
            if ((i + 1) % inventorySlots == 0) {
                fullInventoryTable.row();
            }
            i++;
        }

        while (i < size) {
            Stack emptySlot = new Stack();
            emptySlot.add(new Image(slotDrawable));
            fullInventoryTable.add(emptySlot).size(64, 64).pad(4f);
            if ((i + 1) % inventorySlots == 0) fullInventoryTable.row();
            i++;
        }
        fullInventoryTable.row();
        fullInventoryTable.add(trashCan).size(64, 64).pad(4f);
        fullInventoryTable.row();
        fullInventoryTable.add(settingIcon).size(64, 64).pad(4f);
    }

    private void setBackground(Table fullInventoryTable) {
        // Remove old actors if needed
        if (background != null) background.remove();

        background = new Image(new TextureRegionDrawable(new TextureRegion(InventoryAssetManager.getBackGround())));
        background.getColor().a = 0.85f;
        background.setScaling(Scaling.stretch);

        float bgWidth = 700f;
        float bgHeight = 700f;
        background.setSize(bgWidth, bgHeight);
        background.setPosition(
            (Gdx.graphics.getWidth() - bgWidth) / 2f,
            (Gdx.graphics.getHeight() - bgHeight) / 2f
        );

        // Clear previous table content (but don't remove the table actor itself!)
        fullInventoryTable.clear();
        fullInventoryTable.setFillParent(true);
        fullInventoryTable.padBottom(450);

        // Re-add in correct order: background first, then table
        gameView.getUiStage().addActor(background);
        gameView.getUiStage().addActor(fullInventoryTable);
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

    private void setIcon(ItemInstance item, Stack slotStack) {
        Image icon;
        if (item != null) {
            Texture texture = item.getTexture();
            icon = new Image(texture);
            icon.setSize(32, 32);
            icon.setScaling(Scaling.none);
            slotStack.add(icon);
        }
    }

    private void addInventorySectionTabs(Table fullInventoryTable, Skin skin) {
        Table sectionTabs = new Table();
        sectionTabs.defaults().height(32);

        TextureRegionDrawable[] icons = {
            new TextureRegionDrawable(new Texture(Gdx.files.internal("Images/Inventory/inventory.png"))),
            new TextureRegionDrawable(new Texture(Gdx.files.internal("Images/Inventory/skills.png"))),
            new TextureRegionDrawable(new Texture(Gdx.files.internal("Images/Inventory/social.png"))),
            new TextureRegionDrawable(new Texture(Gdx.files.internal("Images/Inventory/map.png")))
        };

        for (int i = 0; i < icons.length; i++) {
            ImageTextButton tab = new ImageTextButton("", skin);
            tab.getStyle().imageUp = icons[i];
            int finalI = i;
            tab.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    switch (finalI) {
                        case 0:
                            showFullInventory();
                            break;
                        case 1:
                            showSkill();
                            break;
                        case 2:
                            //TODO
                            break;
                        case 3:
                            //TODO
                            break;
                    }
                }
            });

            sectionTabs.add(tab).width(60).padRight(2 * (4 - i) + 100);
        }
        fullInventoryTable.add(sectionTabs).colspan(inventorySlots).padBottom(16f).row();
    }

    private void showSkill() {
        Table fullInventoryTable = gameView.getFullInventoryTable();
        Skin skin = InGameAssetManager.getSkin();
        fullInventoryTable.clear();
        fullInventoryTable.remove();
        setBackground(fullInventoryTable);
        addInventorySectionTabs(fullInventoryTable, InGameAssetManager.getSkin());

        Player player = App.getCurrentGame().getCurrentPlayer();
        PlayerAbilities abilities = player.getAbilities();

        int miningAbility = abilities.getAbilityLevel(abilities.getMiningAbility());
        int farmingAbility = abilities.getAbilityLevel(abilities.getFarmingAbility());
        int foragingAbility = abilities.getAbilityLevel(abilities.getNatureAbility());
        int fishingAbility = abilities.getAbilityLevel(abilities.getFishingAbility());

        Image miningSkill = new Image(InventoryAssetManager.getMiningSkill());
        Image farmingSkill = new Image(InventoryAssetManager.getFarmingSkill());
        Image foragingSkill = new Image(InventoryAssetManager.getForagingSkill());
        Image fishingSkill = new Image(InventoryAssetManager.getFishingSkill());

        ProgressBar.ProgressBarStyle barStyle = skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);
        ProgressBar miningProgressBar = new ProgressBar(0, 4, 1, false, barStyle);
        ProgressBar farmingProgressBar = new ProgressBar(0, 4, 1, false, barStyle);
        ProgressBar foragingProgressBar = new ProgressBar(0, 4, 1, false, barStyle);
        ProgressBar fishingProgressBar = new ProgressBar(0, 4, 1, false, barStyle);

        miningProgressBar.setValue(miningAbility);
        farmingProgressBar.setValue(farmingAbility);
        foragingProgressBar.setValue(foragingAbility);
        fishingProgressBar.setValue(fishingAbility);

        miningProgressBar.setAnimateDuration(0.3f);
        farmingProgressBar.setAnimateDuration(0.3f);
        foragingProgressBar.setAnimateDuration(0.3f);
        fishingProgressBar.setAnimateDuration(0.3f);

        miningProgressBar.setWidth(200);
        farmingProgressBar.setWidth(200);
        foragingProgressBar.setWidth(200);
        fishingProgressBar.setWidth(200);

        fullInventoryTable.padTop(400f);

        fullInventoryTable.add(miningSkill).width(64).pad(4f).padRight(500f).row();
        fullInventoryTable.add(farmingSkill).width(64).pad(4f).padRight(500f).row();
        fullInventoryTable.add(foragingSkill).width(64).pad(4f).padRight(500f).row();
        fullInventoryTable.add(fishingSkill).width(64).pad(4f).padRight(500f).row();

        miningSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Mining Ability", skin);
                dialog.text("Increases the efficiency \n of breaking rocks and ores.");
                dialog.button("OK");
                dialog.show(gameView.getUiStage());
            }
        });
        farmingSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Farming Ability", skin);
                dialog.text("Improves planting, watering,\n and harvesting crops.");
                dialog.button("OK");
                dialog.show(gameView.getUiStage());
            }
        });
        foragingSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Foraging Ability", skin);
                dialog.text("Boosts gathering wood and wild items.");
                dialog.button("OK");
                dialog.show(gameView.getUiStage());
            }
        });
        fishingSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Fishing Ability", skin);
                dialog.text("Enhances fishing success and speed.");
                dialog.button("OK");
                dialog.show(gameView.getUiStage());
            }
        });
    }

    private void toggleJournal() {
        if (isJournalOpen) {
            isJournalOpen = false;
            showFullInventory();
        } else {
            isJournalOpen = true;
            openJournal();
        }
    }

    private void openJournal() {
        //TODO
    }

    private void setting() {
        Skin skin = InGameAssetManager.getSkin();
        Dialog dialog = new Dialog("Settings", skin);

        Table content = new Table();

        TextButton exit = new TextButton("Exit", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // TODO
            }
        });

        content.add(exit).pad(5).row();
        dialog.getContentTable().add(content).pad(10).expand().fill();

        dialog.button("Close");

        dialog.show(gameView.getUiStage());

        dialog.setSize(400, 300);
        dialog.setPosition(
            (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
        );

        dialog.invalidateHierarchy();
    }

}



