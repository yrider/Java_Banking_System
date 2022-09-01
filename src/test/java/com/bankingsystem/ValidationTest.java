package com.bankingsystem;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    Validation validation = new Validation();

    @ParameterizedTest
    @ValueSource(strings = {"Mr", "mr", "Mrs", "mrs", "Miss", "miss", "Ms", "ms", "Sir", "sir", "Dr", "dr", "Lord", "lord"})
    public void shouldReturnTrueForValidTitle(String validTitles) {
        assertTrue(validation.isCorrectTitle(validTitles));
    }

    @Test
    public void shouldReturnFalseForInvalidTitle() {
        String title = "Mss";
        boolean invalidTitle = validation.isCorrectTitle(title);
        // test
        assertFalse(invalidTitle);
    }

    @Test
    public void shouldReturnTrueForValidPassword() {
        // password must be >= 8 chars and contain at least one: capital letter, digit and special char
        String password = "Password_3%";
         // test
        assertTrue(validation.isValidPassword(password));
    }

    @Test
    public void shouldReturnFalseForInvalidPassword() {
        // password must be >= 8 chars and contain at least one: capital letter, digit and special char
        String password = "Password3";
        // test
        assertFalse(validation.isValidPassword(password));
    }

    @Test
    public void shouldReturnTrueForValidName() {
        // name cannot contain special characters or digits
        String name = "harry";
        // test
        assertTrue(validation.isValidName(name));
    }

    @Test
    public void shouldReturnFalseForInvalidName() {
        String name = "harry2";
        // test
        assertFalse(validation.isValidName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Male", "male", "Female", "female"})
    public void shouldReturnTrueForValidGender(String validGenders) {
        assertTrue(validation.isValidGender(validGenders));
    }

    @Test
    public void shouldReturnFalseForInvalidGender() {
        String invalidGender = "malee";
        assertFalse(validation.isValidGender(invalidGender));
    }

    @Test
    public void shouldReturnTrueForValidDOB() {
        // DOB must be in following fmt: YYYY/MM/DD and be LocalDate type
        String validDOB = "2000-01-01";
        assertTrue(validation.isValidDOB(validDOB));
    }

    @Test
    public void shouldReturnFalseForInvalidDOB() {
        String validDOB = "2000-13-01";
        assertFalse(validation.isValidDOB(validDOB));
    }

    @ParameterizedTest
    @ValueSource(strings = {"full-time", "part-time", "student", "unemployed"})
    public void shouldReturnTrueForValidEmploymentStatus(String validEmployments) {
        assertTrue(validation.isValidEmployment(validEmployments));
    }

    @Test
    public void shouldReturnFalseForInvalidEmploymentStatus() {
        String invalidEmployment = "ffull-time";
        assertFalse(validation.isValidEmployment(invalidEmployment));
    }

    @Test
    public void shouldReturnTrueForValidAnnualIncome() {
        String validIncome = "40000";
        assertTrue(validation.isValidAnnualIncome(validIncome));
    }

    @Test
    public void shouldReturnFalseForInvalidAnnualIncome() {
        String invalidIncome = "3000_";
        assertFalse(validation.isValidAnnualIncome(invalidIncome));
    }

    @Test
    public void shouldReturnTrueForValidEmailAddress() {
        String email = "york.rider@hotmail.com";
        assertTrue(validation.isValidEmailAddress(email));
    }

    @Test
    public void shouldReturnFalseForInvalidEmailAddress() {
        String email = "not_an_email@";
        assertFalse(validation.isValidEmailAddress(email));
    }
}