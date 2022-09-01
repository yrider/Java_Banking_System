package com.bankingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = new Database();
    }

    @Test
    public void itShouldValidateFullCustomerIntroRequest() {
        // given account number
        int customerID = 23651794;

        String fullUserIntro = database.getFullCustomerIntro(customerID);
        assertEquals("test_title. test_forename test_surname", fullUserIntro);
    }

    @Test
    public void itShouldValidateShortCustomerIntroRequest() {
        // given account number
        int customerID = 23651794;

        String fullUserIntro = database.getShortCustomerIntro(customerID);

        assertEquals("test_title. test_surname", fullUserIntro);
    }

    @Test
    public void itShouldValidateAllCustomerDetailsRequest() {
        // given account number
        int customerID = 23651794;

        Map<String, Object> trueInputs = Map.ofEntries(
                entry("title", "test_title"),
                entry("forename", "test_forename"),
                entry("surname", "test_surname"),
                entry("gender", "Male"),
                entry("dob", "test_dob"),
                entry("mobile", "test_mobile"),
                entry("employment_status", "full-time"),
                entry("annual_income", 100000)
        );

        Map<String, Object> test = database.getAllCustomerDetails(customerID);

        assertEquals(trueInputs, test);
    }

    @Test
    public void itShouldValidateGenderRequest() {
        // given account number
        int customerID = 23651794;

        String gender = database.getOneCustomerDetail("gender", customerID);

        assertEquals("Male", gender);
    }

    @Test
    public void itShouldValidateEmploymentStatusRequest() {
        // given account number
        int customerID = 23651794;

        String employmentStatus = database.getOneCustomerDetail("employment_status", customerID);

        assertEquals("full-time", employmentStatus);
    }

    @Test
    public void itShouldValidatePasswordRequest() {
        // given account number
        int customerID = 23651794;

        String password = database.getOneCustomerDetail("password", customerID);

        assertEquals("test_password", password);
    }

    @Test
    public void itShouldValidateIncomeRequest() {
        // given account number
        int customerID = 23651794;

        float annualIncome = database.getAnnualIncome(customerID);

        assertEquals(100000, annualIncome);
    }

    @Test
    public void itShouldValidateMobileRequest() {
        // given account number
        int customerID = 23651794;

        String mobile = database.getOneCustomerDetail("mobile", customerID);

        assertEquals("test_mobile", mobile);
    }

    @Test
    public void itShouldValidateDobRequest() {
        // given account number
        int customerID = 23651794;

        String dob = database.getOneCustomerDetail("dob", customerID);

        assertEquals("test_dob", dob);
    }

    @Test
    public void itShouldValidateBalanceRequest() {
        // given account number
        int accountNumber = 23651794;

        float balance = database.getBalance(accountNumber);

        assertEquals(1000, balance);
    }

    @Test
    public void itShouldReturnFalseAsAccountDoesNotExist() {
        // given customer id
        int customerID = 1111;

        boolean shouldBeFalse = database.customerIDExists(customerID);

        assertFalse(shouldBeFalse);
    }

    @Test
    public void itShouldReturnFalseAsAccountDoesExist() {
        // given customer id
        int customerID = 23651797;

        boolean shouldBeTrue = database.customerIDExists(customerID);
        assertTrue(shouldBeTrue);
    }

    @Test
    public void shouldReturnTrueAsAccountExists() {

        int customerID = 23651794;
        boolean shouldBeTrue = database.customerIDExists(customerID);
        assertTrue(shouldBeTrue);
    }

    @Test
    public void shouldReturnFalseAsAccountExists() {

        int customerID = 2379455;
        boolean shouldBeFalse = database.customerIDExists(customerID);
        assertFalse(shouldBeFalse);
    }


}
