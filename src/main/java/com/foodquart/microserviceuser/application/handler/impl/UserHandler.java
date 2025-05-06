package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.CreateEmployeeRequestDto;
import com.foodquart.microserviceuser.application.dto.request.CreateOwnerRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import com.foodquart.microserviceuser.application.mapper.IUserRequestMapper;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public CreateUserResponseDto createOwner(CreateOwnerRequestDto createOwnerRequestDto) {
        UserModel user = userRequestMapper.toUser(createOwnerRequestDto);
        user.setRole(Role.OWNER);
        user = userServicePort.saveUser(user);
        return userRequestMapper.toResponse("The owner has been created successfully", user.getId());
    }

    @Override
    public CreateUserResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        UserModel user = userRequestMapper.toUser(createEmployeeRequestDto);
        user.setRole(Role.EMPLOYEE);
        user = userServicePort.saveUser(user);
        return userRequestMapper.toResponse("The employee has been created successfully", user.getId());
    }
}
