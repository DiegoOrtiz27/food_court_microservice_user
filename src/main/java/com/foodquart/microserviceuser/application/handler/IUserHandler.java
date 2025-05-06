package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.request.CreateEmployeeRequestDto;
import com.foodquart.microserviceuser.application.dto.request.CreateOwnerRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;

public interface IUserHandler {

    CreateUserResponseDto createOwner(CreateOwnerRequestDto createOwnerRequestDto);

    CreateUserResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto);

}
