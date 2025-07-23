package com.untildawn.Enums.ItemConsts;


public class ItemLevels {

    public enum ToolLevels implements Level {
        BASIC(0),
        COOPER(1),
        IRON(2),
        GOLDEN(3),
        IRIDIUM(4),
        ;
        private final int level;

        ToolLevels(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    public enum BackPackLevels implements Level {
        BASIC(0),
        BIG(1),
        DELUXE(2),
        ;
        private final int level;

        BackPackLevels(int level) {
            this.level = level;
        }

        public int getLevel() {
            return (level + 1) * 12;
        }
    }

    public enum FishingPoleLevels implements Level {
        TRAINING(0),
        BAMBOO(1),
        FIBER_GLASS(2),
        IRIDIUM(3),
        ;
        private final int level;

        FishingPoleLevels(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
