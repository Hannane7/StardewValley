package com.untildawn.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.untildawn.Enums.GameConsts.Seasons;
import com.untildawn.Enums.GameConsts.WeatherStates;
import com.untildawn.controllers.utils.GenerateRandomNumber;
import com.untildawn.controllers.UpdateControllers.UpdateByDay;
import com.untildawn.controllers.UpdateControllers.UpdateByHour;
import com.untildawn.models.Items.ShippingBin;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.PlayerMap;
import com.untildawn.models.MapElements.Shop;
import com.untildawn.models.NPCs.NPC;
import com.untildawn.models.NPCs.NPCRelation;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Players.PlayerRelation;
import com.untildawn.models.States.DateTime;
import com.untildawn.models.States.Weather;


import java.util.*;

public class Game {
    private ArrayList<Player> gamePlayers;
    private Map<Player, PlayerMap> playerMaps;
    private Player currentPlayer;
    private DateTime dateTime;
    private Weather weather;
    private GameMap gameMap;
    private ArrayList<NPC> NPCs;
    private ArrayList<Shop> shops;
    private ArrayList<NPCRelation> relations;
    private ArrayList<PlayerRelation> playerRelations;
    private ShippingBin shippingBin;
    private UpdateByHour updaterByHour;
    private UpdateByDay updaterByDay;
    private Weather tomorrowWeather;
    private TiledMap map;

    public Game(ArrayList<Player> gamePlayers, Map<Player, PlayerMap> playerMaps, Player currentPlayer, GameMap gameMap) {
        this.gamePlayers = gamePlayers;
        this.playerMaps = playerMaps;
        this.currentPlayer = currentPlayer;
        this.dateTime = new DateTime();
        this.weather = new Weather();
        this.tomorrowWeather = new Weather();
        setTomorrowWeather();
        this.gameMap = gameMap;
        this.NPCs = new ArrayList<>();
        this.shops = new ArrayList<>();
        this.shippingBin = new ShippingBin();
        this.updaterByDay = new UpdateByDay(this);
        this.updaterByHour = new UpdateByHour(this);
        this.map = new TmxMapLoader().load("Map1.tmx");
    }

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }


    public GameMap getGameMap() {
        return gameMap;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Weather getWeather() {
        return this.weather;
    }

    public PlayerMap getPlayerMap(Player player) {
        return playerMaps.get(player);
    }

    public boolean gameHasPlayer(Player player) {
        return gamePlayers.contains(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getNextPlayer() {
        return gamePlayers.get((this.gamePlayers.indexOf(this.currentPlayer) + 1) % (this.gamePlayers.size()));
    }

    public Player getPlayerByUsername(String username) {
        for (Player player : this.gamePlayers) {
            if (player.getUser().getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<PlayerMap> getPlayerMaps() {
        return new ArrayList<>(playerMaps.values());
    }
    public Map<Player, PlayerMap> getPlayerAndPlayerMaps() { return playerMaps; }

    public ArrayList<Player> getPlayers() {
        return gamePlayers;
    }

    public void addShop(Shop shop) {
        this.shops.add(shop);
    }

    public void addNPC(NPC npc) {
        this.NPCs.add(npc);
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public NPC getNPC(String name) {
        for (NPC npc : this.NPCs) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }

    public NPCRelation getRelation(Player player, NPC npc) {
        for (NPCRelation relation : this.relations) {
            if (relation.getPlayer() == player
                    && relation.getNpc() == npc) {
                return relation;
            }
        }
        return null;
    }

    public void addRelation(NPCRelation relation) {
        this.relations.add(relation);
    }

    public void setRelations(ArrayList<NPCRelation> relations) {
        this.relations = relations;
    }

    public void setPlayerRelations(ArrayList<PlayerRelation> playerRelations) {
        this.playerRelations = playerRelations;
    }

    public PlayerRelation getPlayerRelation(Player player1, Player player2) {
        for (PlayerRelation playerRelation : this.playerRelations) {
            Player p1 = playerRelation.getPlayer1();
            Player p2 = playerRelation.getPlayer2();

            if ((p1 == player1 && p2 == player2)
                    || (p2 == player1 && p1 == player2)) {
                return playerRelation;
            }
        }

        return null;
    }

    public Shop getShop(String targetShop) {
        for (Shop shop : this.shops) {
            if (shop.getShopName().equalsIgnoreCase(targetShop)) {
                return shop;
            }
        }
        return null;
    }

    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public void setTurn(Player player) {
        this.setCurrentPlayer(player);
    }

    public boolean isPlayerActive(Player player) {
        return player.getEnergyPerTurn() > 0;
    }

    public void updateByHour(boolean isCheat) {
        this.updaterByHour.execute(isCheat);
    }

    public void updateByDay(boolean isCheat) {
        this.updaterByDay.execute(isCheat);
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
    public void setTomorrowWeather() {
        Seasons seasons = this.getDateTime().getSeason();
        int weatherRandomVariable = GenerateRandomNumber.generateRandomNumber(1, 10);
        WeatherStates weatherStates = null;
        switch (seasons.name().toLowerCase()) {
            case "spring":
                if (1 <= weatherRandomVariable && weatherRandomVariable <= 5) weatherStates = WeatherStates.SUNNY;

                else if (6 <= weatherRandomVariable && weatherRandomVariable <= 8) weatherStates = WeatherStates.RAIN;

                else weatherStates = WeatherStates.STORM;
                break;
            case "summer":
                if (1 <= weatherRandomVariable && weatherRandomVariable <= 8) weatherStates = WeatherStates.SUNNY;

                else if (weatherRandomVariable == 9) weatherStates = WeatherStates.RAIN;

                else weatherStates = WeatherStates.STORM;
                break;
            case "fall":
                if (1 <= weatherRandomVariable && weatherRandomVariable <= 2) weatherStates = WeatherStates.SUNNY;

                else if (3 <= weatherRandomVariable && weatherRandomVariable <= 7) weatherStates = WeatherStates.RAIN;

                else weatherStates = WeatherStates.STORM;
                break;
            case "winter":
                if (1 <= weatherRandomVariable && weatherRandomVariable <= 3) weatherStates = WeatherStates.SUNNY;
                else weatherStates = WeatherStates.SNOWY;
                break;
        }
        this.tomorrowWeather.setCurrentWeather(weatherStates);
    }

    public ArrayList<NPCRelation> getRelations() {
        return relations;
    }

    public ArrayList<PlayerRelation> getPlayerRelations() {
        return playerRelations;
    }
    public boolean hasAllPlayersFainted() {
        return getPlayers().get(0).isFainted()
                && getPlayers().get(1).isFainted()
                && getPlayers().get(2).isFainted()
                && getPlayers().get(3).isFainted();
    }

    public TiledMap getMap() {
        return map;
    }
}
