package com.bankingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }

    @Test
    public void shouldReturnTrueForValidPassword() {

        String enteredPassword = "testPassword$123";
        String realPassword = "testPassword$123";

        boolean shouldBeTrue = bank.isCorrectPassword(enteredPassword, realPassword);

        assertTrue(shouldBeTrue);
    }

    @Test
    public void shouldReturnFalseForInvalidPassword() {

        String enteredPassword = "testPassword$123";
        String realPassword = "testPasswrd$123";

        boolean shouldBeFalse = bank.isCorrectPassword(enteredPassword, realPassword);

        assertFalse(shouldBeFalse);
    }


}
