package com.untildawn.models.NPCs;

import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.MapElements.Position;


import java.util.*;

public class MakeNPC {
    public static void makeNPC() {
        // Sebastian
        ArrayList<Quest> sebastianQuests = new ArrayList<>();
        sebastianQuests.add(new Quest(App.getItemDefinition("iron"), 50,
                                      App.getItemDefinition("diamond"), 2));
        sebastianQuests.add(new Quest(App.getItemDefinition("pumpkin_pie"), 1,
                                      App.getItemDefinition("gold_coin"), 5000));
        sebastianQuests.add(new Quest(App.getItemDefinition("rock"), 150,
                                      App.getItemDefinition("quartz"), 50));
        ArrayList<ItemDefinition> sebastianFavorites = new ArrayList<>();
        sebastianFavorites.add(App.getItemDefinition("wool"));
        sebastianFavorites.add(App.getItemDefinition("pumpkin_pie"));
        sebastianFavorites.add(App.getItemDefinition("pizza"));

        NPC sebastian = new NPC("Sebastian", Gender.MALE, NPCConst.Professions.LAZY_BUM,
                                sebastianFavorites, sebastianQuests,
                                new Position(NPCConst.HomePositions.Sebastian.getY(),
                                             NPCConst.HomePositions.Sebastian.getX() - 1), 3);

        for (Quest quest : sebastian.getQuests()) quest.setNpc(sebastian);


        // Abigail
        ArrayList<Quest> abigailQuests = new ArrayList<>();
        abigailQuests.add(new Quest(App.getItemDefinition("gold_bar"), 1,
                App.getItemDefinition("friendship_level"), 1));
        abigailQuests.add(new Quest(App.getItemDefinition("pumpkin"), 1,
                App.getItemDefinition("gold_coin"), 500));
        abigailQuests.add(new Quest(App.getItemDefinition("wheat"), 50,
                App.getItemDefinition("iridium_sprinkler"), 1));
        ArrayList<ItemDefinition> abigailFavorites = new ArrayList<>();
        abigailFavorites.add(App.getItemDefinition("rock"));
        abigailFavorites.add(App.getItemDefinition("iron"));
        abigailFavorites.add(App.getItemDefinition("coffee"));

        NPC abigail = new NPC("Abigail", Gender.FEMALE, NPCConst.Professions.LAZY_BUM,
                abigailFavorites, abigailQuests,
                new Position(NPCConst.HomePositions.Abigail.getY(),
                        NPCConst.HomePositions.Abigail.getX() - 1), 8);

        for (Quest quest : abigail.getQuests()) quest.setNpc(abigail);

        // Harvey
        ArrayList<Quest> harveyQuests = new ArrayList<>();
        harveyQuests.add(new Quest(App.getItemDefinition("blue_jazz"), 12, // should be all plants
                App.getItemDefinition("gold_coin"), 750));
        harveyQuests.add(new Quest(App.getItemDefinition("salmon"), 1,
                App.getItemDefinition("friendship_level"), 1));
        harveyQuests.add(new Quest(App.getItemDefinition("wine"), 1,
                App.getItemDefinition("salad"), 5));
        ArrayList<ItemDefinition> harveyFavorites = new ArrayList<>();
        harveyFavorites.add(App.getItemDefinition("coffee"));
        harveyFavorites.add(App.getItemDefinition("pickle"));
        harveyFavorites.add(App.getItemDefinition("wine"));

        NPC harvey = new NPC("Harvey", Gender.MALE, NPCConst.Professions.LAZY_BUM,
                harveyFavorites, harveyQuests,
                new Position(NPCConst.HomePositions.Harvey.getY(),
                        NPCConst.HomePositions.Harvey.getX() - 1), 4);

        for (Quest quest : harvey.getQuests()) quest.setNpc(harvey);

        // Lia
        ArrayList<Quest> liaQuests = new ArrayList<>();
        liaQuests.add(new Quest(App.getItemDefinition("wood"), 10, // should be hard wood
                App.getItemDefinition("gold_coin"), 500));
        liaQuests.add(new Quest(App.getItemDefinition("salmon"), 1,
                App.getItemDefinition(""), 1)); // recipe
        liaQuests.add(new Quest(App.getItemDefinition("wood"), 200,
                App.getItemDefinition("deluxe_scarecrow"), 3));
        ArrayList<ItemDefinition> liaFavorites = new ArrayList<>();
        liaFavorites.add(App.getItemDefinition("salad"));
        liaFavorites.add(App.getItemDefinition("grape"));
        liaFavorites.add(App.getItemDefinition("wine"));

        NPC lia = new NPC("Lia", Gender.FEMALE, NPCConst.Professions.LAZY_BUM,
                liaFavorites, liaQuests,
                new Position(NPCConst.HomePositions.Lia.getY(),
                        NPCConst.HomePositions.Lia.getX() - 1), 12);

        for (Quest quest : lia.getQuests()) quest.setNpc(lia);

        // Robin
        ArrayList<Quest> robinQuests = new ArrayList<>();
        robinQuests.add(new Quest(App.getItemDefinition("wood"), 80,
                App.getItemDefinition("gold_coin"), 1000));
        robinQuests.add(new Quest(App.getItemDefinition("iron_bar"), 10,
                App.getItemDefinition("bee_house"), 3));
        robinQuests.add(new Quest(App.getItemDefinition("wood"), 1000,
                App.getItemDefinition("gold_coin"), 25000));
        ArrayList<ItemDefinition> robinFavorites = new ArrayList<>();
        robinFavorites.add(App.getItemDefinition("spaghetti"));
        robinFavorites.add(App.getItemDefinition("wood"));
        robinFavorites.add(App.getItemDefinition("iron_bar"));

        NPC robin = new NPC("Robin", Gender.MALE, NPCConst.Professions.WOOD_SELLER,
                robinFavorites, robinQuests,
                new Position(NPCConst.HomePositions.Robin.getY(),
                        NPCConst.HomePositions.Robin.getX() - 1), 3);

        for (Quest quest : robin.getQuests()) quest.setNpc(robin);
        // Kian
        NPC Kian = new NPC("Kian", Gender.OTHER, NPCConst.Professions.BEING_KIAN,
                new ArrayList<>(), new ArrayList<>(), new Position(31, 57), Integer.MAX_VALUE);


        Game game = App.getCurrentGame();
        game.addNPC(sebastian);
        game.addNPC(abigail);
        game.addNPC(harvey);
        game.addNPC(lia);
        game.addNPC(robin);
        game.addNPC(Kian);
    }
}
