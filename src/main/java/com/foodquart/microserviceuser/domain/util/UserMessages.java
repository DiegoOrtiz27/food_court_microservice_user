package com.foodquart.microserviceuser.domain.util;

public class UserMessages {

    private UserMessages() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OWNER_CREATED = "The owner has been created successfully";
    public static final String EMPLOYEE_CREATED = "The employee has been created successfully";
    public static final String CUSTOMER_CREATED = "The customer has been created successfully";

    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_ALREADY_USED = "The email '%s' is already in use";
    public static final String DOCUMENT_ID_ALREADY_USED = "The document id '%s' is already in use";
    public static final String INVALID_DOCUMENT_ID = "Document ID '%s' must contain only numbers";
    public static final String INVALID_EMAIL = "Email '%s' has an invalid format";
    public static final String INVALID_PHONE_NUMBER = "Phone number '%s' is invalid. Phone number must include country code (e.g., +57xxxxxxxxxx)";
    public static final String REQUIRED_FIELD = "Field '%s' is required";
    public static final String USER_NOT_ADULT = "User '%s %s' must be at least 18 years old";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
}
