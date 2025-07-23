package com.untildawn.models.NPCs;

import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.MapElements.GameMap;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.MapElements.Shop;


import java.util.ArrayList;

public class MakeShops {
    public static void makeShops() {

        shopMaker("BlackSmith", NPCConst.ShopPositions.BlackSmith,
                "Clint", Gender.MALE, NPCConst.Professions.BLACK_SMITH,
                9, 14);

        shopMaker("JojaMart", NPCConst.ShopPositions.JojaMart,
                "Morris", Gender.MALE, NPCConst.Professions.FARMER,
                9, 21);

        shopMaker("Pierre's General Store", NPCConst.ShopPositions.PierreGeneralStore,
                "Pierre", Gender.MALE, NPCConst.Professions.FARMER,
                9, 15);

        shopMaker("Carpenter's Shop", NPCConst.ShopPositions.CarpenterShop,
                "Robin", Gender.MALE, NPCConst.Professions.WOOD_SELLER,
                9, 18);

        shopMaker("Fish Shop", NPCConst.ShopPositions.FishShop,
                "Willy", Gender.MALE, NPCConst.Professions.FISHERMAN,
                9, 15);

        shopMaker("Marnie's Ranch", NPCConst.ShopPositions.MarnieRanch,
                "Marnie", Gender.FEMALE, NPCConst.Professions.RANCHER,
                9, 14);

        shopMaker("The Stardrop Saloon", NPCConst.ShopPositions.StarDropSaloon,
                "Gus", Gender.MALE, NPCConst.Professions.BARISTA,
                12, 22);


    }

    private static void shopMaker(String shopName, NPCConst.ShopPositions shopPosition,
                           String NPCName, Gender gender,
                           NPCConst.Professions professions,
                           int startTime, int endTime) {
        Game game = App.getCurrentGame();
        GameMap map = game.getGameMap();

        // make shopkeeper
        int y = shopPosition.getY();
        int x = shopPosition.getX();
        NPC newNPC = new NPC(NPCName, gender, professions,
                new ArrayList<>(), new ArrayList<>(),
                new Position(y, x - 1), 0);


        // make shop
        Shop newShop = new Shop(shopName, map.getTile(y, x), newNPC, startTime, endTime, new Position(y, x));

        game.addNPC(newNPC);
        game.addShop(newShop);
    }
}
