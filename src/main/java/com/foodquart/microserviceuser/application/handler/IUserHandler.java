package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.request.CreateUserRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;

public interface IUserHandler {

    CreateUserResponseDto createOwner(CreateUserRequestDto createOwnerRequestDto);

    CreateUserResponseDto createEmployee(CreateUserRequestDto createUserRequestDto);

    CreateUserResponseDto createCustomer(CreateUserRequestDto createUserRequestDto);

}
