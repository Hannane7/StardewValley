package com.untildawn.controllers.PreGameControllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Main;
import com.untildawn.controllers.utils.PasswordGenerator;
import com.untildawn.controllers.utils.SessionManager;
import com.untildawn.models.App;
import com.untildawn.models.User;
import com.untildawn.views.PreGameMenus.LoginMenuView;


public class LoginMenuController {

    private LoginMenuView view;
    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleButtons() {
        if (view != null) {
            view.getLoginButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    String username = view.getUsernameField().getText();
//                    String password = view.getPasswordField().getText();
//
//                    login(username, password);
                    view.dispose();
                    Main.getMain().setScreen(Menus.PreGameMenus.MAIN_MENU.getMenu()); // temp
                }
            });

            view.getForgetPasswordButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String username = view.getUsernameField().getText();

                    forgetPassword(username);
                }
            });

            view.getSignupButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    view.dispose();
                    Main.getMain().setScreen(Menus.PreGameMenus.SIGNUP_MENU.getMenu());

                }
            });
        }
    }

    public boolean autoLogin() {
        String rememberedUser = SessionManager.loadSession();
        if (rememberedUser != null) {
            User user = App.getUser(rememberedUser);
            if (user != null) {
                App.setCurrentUser(user);
                this.view.showMessage("🔓 Auto-logged in as " + rememberedUser);
                App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
                this.view.showMessage("You are now in main menu.\n");
                return true;
            }
        }

        return false;
    }

    public void exitMenu() {

    }

    public void login(String username, String password) {

    }
    public void forgetPassword(String username) {
        if (username.isEmpty()) {
            this.view.showError("Please enter your username.");
            return;
        }

        if (!App.userExists(username)) {
            this.view.showError("User doesn't exist.");
            return;
        }

        User user = App.getUser(username);
        String email = user.getEmail();

//        EmailSender.sendEmail(email);
        this.view.showMessage("Your password has been\n   sent to your email.");
    }
}
