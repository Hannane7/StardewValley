package com.untildawn.views;

import java.util.Scanner;
import java.util.function.Consumer;


/*
    Interface for menus.
 */
public interface AppMenu {
    void showMessage(String message);
    void showError(String error);
    void showMessageAndExecute(String message, Runnable onClose);
    void showConfirmation(String message, final Consumer<Boolean> resultCallback);
    public void handleInput(Scanner sc);
}
