package com.foodquart.microserviceuser.infrastructure.documentation;

public class APILoginDocumentationConstant {

    public static final String LOGIN_SUMMARY = "Login to the system";
    public static final String LOGIN_DESCRIPTION = "Allows a user (ADMIN, OWNER, EMPLOYEE, CLIENT) to authenticate using email and password. Returns a JWT token if credentials are valid.";
    public static final String LOGIN_REQUEST_BODY_DESCRIPTION = "Login credentials: email and password";
    public static final String LOGIN_SUCCESS_DESCRIPTION = "Authentication successful";
    public static final String LOGIN_INVALID_REQUEST_DESCRIPTION = "Invalid request format";
    public static final String LOGIN_INVALID_CREDENTIALS_DESCRIPTION = "Invalid credentials";
    public static final String LOGIN_USER_NOT_FOUND_DESCRIPTION = "User not found";
    public static final String LOGIN_TAG = "Authentication";

    private APILoginDocumentationConstant() {
    }
}