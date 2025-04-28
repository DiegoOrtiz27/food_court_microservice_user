package com.foodquart.microserviceuser.domain.exception;

public class DocumentIdAlreadyExistsException extends DomainException {
  public DocumentIdAlreadyExistsException(String documentId) {
    super("The document id " + documentId + " already has a registered.");
  }
}
