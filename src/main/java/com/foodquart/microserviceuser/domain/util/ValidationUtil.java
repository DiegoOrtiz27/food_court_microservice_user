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
    public static final int MIN_ADULT_AGE = 18;
    public static final String FIELD_FIRST_NAME = "Name";
    public static final String FIELD_LAST_NAME = "Last name";
    public static final String FIELD_DOCUMENT_ID = "Document ID";
    public static final String FIELD_PHONE = "Phone";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_PASSWORD = "Password";
    public static final String FIELD_ROLE = "Role";
    public static final String FIELD_BIRTHDATE = "Birthdate";

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
            throw new RequiredFieldException(FIELD_FIRST_NAME);
        }
        if (isBlank(user.getLastName())) {
            throw new RequiredFieldException(FIELD_LAST_NAME);
        }
        if (isBlank(user.getDocumentId())) {
            throw new RequiredFieldException(FIELD_DOCUMENT_ID);
        }
        if (isBlank(user.getPhone())) {
            throw new RequiredFieldException(FIELD_PHONE);
        }
        if (isBlank(user.getEmail())) {
            throw new RequiredFieldException(FIELD_EMAIL);
        }
        if (isBlank(user.getPassword())) {
            throw new RequiredFieldException(FIELD_PASSWORD);
        }
        if (user.getRole() == null) {
            throw new RequiredFieldException(FIELD_ROLE);
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
                throw new RequiredFieldException(FIELD_BIRTHDATE);
            }
            if (!isAdult(user.getBirthDate())) {
                throw new UserNotAdultException(user.getFirstName(), user.getLastName());
            }
        }
    }

    public static boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= MIN_ADULT_AGE;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}