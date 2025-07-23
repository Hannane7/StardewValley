package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AvatarAssetManager {
    private static final Texture Abigail = new Texture(Gdx.files.internal("Images/Villagers/Abigail.png"));
    private static final Texture Alex = new Texture(Gdx.files.internal("Images/Villagers/Alex.png"));
    private static final Texture Birdie = new Texture(Gdx.files.internal("Images/Villagers/Birdie.png"));
    private static final Texture Bouncer = new Texture(Gdx.files.internal("Images/Villagers/Bouncer.png"));
    private static final Texture Caroline = new Texture(Gdx.files.internal("Images/Villagers/Caroline.png"));
    private static final Texture Clint = new Texture(Gdx.files.internal("Images/Villagers/Clint.png"));
    private static final Texture Demetrius = new Texture(Gdx.files.internal("Images/Villagers/Demetrius.png"));
    private static final Texture Dwarf = new Texture(Gdx.files.internal("Images/Villagers/Dwarf.png"));
    private static final Texture Elliott = new Texture(Gdx.files.internal("Images/Villagers/Elliott.png"));
    private static final Texture Emily = new Texture(Gdx.files.internal("Images/Villagers/Emily.png"));
    private static final Texture Evelyn = new Texture(Gdx.files.internal("Images/Villagers/Evelyn.png"));
    private static final Texture Fizz_00 = new Texture(Gdx.files.internal("Images/Villagers/Fizz_00.png"));
    private static final Texture George = new Texture(Gdx.files.internal("Images/Villagers/George.png"));
    private static final Texture Gil = new Texture(Gdx.files.internal("Images/Villagers/Gil.png"));
    private static final Texture Governor = new Texture(Gdx.files.internal("Images/Villagers/Governor.png"));
    private static final Texture Grandpa = new Texture(Gdx.files.internal("Images/Villagers/Grandpa.png"));
    private static final Texture Gunther = new Texture(Gdx.files.internal("Images/Villagers/Gunther.png"));
    private static final Texture Gus = new Texture(Gdx.files.internal("Images/Villagers/Gus.png"));
    private static final Texture Haley = new Texture(Gdx.files.internal("Images/Villagers/Haley.png"));
    private static final Texture Harvey = new Texture(Gdx.files.internal("Images/Villagers/Harvey.png"));
    private static final Texture Henchman_Portrait_1 = new Texture(Gdx.files.internal("Images/Villagers/Henchman_Portrait_1.png"));
    private static final Texture Jas = new Texture(Gdx.files.internal("Images/Villagers/Jas.png"));
    private static final Texture Jodi = new Texture(Gdx.files.internal("Images/Villagers/Jodi.png"));
    private static final Texture Kent = new Texture(Gdx.files.internal("Images/Villagers/Kent.png"));
    private static final Texture Krobus = new Texture(Gdx.files.internal("Images/Villagers/Krobus.png"));
    private static final Texture Leah = new Texture(Gdx.files.internal("Images/Villagers/Leah.png"));
    private static final Texture Leo = new Texture(Gdx.files.internal("Images/Villagers/Leo.png"));
    private static final Texture Lewis = new Texture(Gdx.files.internal("Images/Villagers/Lewis.png"));
    private static final Texture Linus = new Texture(Gdx.files.internal("Images/Villagers/Linus.png"));
    private static final Texture Marlon = new Texture(Gdx.files.internal("Images/Villagers/Marlon.png"));
    private static final Texture Marnie = new Texture(Gdx.files.internal("Images/Villagers/Marnie.png"));
    private static final Texture Maru = new Texture(Gdx.files.internal("Images/Villagers/Maru.png"));
    private static final Texture Morris = new Texture(Gdx.files.internal("Images/Villagers/Morris.png"));
    private static final Texture Mr_Qi = new Texture(Gdx.files.internal("Images/Villagers/Mr_Qi.png"));
    private static final Texture Pam = new Texture(Gdx.files.internal("Images/Villagers/Pam.png"));
    private static final Texture Penny = new Texture(Gdx.files.internal("Images/Villagers/Penny.png"));

    private static final Texture Pierre = new Texture(Gdx.files.internal("Images/Villagers/Pierre.png"));
    private static final Texture Professor_Snail = new Texture(Gdx.files.internal("Images/Villagers/Professor_Snail.png"));
    private static final Texture Robin = new Texture(Gdx.files.internal("Images/Villagers/Robin.png"));
    private static final Texture Sam = new Texture(Gdx.files.internal("Images/Villagers/Sam.png"));
    private static final Texture Sandy = new Texture(Gdx.files.internal("Images/Villagers/Sandy.png"));
    private static final Texture Sebastian = new Texture(Gdx.files.internal("Images/Villagers/Sebastian.png"));
    private static final Texture Shane = new Texture(Gdx.files.internal("Images/Villagers/Shane.png"));
    private static final Texture Vincent = new Texture(Gdx.files.internal("Images/Villagers/Vincent.png"));
    private static final Texture Willy = new Texture(Gdx.files.internal("Images/Villagers/Willy.png"));
    private static final Texture Wizard = new Texture(Gdx.files.internal("Images/Villagers/Wizard.png"));

    public static Texture[] getSkinTextures() {
        return new Texture[]{
            Abigail, Alex, Birdie, Bouncer, Caroline, Clint, Demetrius, Dwarf,
            Elliott, Emily, Evelyn, Fizz_00, George, Gil, Governor, Grandpa, Gunther,
            Gus, Haley, Harvey, Henchman_Portrait_1, Jas, Jodi, Kent, Krobus, Leah,
            Leo, Lewis, Linus, Marlon, Marnie, Maru, Morris, Mr_Qi, Pam, Penny, Pierre,
            Professor_Snail, Robin, Sam, Sandy, Sebastian, Shane, Vincent, Willy, Wizard
        };
    }

    public static Texture getAbigail() {
        return Abigail;
    }
}
