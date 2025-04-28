package com.foodquart.microserviceuser.domain.exception;

public class EmailAlreadyExistsException extends DomainException  {
    public EmailAlreadyExistsException(String email) {
        super("The email '" + email + "' is already in use");
    }
}
