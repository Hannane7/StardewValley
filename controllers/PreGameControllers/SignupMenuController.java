package com.untildawn.controllers.PreGameControllers;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Main;
import com.untildawn.views.PreGameMenus.SignupMenuView;


public class SignupMenuController {

    private SignupMenuView view;

    public void setView(SignupMenuView view) {
        this.view = view;
    }

    public void handleButtons() {
        if (view != null) {
            view.getSignupButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String username = view.getUsernameField().getText();
                    String email = view.getEmailField().getText();
                    String password = view.getPasswordField().getText();

//                    signup(username, email, password);
                }
            });

            view.getLoginButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    view.dispose();
                    Main.getMain().setScreen(Menus.PreGameMenus.LOGIN_MENU.getMenu());

                }
            });
        }
    }


    public void exitMenu() {

    }


    public void signup(String username,
                       String password,
                       String nickname,
                       String email,
                       String gender) {

    }

}


