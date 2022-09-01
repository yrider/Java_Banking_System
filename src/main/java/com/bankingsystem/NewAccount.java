package com.bankingsystem;

import com.mysql.cj.conf.PropertyDefinitions;

import java.time.LocalDate;

public class NewAccount {

    Validation validation = new Validation();
    Menu menu = new Menu();
    Database database = new Database();

    public void openNewAccount() throws InterruptedException {

        displayNewAccMessage();

        // Save user's input to sql database
        String title = getTitle();
        String forename = getForename();
        String surname = getSurname();
        String gender = getGender();
        String mobile = getMobile();

        LocalDate DOB = getDOB();
        String employmentStatus = getEmploymentStatus();
        float annualIncome = getAnnualIncome();

        String password = getPassword();

        database.createCustomer(title, forename, surname, gender, mobile, DOB, employmentStatus, annualIncome, password);
        database.createAccount();

        displayAccCreatedMessage(database.getLastCustomerID());

        menu.mainMenu();
    }
    private String getTitle() {
        // return the user's title
        return validation.validateTitle();
    }
    private String getForename() {
        // Return the user's firstname
        return validation.validateFirstOrLastName("Forename: ");
    }
    private String getSurname() {
        // return the user's surname
        return validation.validateFirstOrLastName("Surname: ");
    }
    private String getPassword() {
        // return the user's password
        displayPasswordMessage();
        return validation.validatePassword();
    }
    private String getGender() {
        // return the user's gender
        return validation.validateGender();
    }
    private String getMobile() {
        // return the user's mobile
        return validation.validateMobile();
    }
    private LocalDate getDOB() {
        // return the user's date of birth
        return validation.validateDOB();
    }
    private String getEmploymentStatus() {
        // return the user's employment status
        return validation.validateEmploymentStatus();
    }
    private float getAnnualIncome() {
        // return the user's annual income
        return validation.validateAnnualIncome();
    }

    private void displayNewAccMessage() {
        // output message to console on creating a new account
        System.out.println("\n------------------------------------------------");
        System.out.println("              CREATE A NEW ACCOUNT              ");
        System.out.println("------------------------------------------------\n");
        System.out.println("     PLEASE ENTER THE FOLLOWING INFORMATION:");
        System.out.println("------------------------------------------------\n");
    }

    private void displayAccCreatedMessage(int customerID) {
        // output message to user explaining the account has been successfully created
        System.out.println("\n-----------------------------------------------");
        System.out.println("| ACCOUNT SUCCESSFULLY CREATED:               |");
        System.out.println("| - - - - - - - - - - - - - - - - - - - - - - |");
        System.out.println("| THANK YOU FOR CREATING AN ACCOUNT WITH US.  |");
        System.out.println("| PLEASE LOGIN WITH THE FOLLOWING CUSTOMER ID |");
        System.out.printf("| YOUR CUSTOMER ID: %d                  |\n", customerID);
        System.out.println("|                                             |");
        System.out.println("| YOU'LL NOW BE TAKEN BACK TO THE MAIN-MENU   |");
        System.out.println("-----------------------------------------------");
    }

    private void displayPasswordMessage() {
        // output message to console on password requirements
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("| CREATING A PASSWORD           |");
        System.out.println("| - - - - - - - - - - - - - - - |");
        System.out.println("| Your password must contain:   |");
        System.out.println("|   > One special character     |");
        System.out.println("|   > One capital letter        |");
        System.out.println("|   > One lower case letter     |");
        System.out.println("|   > One number                |");
        System.out.println("|   > At least 8 characters     |");
        System.out.println("---------------------------------");
    }
}

