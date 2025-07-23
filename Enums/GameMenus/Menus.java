package com.untildawn.Enums.GameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.controllers.InGameControllers.GameControllers.GameController;
import com.untildawn.controllers.PreGameControllers.*;
import com.untildawn.controllers.PreGameControllers.GameMenuController;
import com.untildawn.controllers.PreGameControllers.MainMenuController;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.views.AppMenu;
import com.untildawn.views.InGameMenus.*;
import com.untildawn.views.PreGameMenus.*;
import com.untildawn.views.PreGameMenus.ExitMenuView;
import java.util.Scanner;

/*
    This class has enums containing every menu in the game.
    later we use Menus.InGameMenus.HOME_MENU for example to change the current menu.
 */
public class Menus {
    public enum PreGameMenus implements Menu {
        SIGNUP_MENU(new SignupMenuView(new SignupMenuController())),
        LOGIN_MENU(new LoginMenuView(new LoginMenuController())),
        MAIN_MENU(new MainMenuView(new MainMenuController())),
        PROFILE_MENU(new ProfileMenuView(new ProfileMenuController())),
        AVATAR_MENU(new AvatarMenuView(new AvatarMenuController())),
        GAME_MENU(new GameMenuView(new GameMenuController())),
        EXIT_MENU(new ExitMenuView(new ExitMenuController()));

        private final Screen menu;

        PreGameMenus(Screen menu) {
            this.menu = menu;
        }

        public Screen getMenu() {
            return menu;
        }
    }

    public enum InGameMenus implements Menu {
        HOME_MENU(new HomeMenu()),
        CRAFTING_MENU(new CraftingMenu()),
        COOKING_MENU(new CookingMenu()),
        ACTION_MENU(new ActionMenuView()),
        SHOP_MENU(new ShopMenuView()),
        EXIT_MENU(new ExitMenu()),
        INVENTORY_MENU(new InventoryMenu()),
        MENU_SWITCHER(new MenuSwitcherView()),
        TRADE_MENU(new TradeMenuView()),
        GAME_VIEW(new GameView(new GameController()))
        ;
        private final Screen menu;

        InGameMenus(Screen menu) {
            this.menu = menu;
        }

        public Screen getMenu() {
            return menu;
        }
    }
}
