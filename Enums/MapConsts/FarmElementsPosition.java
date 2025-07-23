package com.untildawn.Enums.MapConsts;

public class FarmElementsPosition {
    public enum TopLeftFarm {
        COTTAGE(5, 25),
        LAKE_1(20, 20),
        LAKE_2(25, 15),
        QUARRY(0, 15),
        GREENHOUSE(5, 10),
        ;

        private final int y;
        private final int x;
        TopLeftFarm(int y, int x) {
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
    public enum TopRightFarm {
        COTTAGE(5, 85),
        LAKE(20, 80),
        QUARRY_1(0, 75),
        QUARRY_2(29, 65),
        GREENHOUSE(5, 70),
        ;
        private final int y;
        private final int x;
        TopRightFarm(int y, int x) {
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
    public enum BottomLeftFarm {
        COTTAGE(65, 25),
        LAKE(80, 15),
        QUARRY_1(60, 15),
        QUARRY_2(89, 5),
        GREENHOUSE(65, 10),
        ;
        private final int y;
        private final int x;
        BottomLeftFarm(int y, int x) {
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
    public enum BottomRightFarm {
        COTTAGE(65, 85),
        LAKE_1(80, 80),
        LAKE_2(85, 75),
        QUARRY(60, 75),
        GREENHOUSE(65, 70),
        ;
        private final int y;
        private final int x;
        BottomRightFarm(int y, int x) {
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
}
