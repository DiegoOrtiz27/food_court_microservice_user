package com.foodquart.microserviceuser.domain.util;

public final class ValidationUtil {
    private static final String EMAIL_REGEX = "^[\\w+.-]+@[\\w.-]+\\.[a-z]{2,}$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,13}$";
    private static final String NUMERIC_REGEX = "^\\d+$";

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
}
