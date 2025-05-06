package com.foodquart.microserviceuser.infrastructure.exceptionhandler;

import com.foodquart.microserviceuser.domain.exception.DocumentIdAlreadyExistsException;
import com.foodquart.microserviceuser.domain.exception.DomainException;
import com.foodquart.microserviceuser.domain.exception.EmailAlreadyExistsException;
import com.foodquart.microserviceuser.domain.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse("EMAIL_CONFLICT", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DocumentIdAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantAlreadyHasOwner(DocumentIdAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse("DOCUMENT_CONFLICT", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDataFoundException(NoDataFoundException ex) {
        ErrorResponse error = new ErrorResponse("NO_DATA_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        ErrorResponse error = new ErrorResponse("DOMAIN_ERROR", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ValidationError> errors = fieldErrors.stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                "VALIDATION_FAILED",
                "Validation failed for one or more fields",
                errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
