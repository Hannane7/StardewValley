package com.untildawn.Enums.ItemConsts;

public enum ItemDisplay {

    floor(" "),
    home(" "),
    cottage("C"),
    lake(" "),
    quarry(" "),
    greenhouse(" "),
    rock("R"),
    wood("W"),
    fiber("B"),
    tree("T"),
    foraging_seeds("S"),
    foraging_minerals("R"),
    foraging_crops("C"),
    shop(" "),

    barn_animal("b"),
    coop_animal("c"),
    all_crops("P"),
    building("t");
    private final String display;

    ItemDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public static String getDisplayByType(ItemType type) {
        for (ItemDisplay itemDisplay : ItemDisplay.values()) {
            if (itemDisplay.name().equalsIgnoreCase(type.name())) {
                return itemDisplay.getDisplay();
            }
        }
        return "?";
    }
}
