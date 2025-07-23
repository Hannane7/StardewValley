package com.untildawn.views.PreGameMenus;

public class TerminalAnimation {
    public static void loadingAnimation(String text) throws InterruptedException {
        System.out.print(text);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(500);
            System.out.print(".");
        }

        Thread.sleep(1000);

        int totalLength = text.length() + 3;
        System.out.print("\r" + " ".repeat(totalLength));
        System.out.print("\r");
    }
}
