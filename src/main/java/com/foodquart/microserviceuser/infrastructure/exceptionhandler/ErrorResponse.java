package com.foodquart.microserviceuser.infrastructure.exceptionhandler;


public record ErrorResponse(String errorCode, String message) {
}