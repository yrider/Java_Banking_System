package com.bankingsystem;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {

    public void mainMenu() throws InterruptedException {

        // instantiate objects
        Scanner scannerObj = new Scanner(System.in);
        NewAccount newAccount = new NewAccount();
        Bank bank = new Bank();

        // Output messages to user
        displayWelcomeMessage();
        displayMenuOptions();

        System.out.print("\nOPTION: ");

        String userInput = scannerObj.next();

        switch (userInput) {
            case "1" -> bank.login();
            case "2" -> newAccount.openNewAccount();
            case "3" -> System.exit(0);
        }
    }

    public void displayWelcomeMessage() throws InterruptedException {

        System.out.println("\n------------------------------------------------");
        System.out.println("                     WELCOME              ");
        System.out.println("------------------------------------------------");
        System.out.println();
        TimeUnit.SECONDS.sleep(2);
    }

    public void displayMenuOptions() {

        System.out.println("PLEASE CHOOSE FROM ONE OF THE FOLLOWING OPTIONS: ");
        System.out.println("------------------------------------------------");
        System.out.println("1) LOGIN");
        System.out.println("2) OPEN A NEW ACCOUNT");
        System.out.println("3) QUIT");
    }

    public void displaySecondMenuOptions() {

        System.out.println("PLEASE CHOOSE FROM ONE OF THE FOLLOWING OPTIONS: ");
        System.out.println("------------------------------------------------");
        System.out.println("1) ACCOUNT BALANCE");
        System.out.println("2) WITHDRAW FUNDS");
        System.out.println("3) DEPOSIT FUNDS");
        System.out.println("4) PREVIOUS TRANSACTIONS");
        System.out.println("5) PERSONAL DETAILS");
        System.out.println("6) LOGOUT & QUIT");
    }
}
