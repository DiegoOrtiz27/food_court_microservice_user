package com.foodquart.microserviceuser.domain.exception;

public class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
}
