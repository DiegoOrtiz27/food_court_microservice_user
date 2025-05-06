package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.CreateUserRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import com.foodquart.microserviceuser.application.mapper.IUserRequestMapper;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.util.UserMessages;
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
    public CreateUserResponseDto createOwner(CreateUserRequestDto createOwnerRequestDto) {
        UserModel user = userRequestMapper.toUser(createOwnerRequestDto);
        user.setRole(Role.OWNER);
        user = userServicePort.saveUser(user);
        return userRequestMapper.toResponse(UserMessages.OWNER_CREATED, user.getId());
    }

    @Override
    public CreateUserResponseDto createEmployee(CreateUserRequestDto createUserRequestDto) {
        UserModel user = userRequestMapper.toUser(createUserRequestDto);
        user.setRole(Role.EMPLOYEE);
        user = userServicePort.saveUser(user);
        return userRequestMapper.toResponse(UserMessages.EMPLOYEE_CREATED, user.getId());
    }

    @Override
    public CreateUserResponseDto createCustomer(CreateUserRequestDto createUserRequestDto) {
        UserModel user = userRequestMapper.toUser(createUserRequestDto);
        user.setRole(Role.CUSTOMER);
        user = userServicePort.saveUser(user);
        return userRequestMapper.toResponse(UserMessages.CUSTOMER_CREATED, user.getId());
    }
}
