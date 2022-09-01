package com.bankingsystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
public class Database {

    final String url = "jdbc:mysql://localhost:3306/banking-system";
    final String username = "";
    final String password = "";

    Connection connection;
    Statement statement;

    // constructor method for connecting to database when instance of database is created
    public Database() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch (SQLException e) {
            System.out.println("SQL Error: cannot connect to database. Please close the program and try again.");
        }
    }

    public void createCustomer(String title, String forename, String surname, String gender, String mobile,
                                 LocalDate DOB, String employmentStatus, Float annualIncome, String password) {

        try {
            String query = "INSERT INTO customer " +
                    "(title, forename, surname, password, gender, mobile, dob, employment_status, annual_income)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create mysql insert statement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, title);
            preparedStmt.setString(2, forename);
            preparedStmt.setString(3, surname);
            preparedStmt.setString(4, password);
            preparedStmt.setString(5, gender);
            preparedStmt.setString(6, mobile);
            preparedStmt.setString(7, String.valueOf(DOB));
            preparedStmt.setString(8, employmentStatus);
            preparedStmt.setFloat(9, annualIncome);

            preparedStmt.execute();
        }
        catch (SQLException e) {
            System.out.println("SQL Exception error in Database.createCustomer");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
    }
    public void createAccount() {

        try {
            int customerID = getLastCustomerID();

            String query = "INSERT INTO account (balance, customer_id) VALUES (?, ?)";

            // create mysql insert statement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setFloat(1, 0);
            preparedStmt.setInt(2, customerID);
            preparedStmt.execute();
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.createAccount");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
    }

    public boolean customerIDExists(int customerID) {

        int value = 0;
        try {
            // check that a record does exist by selecting count of 1. if 0 then no customer exists with given id.
            String query = String.format("SELECT COUNT(1) FROM customer WHERE customer_id=%s", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();

            value = queryResult.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.customerIDExists");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        // if value == 1 customer exists else they do not.
        return value == 1;
    }

    public int getLastCustomerID() {
        int customerID = 0;
        try {
            // get the last row for the newly created customer id
            String getLastID = "SELECT customer_id FROM customer WHERE customer_id=(SELECT MAX(customer_id) FROM customer)";
            ResultSet queryResult = statement.executeQuery(getLastID);
            queryResult.first();
            customerID = queryResult.getInt("customer_id");
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getLastCustomerID");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return customerID;
    }

    public Map<String, Object> getAllCustomerDetails(int customerID) {
        Map<String, Object> customerDetails = new HashMap<>();
        List<String> customerInfo = Arrays.asList("title", "forename", "surname", "gender", "mobile", "dob",
                "employment_status", "annual_income");

        try {
            String query = String.format("SELECT * FROM customer WHERE customer_id=%d", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();

            for (String detail : customerInfo) {
                // add all customer details to map
                if (detail.equals("annual_income")) {
                    customerDetails.put(detail, queryResult.getInt(detail));
                }
                else {
                    customerDetails.put(detail, queryResult.getString(detail));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getAllCustomerDetails");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return customerDetails;
    }
    public String getOneCustomerDetail(String detail, int customerID) {

        String value = null;

        try {
            String query = String.format("SELECT %s FROM customer WHERE customer_id=%d", detail, customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            value = queryResult.getString(detail);
        } catch (SQLException e) {
            System.out.println("SQL Exception in Database.getOneCustomerDetail");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return value;
    }

    public String getFullCustomerIntro(int customerID) {

        String fullName = null;
        try {
            String query = String.format("SELECT title, forename, surname FROM customer WHERE customer_id=%d", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            fullName = queryResult.getString("title") + ". " +
                    queryResult.getString("forename") + " " + queryResult.getString("surname");

        } catch (SQLException e) {
            System.out.println("SQL Exception in Database.getFullCustomerIntro");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return fullName;
    }
    
    public String getShortCustomerIntro(int customerID) {

        String shortName = null;

        try {
            String query = String.format("SELECT title, surname FROM customer WHERE customer_id=%d", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            shortName = queryResult.getString("title") + ". " + queryResult.getString("surname");
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getShortCustomerIntro");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return shortName;
    }

    public float getAnnualIncome(int customerID) {

        int income = 0;

        try {
            String query = String.format("SELECT annual_income FROM customer WHERE customer_id = %d", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            income = queryResult.getInt("annual_income");
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getAnnualIncome");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return income;
    }

    public int getAccountNumber(int customerID) {

        int accountNumber = 0;

        try {
            String query = String.format("SELECT account_id FROM account WHERE customer_id=%d", customerID);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            accountNumber = queryResult.getInt("account_id");
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getAccountNumber");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return accountNumber;
    }


    public float getBalance(int accountNumber) {

        float balance = 0;

        try {
            String query = String.format("SELECT balance FROM account WHERE customer_id=%d", accountNumber);
            ResultSet queryResult = statement.executeQuery(query);
            queryResult.first();
            balance = queryResult.getFloat("balance");

        } catch (SQLException e) {
            System.out.println("SQL Exception in Database.getBalance");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return balance;
    }

    public ArrayList<List<String>> getTransactions(String query) {

        String timestamp;
        String comment;
        float amount;
        ArrayList<List<String>> transactions = new ArrayList<>();

        try {
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()) {
                timestamp = queryResult.getString("transaction_date");
                amount = queryResult.getFloat("amount");
                comment = queryResult.getString("comment");

                // format transaction amount and turn to string before appending to list
                String fmtAmount;
                if (amount > 0) {
                    fmtAmount = String.format("+ £%,.2f", amount);
                }
                else {
                    fmtAmount = String.format("- £%,.2f", Math.abs(amount));
                }

                // add transaction to map
                transactions.add(0, Arrays.asList(timestamp, fmtAmount, comment));
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception in Database.getTransactions");
            System.out.println("Please close the program and try again or investigate the SQL database");
            System.out.println("Please also ensure that the class attributes named: url, password and username are valid.");
        }
        return transactions;
    }

    public boolean createTransaction(int customerID, float amount, String comment) {

        try {

            String query = "INSERT INTO transaction (account_id, amount,  comment) VALUES (?, ?, ?)";
            int accountID = getAccountNumber(customerID);

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, accountID);
            preparedStmt.setFloat(2, amount);
            preparedStmt.setString(3, comment);
            preparedStmt.execute();

            // change balance in account table
            float balance = getBalance(customerID);
            balance += amount;

            query = String.format("UPDATE account SET BALANCE = %f WHERE customer_id = %d", balance, customerID);
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
}