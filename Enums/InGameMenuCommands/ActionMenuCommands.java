package com.untildawn.Enums.InGameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ActionMenuCommands {
    SWITCH_MENU("^\\s*switch\\s+menu\\s*$"),
    NEXT_TURN("^\\s*next\\s+turn\\s*$"),


    TIME("^\\s*time\\s*$"),
    DATE("^\\s*date\\s*$"),
    DATE_TIME("^\\s*datetime\\s*$"),
    DAY_OF_THE_WEEK("^\\s*day\\s+of\\s+the\\s+week\\s*$"),
    CHEAT_ADVANCE_TIME("^\\s*cheat\\s+advance\\s+time\\s+(?<hours>.+?)\\s*h\\s*$"),
    CHEAT_ADVANCE_DATE("^\\s*cheat\\s+advance\\s+date\\s+(?<day>.+?)\\s*d\\s*$"),


    SEASON("^\\s*season\\s*$"),
    CHEAT_THOR("^\\s*cheat\\s+Thor\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s*$"),
    WEATHER("^\\s*weather\\s*$"),
    WEATHER_FORECAST("^\\s*weather\\s+forecast\\s*$"),
    CHEAT_WEATHER_SET("^\\s*cheat\\s+weather\\s+set\\s+(?<type>.+?)\\s*$"),


    GREENHOUSE_BUILD("^\\s*greenhouse\\s+build\\s*$"),


    WALK("^\\s*walk\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s*$"),


    PRINT_MAP("^\\s*print\\s+map\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s+-s\\s+(?<size>.+?)\\s*$"),
    HELP_READING_MAP("^\\s*help\\s+reading\\s+map\\s*$"),


    ENERGY_SHOW("^\\s*energy\\s+show\\s*$"),
    ENERGY_SET("^\\s*energy\\s+set\\s+-v\\s+(?<value>.+?)\\s*$"),
    ENERGY_REFILL("^\\s*energy\\s+refill\\s*$"),
    ENERGY_UNLIMITED("^\\s*energy\\s+unlimited\\s*$"),


    TOOLS_EQUIP("^\\s*tools\\s+equip\\s+(?<toolName>.+?)\\s*$"),
    TOOLS_SHOW_CURRENT("^\\s*tools\\s+show\\s+current\\s*$"),
    TOOLS_SHOW_AVAILABLE("^\\s*tools\\s+show\\s+available\\s*$"),
    TOOLS_UPGRADE("^\\s*tools\\s+upgrade\\s+(?<toolName>.+?)\\s*$"),
    TOOLS_USE("^\\s*tools\\s+use\\s+-d\\s+(?<direction>.+?)\\s*$"),


    CRAFT_INFO("^\\s*craft\\s+info\\s+-n\\s+(?<craftName>.+?)\\s*$"),
    PLANT("^\\s*plant\\s+-s\\s+(?<seed>.+?)\\s+-d\\s+(?<direction>.+?)\\s*$"),
    SHOW_PLANT("^\\s*showplant\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s*$"),
    FERTILIZE("^\\s*fertilize\\s+-f\\s+(?<fertilizer>.+?)\\s+-d\\s+(?<direction>.+?)\\s*$"),
    HOW_MUCH_WATER("^\\s*howmuch\\s+water\\s*$"),


    CRAFTING_SHOW_RECIPES("^\\s*crafting\\s+show\\s+recipes\\s*$"),
    CRAFTING_CRAFT("^\\s*crafting\\s+craft\\s+(?<itemName>.+?)\\s*$"),
    PLACE_ITEM("^\\s*place\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-d\\s+(?<direction>.+?)\\s*$"),
    CHEAT_ADD_ITEM("^\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-c\\s+(?<count>.+?)\\s*$"),


    COOKING_REFRIGERATOR("^\\s*cooking\\s+refrigerator\\s+(put/pick)\\s+(?<item>.+?)\\s*$"),
    COOKING_SHOW_RECIPES("^\\s*cooking\\s+show\\s+recipes\\s*$"),
    COOKING_PREPARE("^\\s*cooking\\s+prepare\\s+(?<recipeName>.+?)\\s*$"),
    EAT("^\\s*eat\\s+(?<foodName>.+?)\\s*$"),


    BUILD("^\\s*build\\s+-a\\s+(?<buildingName>.+?)\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s*$"),
    BUY_ANIMAL("^\\s*buy\\s+animal\\s+-a\\s+(?<animal>.+?)\\s+-n\\s+(?<name>.+?)\\s*$"),
    PET("^\\s*pet\\s+-n\\s+(?<name>.+?)\\s*$"),
    ANIMALS("^\\s*animals\\s*$"),
    SHEPHERD_ANIMALS("^\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>.+?)\\s+-l\\s+(?<y>.+?)\\s+(?<x>.+?)\\s*$"),
    FEED_HAY("^\\s*feed\\s+hay\\s+-n\\s+(?<animalName>.+?)\\s*$"),
    CHEAT_SET_FRIENDSHIP("^\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>.+?)\\s+-c\\s+(?<amount>.+?)\\s*$"),
    PRODUCES("^\\s*produces\\s*$"),
    COLLECT_PRODUCE("^\\s*collect\\s+produce\\s+-n\\s+(?<name>.+?)\\s*$"),
    SELL_ANIMAL("^\\s*sell\\s+animal\\s+-n\\s+(?<name>.+?)\\s*$"),
    FISHING("^\\s*fishing\\s+-p\\s+(?<fishingPole>.+?)\\s*$"),


//    TRADE("^\\s*trade\\s+-u\\s+(?<username>.+?)\\s+-t\\s+(?<type>.+?)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+" +
//            "(?<amount>.+?)\\s+(-p\\s+(?<price>.+?)\\s*)|(-ti\\s+(?<targetItem>.+?)\\s+-ta\\s+(?<targetAmount>.+?)\\s*)$"),
    TRADE("^\\s*trade\\s+\\-u\\s+(?<username>.+?)\\s*$"),
    TRADE_LIST("^\\s*list\\s+trades\\s*$"),
    /// npc commands
    QUESTS_FINISH("^\\s*quests\\s+finish\\s+-i\\s+(?<index>.+?)\\s*$"),
    QUESTS_LIST("^\\s*quests\\s+list\\s*$"),
    FRIENDSHIP_NPC_LIST("^\\s*friendship\\s+NPC\\s+list\\s*$"),
    GIFT_NPC("^\\s*gift\\s+NPC\\s+(?<npcName>.+?)\\s+-i\\s+(?<item>.+?)\\s*$"),
    MEET_NPC("^\\s*meet\\s+NPC\\s+(?<npcName>.+?)\\s*$"),
    GET_QUEST("^\\s*get\\s+quest\\s+NPC\\s+(?<npcName>.+?)\\s*$"),
    ///
    TRADE_HISTORY("^\\s*trade\\s+history\\s*$"),
    TRADE_RESPONSE("^\\s*trade\\s+response\\s+-(accept|reject)\\s+-i\\s+(?<id>.+?)\\s*$"),
    /// TRADE TODO
    START_TRADE("^\\s*start\\s+trade\\s*$"),
    RESPOND("^\\s*respond\\s+-(accept|reject)\\s+-u\\s+(?<username>.+?)\\s*$"),
    ASK_MARRIAGE("^\\s*ask\\s+marriage\\s+-u\\s+(?<username>.+?)\\s*$"),
    FLOWER("^\\s*flower\\s+-u\\s+(?<username>.+?)\\s*$"),
    HUG("^\\s*hug\\s+-u\\s+(?<username>.+?)\\s*"),
    GIFT_HISTORY("^\\s*gift\\s+history\\s+-u\\s+(?<username>.+?)\\s*$"),
    GIFT_RATE("^\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>.+?)\\s+-r\\s+(?<rate>.+?)\\s*$"),
    GIFT_LIST("^\\s*gift\\s+list\\s*$"),
    GIFT("^\\s*gift\\s+-u\\s+(?<username>.+?)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>.+?)\\s*$"),
    TALK_HISTORY("^\\s*talk\\s+history\\s+-u\\s+(?<username>.+?)\\s*$"),
    TALK("^\\s*talk\\s+-u\\s+(?<username>.+?)\\s+-m\\s+(?<message>.+?)\\s*$"),
    FRIENDSHIPS("^\\s*friendships\\s*$"),
    /// selling and buying
    SELL("^\\s*sell\\s+(?<productName>.+?)\\s+-n\\s+(?<count>.+?)\\s*$"),
    CHEAT_ADD_DOLLARS("^\\s*cheat\\s+add\\s+(?<count>.+?)\\s+dollars\\s*$"),
    PURCHASE("^\\s*purchase\\s+(?<productName>.+?)\\s+-n\\s+(?<count>.+?)\\s*$"),
    SHOW_ALL_AVAILABLE_PRODUCTS("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
    SHOW_ALL_PRODUCTS("^\\s*show\\s+all\\s+products\\s*$"),
    /// faravari
    ARTISAN_GET("^\\s*artisan\\s+get\\s+(?<artisanName>.+?)\\s*$"),
    ARTISAN_USE("^\\s*artisan\\s+use\\s+-r\\s+(?<artisanName>.+?)\\s+-n\\s+(?<item1Name>.+?)" +
            "(\\s+-i\\s+(?<ingredient>.+?))?\\s*$"),
    CHEAT_INFINITY_INVENTORY("^\\s*cheat\\s+infinity\\s+inventory\\s*$"),
    SHOW_MONEY("^\\s*show\\s+money\\s*$");
    private final String pattern;

    ActionMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

