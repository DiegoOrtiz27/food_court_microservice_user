package com.foodquart.microserviceuser.domain.exception;

public class InvalidDocumentIdException extends DomainException {
    public InvalidDocumentIdException(String documentId) {
        super("Document ID '" + documentId + "' must contain only numbers");
    }
}
