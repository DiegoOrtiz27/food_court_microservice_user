package com.foodquart.microserviceuser.infrastructure.documentation;

public class APIUserDocumentationConstant {

    public static final String CREATE_OWNER_SUMMARY = "Create a new restaurant owner";
    public static final String CREATE_OWNER_SUCCESS_DESCRIPTION = "Owner created successfully";
    public static final String CREATE_OWNER_INVALID_FIELDS_DESCRIPTION = "Validation failed: invalid fields like email, phone, or underage";
    public static final String CREATE_OWNER_FORBIDDEN_DESCRIPTION = "Forbidden: Only ADMIN users are allowed to create owners";
    public static final String CREATE_OWNER_CONFLICT_DESCRIPTION = "Conflict: Email or document already registered";
    public static final String CREATE_OWNER_REQUEST_BODY_DESCRIPTION = "Owner registration details including name, document, phone, email, etc.";

    public static final String CREATE_EMPLOYEE_SUMMARY = "Create a new employee";
    public static final String CREATE_EMPLOYEE_SUCCESS_DESCRIPTION = "Employee created successfully";
    public static final String CREATE_EMPLOYEE_INVALID_FIELDS_DESCRIPTION = "Validation failed: invalid fields like email, phone, or underage";
    public static final String CREATE_EMPLOYEE_FORBIDDEN_DESCRIPTION = "Forbidden: Only OWNER users are allowed to create employee";
    public static final String CREATE_EMPLOYEE_CONFLICT_DESCRIPTION = "Conflict: Email or document already registered";
    public static final String CREATE_EMPLOYEE_REQUEST_BODY_DESCRIPTION = "Employee registration details including name, document, phone, email, etc.";

    public static final String CREATE_CUSTOMER_SUMMARY = "Create a new customer";
    public static final String CREATE_CUSTOMER_SUCCESS_DESCRIPTION = "Customer created successfully";
    public static final String CREATE_CUSTOMER_INVALID_FIELDS_DESCRIPTION = "Validation failed: invalid fields like email, phone, or underage";
    public static final String CREATE_CUSTOMER_CONFLICT_DESCRIPTION = "Conflict: Email or document already registered";
    public static final String CREATE_CUSTOMER_REQUEST_BODY_DESCRIPTION = "Customer registration details including name, document, phone, email, etc.";

    public static final String GET_USER_INFO_SUMMARY = "Get user information";
    public static final String GET_USER_INFO_SUCCESS_DESCRIPTION = "User information retrieved successfully";
    public static final String GET_USER_INFO_NOT_FOUND_DESCRIPTION = "User not found";

    private APIUserDocumentationConstant() {
    }
}
