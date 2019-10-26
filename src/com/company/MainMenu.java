package com.company;

import java.util.Scanner;

public class MainMenu {

    public enum MenuItems{
        ADMIN_LOGIN_REG("ADMIN REGISTRATION"),
        ADD_BOOKS("ADD BOOKS"),
        SHOW_BOOKS("SHOW BOOKS"),
        SORT_BOOKS("SORT BOOKS"),
        SUBSCRIBER_REGISTRATION("SUBSCRIBER'S REGISTRATION"),
        BORROW_BOOK("BORROW BOOK"),
        MY_BORROWED_BOOKS("MY BORROWED BOOKS"),
        RETURN_BOOK("RETURN BOOK"),
        ALL_BORROWED_BOOKS("ALL BORROWED BOOKS"),
        EXIT("EXIT PROGRAM");

        private String description;
        MenuItems(String description){
            this.description = description;
        }

    }


    public static MenuItems showMenuAndGetChoice() {
        MenuItems menuItem = null;
        System.out.println("====================================\n" +
                "PLEASE SELECT ONE ITEM FROM THE MENU\n" +
                "====================================");
        int i = 1;
        for (MenuItems item : MenuItems.values()) {
            System.out.println(i + " " + item.description);
            i++;
        }

        boolean menuNum;
        do {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int choiceIndex = scan.nextInt();
                if (choiceIndex > 0 && choiceIndex <= MenuItems.values().length){
                    menuItem = MenuItems.values()[choiceIndex - 1];
                    break;
                }else {
                    menuNum = false;
                    System.out.println("ENTER A MENU ITEM NUMBER: 1 - 10");
                }
            } else {
                menuNum = false;
                System.out.println("ENTER A MENU ITEM NUMBER: 1 - 10");
            }
        }while (!menuNum);

        return menuItem;
     }


}
