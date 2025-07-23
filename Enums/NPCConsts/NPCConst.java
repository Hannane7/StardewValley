package com.untildawn.Enums.NPCConsts;

public class NPCConst {
    public enum HomePositions {
        Sebastian(35, 40),
        Abigail(50, 45),
        Harvey(35, 50),
        Lia(55, 40),
        Robin(55, 50);

        private final int y, x;

        HomePositions(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public enum ShopPositions {
        BlackSmith(45, 35),
        JojaMart(45, 45),
        PierreGeneralStore(45, 55),
        CarpenterShop(50, 40),
        FishShop(50, 50),
        MarnieRanch(40, 40),
        StarDropSaloon(40, 50),
        ;

        private final int y, x;

        ShopPositions(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }

    public enum Professions {
        LAZY_BUM,
        BLACK_SMITH,
        FARMER,
        RANCHER,
        FISHERMAN,
        BARISTA,
        PET_SELLER,
        WOOD_SELLER,
        BEING_KIAN,
        ;
    }

}
