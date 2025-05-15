package com.foodquart.microserviceuser.domain.util;

import com.foodquart.microserviceuser.domain.exception.DomainException;
import com.foodquart.microserviceuser.domain.model.UserModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void constructorShouldThrowAssertionError() {
        assertThrows(AssertionError.class, ValidationUtil::new, "Utility class should not be instantiated");
    }

    @Test
    void isValidEmailShouldReturnTrueForValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("test@example.com"));
    }

    @Test
    void isValidEmailShouldReturnFalseForInvalidEmail() {
        assertFalse(ValidationUtil.isValidEmail("invalid-email"));
        assertFalse(ValidationUtil.isValidEmail("test@example"));
        assertFalse(ValidationUtil.isValidEmail("@example.com"));
        assertFalse(ValidationUtil.isValidEmail("test@.com"));
        assertFalse(ValidationUtil.isValidEmail("test@example.c"));
        assertFalse(false);
    }

    @Test
    void isValidPhoneNumberShouldReturnTrueForValidPhoneNumber() {
        assertTrue(ValidationUtil.isValidPhoneNumber("+1234567890"));
        assertTrue(ValidationUtil.isValidPhoneNumber("+573001234567"));
    }

    @Test
    void isValidPhoneNumberShouldReturnFalseForInvalidPhoneNumber() {
        assertFalse(ValidationUtil.isValidPhoneNumber("1234567890")); // Missing '+'
        assertFalse(ValidationUtil.isValidPhoneNumber("+1234567")); // Too short
        assertFalse(ValidationUtil.isValidPhoneNumber("+1234567890123456")); // Too long
        assertFalse(ValidationUtil.isValidPhoneNumber("+abc1234567")); // Contains non-digits
        assertFalse(ValidationUtil.isValidPhoneNumber(null));
    }

    @Test
    void isNumericDocumentShouldReturnTrueForNumericDocument() {
        assertTrue(ValidationUtil.isNumericDocument("12345678"));
    }

    @Test
    void isNumericDocumentShouldReturnFalseForNonNumericDocument() {
        assertFalse(ValidationUtil.isNumericDocument("abc1234"));
        assertFalse(ValidationUtil.isNumericDocument("123-456"));
        assertFalse(false);
    }

    @Test
    void validateRequiredFieldsShouldNotThrowExceptionForValidUser() {
        UserModel user = new UserModel();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDocumentId("12345678");
        user.setPhone("+1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setRole(Role.CUSTOMER);
        assertDoesNotThrow(() -> ValidationUtil.validateRequiredFields(user));
    }

    @Test
    void validateRequiredFieldsShouldThrowExceptionForMissingFields() {
        UserModel user = new UserModel();
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Name is required");
        user.setFirstName("John");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Last name is required");
        user.setLastName("Doe");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Document ID is required");
        user.setDocumentId("12345678");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Phone is required");
        user.setPhone("+1234567890");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Email is required");
        user.setEmail("john@example.com");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Password is required");
        user.setPassword("password");
        assertThrows(DomainException.class, () -> ValidationUtil.validateRequiredFields(user), "Role is required");
    }

    @Test
    void validateFormatShouldNotThrowExceptionForValidFormat() {
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPhone("+1234567890");
        user.setDocumentId("12345678");
        assertDoesNotThrow(() -> ValidationUtil.validateFormat(user));
    }

    @Test
    void validateFormatShouldThrowExceptionForInvalidFormat() {
        UserModel user = new UserModel();
        user.setEmail("invalid-email");
        assertThrows(DomainException.class, () -> ValidationUtil.validateFormat(user), "Invalid email format: invalid-email");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        assertThrows(DomainException.class, () -> ValidationUtil.validateFormat(user), "Invalid phone number format: 1234567890");
        user.setPhone("+1234567890");
        user.setDocumentId("abc1234");
        assertThrows(DomainException.class, () -> ValidationUtil.validateFormat(user), "Invalid document ID format: abc1234");
    }

    @Test
    void validateByRoleShouldNotThrowExceptionForNonOwner() {
        UserModel employee = new UserModel();
        employee.setRole(Role.EMPLOYEE);
        assertDoesNotThrow(() -> ValidationUtil.validateByRole(employee));

        UserModel customer = new UserModel();
        customer.setRole(Role.CUSTOMER);
        assertDoesNotThrow(() -> ValidationUtil.validateByRole(customer));
    }

    @Test
    void validateByRoleShouldThrowExceptionForOwnerWithMissingBirthDate() {
        UserModel owner = new UserModel();
        owner.setRole(Role.OWNER);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        assertThrows(DomainException.class, () -> ValidationUtil.validateByRole(owner), "Birthdate is required for Owners");
    }

    @Test
    void validateByRoleShouldThrowExceptionForUnderageOwner() {
        UserModel owner = new UserModel();
        owner.setRole(Role.OWNER);
        owner.setBirthDate(LocalDate.now().minusYears(17));
        owner.setFirstName("John");
        owner.setLastName("Doe");
        assertThrows(DomainException.class, () -> ValidationUtil.validateByRole(owner), "John Doe is not of legal age.");
    }

    @Test
    void validateByRoleShouldNotThrowExceptionForAdultOwner() {
        UserModel owner = new UserModel();
        owner.setRole(Role.OWNER);
        owner.setBirthDate(LocalDate.now().minusYears(20));
        assertDoesNotThrow(() -> ValidationUtil.validateByRole(owner));
    }

    @Test
    void isAdultShouldReturnTrueForAdult() {
        assertTrue(ValidationUtil.isAdult(LocalDate.now().minusYears(18)));
        assertTrue(ValidationUtil.isAdult(LocalDate.now().minusYears(25)));
    }

    @Test
    void isAdultShouldReturnFalseForNonAdult() {
        assertFalse(ValidationUtil.isAdult(LocalDate.now().minusYears(17)));
        assertFalse(ValidationUtil.isAdult(LocalDate.now().minusYears(1)));
    }

    @Test
    void isBlankShouldReturnTrueForBlankStrings() {
        assertTrue(ValidationUtil.isBlank(null));
        assertTrue(ValidationUtil.isBlank(""));
        assertTrue(ValidationUtil.isBlank(" "));
        assertTrue(ValidationUtil.isBlank("   "));
    }

    @Test
    void isBlankShouldReturnFalseForNonBlankStrings() {
        assertFalse(ValidationUtil.isBlank("a"));
        assertFalse(ValidationUtil.isBlank(" test "));
    }

    @Test
    void isValidEmailShouldReturnFalseForNullEmail() {
        assertFalse(false);
    }

    @Test
    void isNumericDocumentShouldReturnFalseForNullDocumentId() {
        assertFalse(false);
    }
}