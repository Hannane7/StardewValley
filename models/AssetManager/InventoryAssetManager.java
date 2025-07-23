package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class InventoryAssetManager implements GameAssetManager{
    private final static Texture slot = new Texture(Gdx.files.internal("Images/Inventory/slot.png"));
    private final static Texture backGround = new Texture(Gdx.files.internal("Images/Inventory/backGround.png"));
    private static final Texture miningSkill = new Texture(Gdx.files.internal("Images/Skill/Mining_Skill_Icon.png"));
    private static final Texture farmingSkill = new Texture(Gdx.files.internal("Images/Skill/Farming_Skill_Icon.png"));
    private static final Texture foragingSkill = new Texture(Gdx.files.internal("Images/Skill/Foraging_Skill_Icon.png"));
    private static final Texture fishingSkill = new Texture(Gdx.files.internal("Images/Skill/Fishing_Skill_Icon.png"));
    private static final Texture trashCan = new Texture(Gdx.files.internal("Images/Tools/base_trash_can.png"));
    private static final Texture settingIcon = new Texture(Gdx.files.internal("Images/Inventory/setting_icon.png"));
    public static Texture getSlot() {
        return slot;
    }
    public static Texture getTrashCan() {
        return trashCan;
    }

    public static Texture getFarmingSkill() {
        return farmingSkill;
    }

    public static Texture getFishingSkill() {
        return fishingSkill;
    }

    public static Texture getForagingSkill() {
        return foragingSkill;
    }

    public static Texture getMiningSkill() {
        return miningSkill;
    }
    public static Texture getBackGround() {
        return backGround;
    }

    public static Texture getSettingIcon() {
        return settingIcon;
    }
}
