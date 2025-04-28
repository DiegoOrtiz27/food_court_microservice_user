package com.foodquart.microserviceuser.domain.exception;

public class RequiredFieldException extends DomainException {
    public RequiredFieldException(String fieldName) {
        super("Field '" + fieldName + "' is required");
    }
}
