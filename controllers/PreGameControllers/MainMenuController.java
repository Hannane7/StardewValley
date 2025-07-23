package com.untildawn.controllers.PreGameControllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Main;
import com.untildawn.models.App;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.PreGameMenus.MainMenuView;
import com.untildawn.views.PreGameMenus.ProfileMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setListeners() {

        view.getGameMenuButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(Menus.PreGameMenus.GAME_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.GAME_MENU);

            }
        });

        view.getProfileMenuButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(Menus.PreGameMenus.PROFILE_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.PROFILE_MENU);
            }
        });

        view.getAvatarMenuButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(Menus.PreGameMenus.AVATAR_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.AVATAR_MENU);
            }
        });

        view.getLogoutButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userLogout();
            }
        });

        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuController.this.exit();
            }
        });

    }

    public void userLogout() {
        // SessionManager.clearSession();
        App.setCurrentUser(null);
        Main.getMain().setScreen(Menus.PreGameMenus.LOGIN_MENU.getMenu());
        App.setCurrentMenu(Menus.PreGameMenus.LOGIN_MENU);
        System.out.println("User logged out. Switching to Login Screen...");
    }

    public void exit() {
        UserDataHandler.saveUsers();
        Gdx.app.exit();
    }
//    public void userLogout() {
//        SessionManager.clearSession();
//        App.setCurrentUser(null);
//        App.setCurrentMenu(Menus.PreGameMenus.LOGIN_MENU);
//        System.out.println("User logged out successfully");
//    }

//    public void exit() {
//        UserDataHandler.saveUsers();
//        try {
//            TerminalAnimation.loadingAnimation("Exiting the program");
//        } catch (InterruptedException e) {
//
//        }
//        System.exit(0);
//    }


}
