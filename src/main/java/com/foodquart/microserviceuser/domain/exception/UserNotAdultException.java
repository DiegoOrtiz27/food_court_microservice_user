package com.foodquart.microserviceuser.domain.exception;

public class UserNotAdultException extends DomainException {

    public UserNotAdultException(String firstName, String lastName) {
        super("User '" + firstName + " " + lastName + "' must be at least 18 years old");
    }
}