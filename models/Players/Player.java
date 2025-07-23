package com.untildawn.models.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.models.Animals.Animal;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.InGameAssetManager;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Items.Refrigerator;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.NPCs.Quest;
import com.untildawn.models.User;

import java.util.ArrayList;
import java.util.Objects;

/*
    When a game is made, it has players; and when users enter the game, they become players of that game.
 */
public class Player {
    private User user;
    private String name;
    private Gender gender;
    private int energy;
    private int energyLimit;
    private Wallet wallet;
    private Inventory inventory;
    private PlayerAbilities abilities;
    private Position position;
    private ItemInstance currentItem;
    private ItemInstance trashCan;
    private int energyPerTurn;
    private ArrayList<Quest> activeQuests;
    private ArrayList<Gift> giftsReceived;
    private ArrayList<String> messages;
    private PlayerMap playerMap;
    private ArrayList<Animal> animals;
    private boolean isFainted;
    private Position cottagePosition;
    private Player spouse;
    private int rejectedDays;
    private ArrayList<Trade> trades;
    private boolean fishingEnable;
    private boolean miningEnable;
    private boolean foragingEnable;
    private boolean farmingEnable;
    private boolean maxEnergyEnable;
    private Refrigerator refrigerator;
    private Sprite itemInHandSprite;

    // graphics
    private final Texture texture;
    private Sprite sprite;
    private final CollisionRect collisionRect;
    private final float width = 100;
    private final float height = 150;
    private final float speed = 10;

    public Player(User user, String name, Gender gender, Position position) {
        this.user = user;
        this.name = name;
        this.gender = gender;
        this.energy = 200; // initial energy
        this.energyLimit = 200;
        this.wallet = new Wallet(0); // initial coin
        this.inventory = new Inventory();
        this.abilities = new PlayerAbilities();
        this.activeQuests = new ArrayList<>();
        this.position = position; // initial position
        this.energyPerTurn = 50;
        this.trashCan = new ItemInstance(Objects.requireNonNull(App.getItemDefinition("base_trash_can")));
        this.giftsReceived = new ArrayList<>();
        this.messages = new ArrayList<>();
        animals = new ArrayList<>();
        this.inventory.setInventoryTools();
        this.isFainted = false;
        this.spouse = null;
        this.rejectedDays = 0;
        this.trades = new ArrayList<>();

        this.texture = InGameAssetManager.getInGameAssetManager().getCharacterTexture();
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.collisionRect = new CollisionRect(
            position.getX(),
            position.getY(),
            (float) texture.getWidth(),
            (float) texture.getHeight()
        );

    }

    public Wallet getWallet() {
        return wallet;
    }

    public ItemInstance getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ItemInstance currentItem) {
        this.currentItem = currentItem;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ItemInstance getTrashCan() {
        return trashCan;
    }

    public int getEnergy() {
        return energy;
    }

    public PlayerAbilities getAbilities() {
        return abilities;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public int getEnergyLimit() {
        return energyLimit;
    }

    public void setEnergyLimit(int energyLimit) {
        this.energyLimit = energyLimit;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public PlayerMap getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(PlayerMap playerMap) {
        this.playerMap = playerMap;
    }

    public void setAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public int getEnergyPerTurn() {
        return energyPerTurn;
    }

    public void setEnergyPerTurn(int energyPerTurn) {
        this.energyPerTurn = energyPerTurn;
    }

    public Tile getPlayerTile(Game game) {
        GameMap gameMap = game.getGameMap();
        return gameMap.getTile((int) this.position.getY(), (int) this.position.getX());
    }

    public void decreaseEnergy(int deltaEnergy) {
        this.energy = Math.max(0, this.energy - deltaEnergy);
        this.energyPerTurn = Math.max(0, this.energyPerTurn - deltaEnergy);
        if (this.energy <= 0) {
            this.isFainted = true;
        }
    }

    public void increaseEnergy(int deltaEnergy) {
        this.energy = Math.min(energyLimit, this.energy + deltaEnergy);
    if(isFainted){
        this.isFainted = false;
    }
    }

    public void reduceEnergy(int ability, ItemInstance tool, Player player,
                             boolean canBeDownGraded, Game game, int energyCost) {
        double rate = 1;
        if (game.getWeather().getCurrentWeather().equals(WeatherStates.SNOWY)) rate = 2;
        if (game.getWeather().getCurrentWeather().equals(WeatherStates.RAIN)
            || game.getWeather().getCurrentWeather().equals(WeatherStates.STORM)) rate = 1.5;
        if (ability == 4) {
            decreaseEnergy((int) rate * (energyCost - 1));
        } else {
            decreaseEnergy((int) rate * energyCost);
        }
        if (canBeDownGraded) {
            int x = tool.decreaseDurability();
            if (x == 0)
                player.changeToolLevel(tool);
        }
    }

    public void addQuest(Quest quest) {
        this.activeQuests.add(quest);
    }

    public ArrayList<Quest> getActiveQuests() {
        return activeQuests;
    }

    public Quest getActiveQuest(int number) {
        try {
            return this.activeQuests.get(number - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addGift(Gift gift) {
        this.giftsReceived.add(gift);
    }

    public ArrayList<Gift> getGiftsReceived() {
        return giftsReceived;
    }


    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void setInventoryTools() {
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("base_hoe"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("base_pickaxe"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("base_axe"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("base_watering_can"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("training_fishing_pole"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("scythe"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("milk_pale"))));
        this.inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("shear"))));
    }

    public void finishQuest(Quest quest) {
        this.activeQuests.remove(quest);
    }


    public void changeToolLevel(ItemInstance tool) {
        String name = tool.getDefinition().getDisplayName().toLowerCase();
        if (name.contains("hoe")) {
            changeInventoryTool(tool, "base_hoe");
        } else if (name.contains("pickaxe")) {
            changeInventoryTool(tool, "base_pickaxe");
        } else if (name.contains("axe")) {
            changeInventoryTool(tool, "base_axe");
        } else if (name.contains("watering can")) {
            changeInventoryTool(tool, "base_watering_can");
        } else if (name.contains("fishing pole")) {
            changeInventoryTool(tool, "training_fishing_pole");
        }
    }

    public void changeInventoryTool(ItemInstance tool, String newToolName) {
        this.inventory.getItems().remove(tool);
        ItemInstance newTool = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName)));
        ArrayList<ItemInstance> items = new ArrayList<>();
        items.add(newTool);
        this.inventory.getItems().put(newTool.getDefinition().getId(), items);
        this.currentItem = newTool;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public void setCottagePosition(Position cottagePosition) {
        this.cottagePosition = cottagePosition;
    }

    public Position getCottagePosition() {
        return cottagePosition;
    }

    public Player getSpouse() {
        return spouse;
    }

    public void setSpouse(Player spouse) {
        this.spouse = spouse;
    }

    public void setRejectedDays(int rejectedDays) {
        this.rejectedDays = rejectedDays;
    }

    public int getRejectedDays() {
        return rejectedDays;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public boolean isMaxEnergyEnable() {
        return maxEnergyEnable;
    }

    public void setMaxEnergyEnable(boolean maxEnergyEnable) {
        this.maxEnergyEnable = maxEnergyEnable;
    }

    public boolean isFarmingEnable() {
        return farmingEnable;
    }

    public void setFarmingEnable(boolean farmingEnable) {
        this.farmingEnable = farmingEnable;
    }

    public boolean isForagingEnable() {
        return foragingEnable;
    }

    public void setForagingEnable(boolean foragingEnable) {
        this.foragingEnable = foragingEnable;
    }

    public boolean isMiningEnable() {
        return miningEnable;
    }

    public void setMiningEnable(boolean miningEnable) {
        this.miningEnable = miningEnable;
    }

    public boolean isFishingEnable() {
        return fishingEnable;
    }

    public void setFishingEnable(boolean fishingEnable) {
        this.fishingEnable = fishingEnable;
    }

    public void reduceEnergyWhenCrafting(int amount) {
        this.energy -= amount;
    }

    public Refrigerator getRefrigerator() {
        return this.refrigerator;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setItemInHandSprite(Sprite itemInHandSprite) {
        this.itemInHandSprite = itemInHandSprite;
    }

    public Sprite getItemInHandSprite() {
        return itemInHandSprite;
    }


    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setSize(width, height);
    }
}

