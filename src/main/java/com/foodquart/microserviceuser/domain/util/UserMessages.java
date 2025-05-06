package com.foodquart.microserviceuser.domain.util;

public class UserMessages {

    private UserMessages() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OWNER_CREATED = "The owner has been created successfully";
    public static final String EMPLOYEE_CREATED = "The employee has been created successfully";
    public static final String CUSTOMER_CREATED = "The customer has been created successfully";

    public static final String USER_NOT_FOUND = "User not found";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
}
