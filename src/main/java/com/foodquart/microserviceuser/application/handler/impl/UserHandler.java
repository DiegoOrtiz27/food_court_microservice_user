package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.CreateUserRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserInfoResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import com.foodquart.microserviceuser.application.mapper.IUserRequestMapper;
import com.foodquart.microserviceuser.application.mapper.IUserResponseMapper;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
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
    private final IUserResponseMapper userResponseMapper;

    @Override
    public CreateUserResponseDto createOwner(CreateUserRequestDto createOwnerRequestDto) {
        UserModel user = userRequestMapper.toUser(createOwnerRequestDto);
        user = userServicePort.createOwner(user);
        return userResponseMapper.toResponse(UserMessages.OWNER_CREATED, user.getId());
    }

    @Override
    public CreateUserResponseDto createEmployee(CreateUserRequestDto createUserRequestDto) {
        UserModel user = userRequestMapper.toUser(createUserRequestDto);
        user = userServicePort.createEmployee(user);
        return userResponseMapper.toResponse(UserMessages.EMPLOYEE_CREATED, user.getId());
    }

    @Override
    public CreateUserResponseDto createCustomer(CreateUserRequestDto createUserRequestDto) {
        UserModel user = userRequestMapper.toUser(createUserRequestDto);
        user = userServicePort.createCustomer(user);
        return userResponseMapper.toResponse(UserMessages.CUSTOMER_CREATED, user.getId());
    }

    @Override
    public GetUserInfoResponseDto getUserInfo(Long userId) {
        return userResponseMapper.toResponse(userServicePort.getUserInfo(userId));
    }
}
