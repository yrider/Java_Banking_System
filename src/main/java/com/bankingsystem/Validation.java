package com.bankingsystem;

import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    // Initial vars
    List<String> possEmployment = Arrays.asList("full-time", "part-time", "student", "unemployed");
    List<String> possTitles = Arrays.asList("mr", "mrs", "ms", "miss", "dr", "sir", "lord");
    Pattern digits = Pattern.compile("[0-9]");
    Pattern specialChars = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
    Scanner scannerObj = new Scanner(System.in);

    public String validateTitle() {

        while (true) {
            System.out.print("Title (Mr, Mrs, Ms, Miss, Dr, Sir, Lord): ");
            String title = scannerObj.next();
            if (isCorrectTitle(title)) {
                return title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
            }
            System.out.println("Incorrect title provided. Please choose from one of the above options.\n");
        }
    }
    public boolean isCorrectTitle(@NotNull String title) {
        return possTitles.contains(title.toLowerCase());
    }
    public String validatePassword() {

        while (true) {

            System.out.print("Password: ");
            String password = scannerObj.next();

            if (isValidPassword(password)) {
                return password;
            }
            else {
                System.out.println("Incorrect password provided. Please ensure our password requirements are met.\n");
            }
        }
    }
    public boolean isValidPassword(String password) {

        Matcher hasDigit = digits.matcher(password);
        Matcher hasSpecialChar = specialChars.matcher(password);

        return password.length() >= 8 && hasDigit.find() && hasSpecialChar.find() &&
                containsUpperCaseCharacter(password) && containsLowerCaseCharacter(password);

    }

    public boolean containsUpperCaseCharacter(@NotNull String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean containsLowerCaseCharacter(@NotNull String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public String validateFirstOrLastName(String nameType) {

        while (true) {

            System.out.print(nameType);
            String name = scannerObj.next();

            if (isValidName(name)) {
                name = name.toLowerCase();
                return name.substring(0, 1).toUpperCase() + name.substring(1);
            }
            else {
                System.out.println("Incorrect name provided. Please ensure you do not enter any digits or special characters.\n");
            }
        }
    }
    public boolean isValidName(String name) {

        // check for digits or special characters
        Matcher hasDigit = digits.matcher(name);
        Matcher hasSpecial = specialChars.matcher(name);

        return !hasDigit.find() && !hasSpecial.find();
    }

    public String validateGender() {

        while (true) {
            System.out.print("Gender (male/female): ");
            String gender = scannerObj.next();

            if (isValidGender(gender)) {
                return gender;
            }
            else {
                System.out.println("Incorrect gender provided. Please choose from one of the above options.\n");
            }
        }
    }
    public boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female");
    }

    public String validateMobile() {

        while (true) {

            System.out.print("Mobile number (UK only): +44");
            String mobile = scannerObj.next();

            if (isValidMobile(mobile)) {
                return mobile;
            }
            else {
                System.out.println("Incorrect input provided. Please ensure you enter a UK mobile number and starts with +447.\n");
            }
        }
    }

    public boolean isValidMobile(String mobile) {
        return mobile.length() == 10 && mobile.startsWith("7");
    }

    public LocalDate validateDOB() {
        while (true) {
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String DOB = scannerObj.next();

            if (isValidDOB(DOB)) {
                return LocalDate.parse(DOB);
            } else {
                System.out.println("Incorrect input provided. Please try again\n");
            }
        }
    }
    public boolean isValidDOB(String DOB) {

        try {
            LocalDate.parse(DOB);
        }
        catch (DateTimeException exc) {
            return false;
        }
        return true;
    }

    public String validateEmploymentStatus() {

        while (true) {
            System.out.print("Employment Status (full-time, part-time, student or unemployed): ");
            String employmentStatus = scannerObj.next();

            if (isValidEmployment(employmentStatus)) {
                return employmentStatus;
            }
            else {
                System.out.println("Incorrect input provided. Please try again\n");
            }
        }
    }
    public boolean isValidEmployment(String employmentStatus) {
        return possEmployment.contains(employmentStatus);
    }

    public float validateAnnualIncome() {

        while (true) {
            System.out.print("Annual Income: ");
            String annualIncome = scannerObj.next();

            if (isValidAnnualIncome(annualIncome)) {
                return Float.parseFloat(annualIncome);
            }
            else {
                System.out.println("Incorrect input provided. Please ensure you enter digits only\n");
            }
        }
    }

    public boolean isValidAnnualIncome(String income) {

        try {
            float temp = Float.parseFloat(income);
        }
        catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    public String validateEmailAddress() {

        while (true) {
            System.out.print("Email Address: ");
            String email = scannerObj.next();

            if (isValidEmailAddress(email)) {
                return email;
            } else {
                System.out.println("Incorrect input provided. Please ensure the email you are using is valid.\n");
            }
        }
    }
    public boolean isValidEmailAddress(String email) {
        // return true if email address has a valid format e.g., name@email.com
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }
}







