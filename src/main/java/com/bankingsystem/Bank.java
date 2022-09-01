package com.bankingsystem;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Bank {

    Menu menu = new Menu();
    Database database = new Database();
    Scanner scannerObj = new Scanner(System.in);
    List<String> transactionOptions = Arrays.asList("1", "2", "3");

    public void login() throws InterruptedException {

        int customerID = getCustomerID();

        if (database.customerIDExists(customerID)) {

            TimeUnit.SECONDS.sleep(1);  // add second delay

            // get the customer to input their password
            boolean loginSuccessful = CustomerCanLogin(database.getOneCustomerDetail("password", customerID));

            if (loginSuccessful) {
                TimeUnit.SECONDS.sleep(1);  // add second delay

                // display user's take user to main menu
                displayCustomersName(customerID);
                getUserInput(customerID);
            }
            else {
                System.out.println("YOU HAVE ENTERED YOUR PASSWORD INCORRECTLY THREE TIMES. PLEASE WAIT 30 MINUTES" +
                        " BEFORE TRYING AGAIN.");
                System.exit(0);
            }
        } else {
            System.out.printf("\nSORRY, %d IS NOT A VALID ID. PLEASE TRY AGAIN.\n", customerID);
            menu.mainMenu();

        }
    }
    private void getUserInput(int customerID) throws InterruptedException {
        menu.displaySecondMenuOptions();

        System.out.print("\nOPTION: ");

        String userInput = scannerObj.next();

        switch (userInput) {

            case "1" -> displayBankDetails(customerID);  // show balance, account number etc.

            case "2" -> withdrawalRequest(customerID);  // allow user to withdraw funds

            case "3" -> depositRequest(customerID);  // allow user to deposit funds

            case "4" -> displayTransactions(customerID);  // display historic deposits and/or withdrawals

            case "5" -> displayPersonalDetails(customerID);  // show personal details of user e.g., age, contact details

            case "6" -> System.exit(0);  // exit program
        }

    }

    private void displayPersonalDetails(int customerID) throws InterruptedException {

        Map<String, Object> personalDetails = database.getAllCustomerDetails(customerID);
        int income = (int) personalDetails.get("annual_income");
        float newIncome = (float) income;

        // output personal details to user
        System.out.println("\n-----------------------------------------------");
        System.out.println("           YOUR PERSONAL DETAILS:             ");
        System.out.println("-----------------------------------------------");
        System.out.printf(" FULL NAME         : %s. %s %s\n", personalDetails.get("title"), personalDetails.get("forename"), personalDetails.get("surname"));
        System.out.printf(" MOBILE            : +44%s\n", personalDetails.get("mobile"));
        System.out.printf(" DOB               : %s\n", personalDetails.get("dob"));
        System.out.printf(" GENDER            : %s\n", personalDetails.get("gender"));
        System.out.printf(" EMPLOYMENT STATUS : %s\n", personalDetails.get("employment_status"));
        System.out.printf(" ANNUAL INCOME     : £%,.2f", newIncome);
        System.out.println("\n-----------------------------------------------\n");

        TimeUnit.SECONDS.sleep(2);
        getUserInput(customerID);
    }

    private void displayCustomersName(int customerID) {

        String shortName = database.getShortCustomerIntro(customerID);

        System.out.println("\n------------------------------------------------");
        System.out.println("                 LOGIN SUCCESSFUL             ");
        System.out.printf("             WELCOME BACK, %s            ", shortName.toUpperCase());
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println();

    }

    private void displayBankDetails(int customerID) throws InterruptedException {

        int accountNumber = database.getAccountNumber(customerID);
        double accountBalance = database.getBalance(customerID);

        System.out.println("\n-----------------------------------------------");
        System.out.println("             YOUR ACCOUNT DETAILS:                ");
        System.out.println("-----------------------------------------------");
        System.out.printf(" BALANCE        : £%,.2f\n", accountBalance);
        System.out.printf(" ACCOUNT NUMBER : %d", accountNumber);
        System.out.println("\n-----------------------------------------------\n");

        // create delay and take user back to menu
        TimeUnit.SECONDS.sleep(2);
        getUserInput(customerID);
    }

    private boolean CustomerCanLogin(String realPassword) {
        int passwordAttempts = 0;

        while (passwordAttempts < 3) {

            String enteredPassword = getPassword();

            if (isCorrectPassword(enteredPassword, realPassword)) {
                // login successful go to main menu
                return true;
            } else {
                // incorrect password, add one to attempt counter
                passwordAttempts += 1;
                System.out.printf("\nINCORRECT PASSWORD. PLEASE TRY AGAIN (ATTEMPTS: %d/3)\n\n", passwordAttempts);
            }
        }
        return false;
    }

    public boolean isCorrectPassword(String enteredPassword, String realPassword) {
        return Objects.equals(enteredPassword, realPassword);
    }

    private String getPassword() {
        System.out.print("Password: ");
        return scannerObj.next();
    }
    private int getCustomerID() {
        System.out.print("Customer ID: ");
        return scannerObj.nextInt();
    }

    private boolean createTransaction(int customerID, float amount, String comment) {
        // return true boolean if money has been successfully deposited or withdrawn from customer's account, else false
        return database.createTransaction(customerID, amount, comment);
    }

    private void depositRequest(int customerID) throws InterruptedException {
        String depositComment = "";

        while (true) {

            try {

                System.out.print("\nDEPOSIT REQUEST: £");
                float depositAmount = scannerObj.nextFloat();

                if (depositAmount > 1000) {
                    System.out.println("SORRY, FOR SECURITY REASONS YOU CANNOT ENTER MORE THAN £1,000.00 AT ONCE. " +
                            "PLEASE ENTER A VALUE BELOW THIS FIGURE.\n");
                }
                else if (depositAmount <= 0) {
                    System.out.println("YOU CANNOT DEPOSIT AN AMOUNT EQUAL TO OR LESS THAN £0.00.\n");
                }
                else {

                    // allow customer the option to add a comment to their withdrawal request
                    System.out.print("ADD A COMMENT TO YOUR DEPOSIT REQUEST? (YES/NO): ");
                    String usersRequest = scannerObj.next();

                    while (true) {

                        if ((usersRequest.equalsIgnoreCase("YES"))) {
                            System.out.print("YOUR COMMENT (MAX 50 CHARACTERS): ");
                            depositComment = scannerObj.next();
                            break;
                        }
                        else if (usersRequest.equalsIgnoreCase("NO")) {
                            break;
                        }
                        else {
                            System.out.println("INCORRECT INPUT. PLEASE ENTER YES OR NO ONLY.\n");

                            // get input from user again
                            System.out.print("ADD A COMMENT TO YOUR DEPOSIT REQUEST? (YES/NO): ");
                            usersRequest = scannerObj.next();
                        }
                    }

                    System.out.print("PLEASE CONFIRM YOUR DEPOSIT REQUEST BY ENTERING YES: ");
                    String confirmDeposit = scannerObj.next();

                    if (confirmDeposit.equalsIgnoreCase("YES")) {

                        boolean successfulDeposit = createTransaction(customerID, depositAmount, depositComment);

                        if (successfulDeposit) {
                            System.out.println("\nPROCESSING YOUR DEPOSIT...\n");
                            TimeUnit.SECONDS.sleep(2);  // add 2-second delay
                            System.out.println("THANK YOU, YOUR DEPOSIT WAS ACCEPTED.");
                            float balance = database.getBalance(customerID);
                            System.out.printf("\nYOUR BALANCE IS NOW: £%,.2f", balance);
                            System.out.println("\nNOW TAKING YOUR BACK TO THE MAIN MENU.\n");
                        }
                        else {
                            System.out.println("SORRY, YOUR DEPOSIT REQUEST WAS NOT SUCCESSFUL. PLEASE TRY AGAIN.\n");
                        }
                        break;
                    }
                    else {
                        System.out.println("YOU DID NOT ENTER YES TO CONFIRM YOUR DEPOSIT. PLEASE TRY AGAIN.\n");
                    }
                }
            }
            catch (InputMismatchException e) {
                System.out.println("YOU HAVE ENTERED NON-DIGIT CHARACTERS WHICH ARE NOT ACCEPTED. PLEASE TRY AGAIN\n");
            }
        }
        getUserInput(customerID);
    }

    private void withdrawalRequest(int customerID) throws InterruptedException {
        String withdrawalComment = "";

        while (true) {

            try {
                System.out.print("WITHDRAWAL REQUEST: £");
                float withdrawalAmount = scannerObj.nextFloat();
                TimeUnit.SECONDS.sleep(1);  // add second delay
                float balance = database.getBalance(customerID);
                if (withdrawalAmount > balance) {
                    System.out.printf("\nYOUR BALANCE IS NOT SUFFICIENT TO WITHDRAW £%,.2f. PLEASE DEPOSIT MONEY OR" +
                            " WITHDRAW A SMALLER AMOUNT", withdrawalAmount);
                    break;
                }

                // allow customer the option to add a comment to their withdrawal request
                System.out.print("ADD COMMENT TO YOUR WITHDRAWAL? (YES/NO): ");
                String usersRequest = scannerObj.next();

                while (true) {
                    if ((usersRequest.equalsIgnoreCase("YES"))) {
                        System.out.print("YOUR COMMENT (MAX 50 CHARACTERS): ");
                        withdrawalComment = scannerObj.next();
                        break;
                    }
                    else if (usersRequest.equalsIgnoreCase("NO")) {
                        break;
                    }
                    else {
                        System.out.println("INCORRECT INPUT, PLEASE ENTER YES OR NO ONLY.\n");
                        // get input from user again
                        System.out.print("ADD A COMMENT TO YOUR DEPOSIT REQUEST? (YES/NO): ");
                        usersRequest = scannerObj.next();
                    }
                }

                System.out.printf("\nPLEASE CONFIRM YOUR WITHDRAWAL REQUEST OF £%,.2f BY ENTERING YES: ", Math.abs(withdrawalAmount));

                // if customer confirms withdrawal request
                if (scannerObj.next().equalsIgnoreCase("YES")) {
                    boolean successfulWithdrawal = createTransaction(customerID, -withdrawalAmount, withdrawalComment);
                    if (successfulWithdrawal) {
                        System.out.println("\nPROCESSING YOUR WITHDRAWAL...\n");
                        TimeUnit.SECONDS.sleep(2);  // add 2-second delay
                        balance -= withdrawalAmount;
                        System.out.println("THANK YOU, YOUR WITHDRAWAL REQUEST WAS ACCEPTED.");
                        System.out.printf("\nYOUR BALANCE IS NOW: £%,.2f\n", balance);
                        System.out.println("NOW TAKING YOU BACK TO THE MAIN MENU\n");
                    }
                    else {
                        System.out.println("SORRY, YOUR WITHDRAWAL REQUEST WAS NOT SUCCESSFUL. PLEASE TRY AGAIN.");
                    }
                    break;
                }
                else {
                    System.out.println("YOU DID NOT ENTER YES TO CONFIRM YOUR WITHDRAWAL REQUEST. PLEASE TRY AGAIN.");
                }
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("YOU HAVE ENTERED NON-DIGIT CHARACTERS WHICH ARE NOT ACCEPTED. PLEASE TRY AGAIN\n");
            }
        }
        getUserInput(customerID);
    }

    private void displayTransactions(int customerID) throws InterruptedException {

        System.out.println("\nPLEASE CHOOSE FROM ONE OF THE FOLLOWING OPTIONS: ");
        System.out.println("-------------------------------------------------");
        System.out.println("1) DEPOSITS");
        System.out.println("2) WITHDRAWALS");
        System.out.println("3) DEPOSITS & WITHDRAWALS\n");

        String query = null;

        while (true) {
            System.out.print("YOUR INPUT: ");
            String userRequest = scannerObj.next();
            if (transactionOptions.contains(userRequest)) {
                int accountID = database.getAccountNumber(customerID);

                switch (userRequest) {
                    case "1" ->
                            query = String.format("SELECT transaction_date, amount, comment FROM transaction WHERE account_id = %d AND amount > 0", accountID);
                    case "2" ->
                        query = String.format("SELECT transaction_date, amount, comment FROM transaction WHERE account_id = %d AND amount < 0", accountID);
                    case "3" ->
                        query = String.format("SELECT transaction_date, amount, comment FROM transaction WHERE account_id = %d", accountID);
                }
                break;
            }
            else {
                System.out.println("INCORRECT INPUT. PLEASE TRY AGAIN.\n");
            }
        }
        ArrayList<List<String>> transactions = database.getTransactions(query);
        displayTransactions(transactions);
        System.out.println();
        getUserInput(customerID);
    }

    private void displayTransactions(ArrayList<List<String>> transactions) {

        System.out.println("\nDATE                       AMOUNT           COMMENT");
        System.out.println("---------------------------------------------------");

        for (List<String> transaction : transactions) {
            if (transaction.get(1).length() == 9) {
                System.out.printf("%s        %s        %s\n", transaction.get(0),
                        transaction.get(1), transaction.get(2));
            }
            else {
                System.out.printf("%s        %s      %s\n", transaction.get(0),
                        transaction.get(1), transaction.get(2));
            }
        }
    }

}
