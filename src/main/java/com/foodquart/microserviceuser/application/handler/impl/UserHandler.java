package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.UserRegisterRequestDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserByEmailResponseDto;
import com.foodquart.microserviceuser.application.handler.IUserHandler;
import com.foodquart.microserviceuser.application.mapper.IUserRequestMapper;
import com.foodquart.microserviceuser.application.mapper.IUserResponseMapper;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.exception.NoDataFoundException;
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
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void createOwner(UserRegisterRequestDto userRegisterRequestDto) {
        UserModel user = userRequestMapper.toUser(userRegisterRequestDto);
        user.setRole(Role.OWNER);
        userServicePort.saveUser(user);
    }

    @Override
    public GetUserByEmailResponseDto getUserByEmail(String email) {
        UserModel user = userServicePort.getUserByEmail(email)
                                    .orElseThrow(() -> new NoDataFoundException("The user was not found"));
        return userResponseMapper.toResponse(user);
    }
}
