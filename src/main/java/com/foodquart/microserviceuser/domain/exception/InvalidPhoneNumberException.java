package com.foodquart.microserviceuser.domain.exception;

public class InvalidPhoneNumberException extends DomainException {
    public InvalidPhoneNumberException(String phoneNumber) {
        super("Phone number '" + phoneNumber + "' is invalid. It should have maximum 13 characters and can include '+'");
    }
}
