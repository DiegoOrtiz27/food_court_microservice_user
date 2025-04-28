package com.foodquart.microserviceuser.domain.exception;

public class InvalidEmailFormatException extends DomainException {
    public InvalidEmailFormatException(String email) {
        super("Email '" + email + "' has an invalid format");
    }
}
