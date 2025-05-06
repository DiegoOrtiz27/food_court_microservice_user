package com.foodquart.microserviceuser.domain.util;

import com.foodquart.microserviceuser.domain.exception.*;
import com.foodquart.microserviceuser.domain.model.UserModel;

import java.time.LocalDate;
import java.time.Period;

public class ValidationUtil {
    public static final String EMAIL_REGEX = "^[\\w+.-]+@[\\w.-]+\\.[a-z]{2,}$";
    public static final String PHONE_REGEX = "^\\+?\\d{10,13}$";
    public static final String NUMERIC_REGEX = "^\\d+$";
    public static final int MAX_PHONE_LENGTH = 13;

    private ValidationUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidPhoneNumber(String phoneNumber, int maxLength) {
        return phoneNumber != null &&
                phoneNumber.matches(PHONE_REGEX) &&
                phoneNumber.length() <= maxLength;
    }

    public static boolean isNumericDocument(String documentId) {
        return documentId != null && documentId.matches(NUMERIC_REGEX);
    }

    public static void validateRequiredFields(UserModel user) {
        if (isBlank(user.getFirstName())) {
            throw new RequiredFieldException("Name");
        }
        if (isBlank(user.getLastName())) {
            throw new RequiredFieldException("Last name");
        }
        if (isBlank(user.getDocumentId())) {
            throw new RequiredFieldException("Document ID");
        }
        if (isBlank(user.getPhone())) {
            throw new RequiredFieldException("Phone");
        }
        if (isBlank(user.getEmail())) {
            throw new RequiredFieldException("Email");
        }
        if (isBlank(user.getPassword())) {
            throw new RequiredFieldException("Password");
        }
        if (user.getRole() == null) {
            throw new RequiredFieldException("Role");
        }
    }

    public static void validateFormat(UserModel user) {
        if (!isValidEmail(user.getEmail())) {
            throw new InvalidEmailFormatException(user.getEmail());
        }
        if (!isValidPhoneNumber(user.getPhone(), MAX_PHONE_LENGTH)) {
            throw new InvalidPhoneNumberException(user.getPhone());
        }
        if (!isNumericDocument(user.getDocumentId())) {
            throw new InvalidDocumentIdException(user.getDocumentId());
        }
    }

    public static void validateByRole(UserModel user) {
        if(user.getRole() == Role.OWNER) {
            if (user.getBirthDate() == null) {
                throw new RequiredFieldException("Birthdate");
            }
            if (!isAdult(user.getBirthDate())) {
                throw new UserNotAdultException(user.getFirstName(), user.getLastName());
            }
        }
    }

    public static boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
