package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.CreateUserRequestDto;
import com.foodquart.microserviceuser.application.dto.response.CreateUserResponseDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserInfoResponseDto;
import com.foodquart.microserviceuser.application.mapper.IUserRequestMapper;
import com.foodquart.microserviceuser.application.mapper.IUserResponseMapper;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.util.UserMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    void createOwnerShouldMapRequestAndDelegateToUserService() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        UserModel userModel = new UserModel();
        CreateUserResponseDto responseDto = new CreateUserResponseDto();

        when(userRequestMapper.toUser(requestDto)).thenReturn(userModel);
        when(userServicePort.createOwner(userModel)).thenReturn(userModel);
        when(userResponseMapper.toResponse(UserMessages.OWNER_CREATED, userModel.getId())).thenReturn(responseDto);

        CreateUserResponseDto result = userHandler.createOwner(requestDto);

        assertEquals(responseDto, result);
        verify(userRequestMapper).toUser(requestDto);
        verify(userServicePort).createOwner(userModel);
        verify(userResponseMapper).toResponse(UserMessages.OWNER_CREATED, userModel.getId());
    }

    @Test
    void createEmployeeShouldMapRequestAndDelegateToUserService() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        UserModel userModel = new UserModel();
        CreateUserResponseDto responseDto = new CreateUserResponseDto();

        when(userRequestMapper.toUser(requestDto)).thenReturn(userModel);
        when(userServicePort.createEmployee(userModel)).thenReturn(userModel);
        when(userResponseMapper.toResponse(UserMessages.EMPLOYEE_CREATED, userModel.getId())).thenReturn(responseDto);

        CreateUserResponseDto result = userHandler.createEmployee(requestDto);

        assertEquals(responseDto, result);
        verify(userRequestMapper).toUser(requestDto);
        verify(userServicePort).createEmployee(userModel);
        verify(userResponseMapper).toResponse(UserMessages.EMPLOYEE_CREATED, userModel.getId());
    }

    @Test
    void createCustomerShouldMapRequestAndDelegateToUserService() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        UserModel userModel = new UserModel();
        CreateUserResponseDto responseDto = new CreateUserResponseDto();

        when(userRequestMapper.toUser(requestDto)).thenReturn(userModel);
        when(userServicePort.createCustomer(userModel)).thenReturn(userModel);
        when(userResponseMapper.toResponse(UserMessages.CUSTOMER_CREATED, userModel.getId())).thenReturn(responseDto);

        CreateUserResponseDto result = userHandler.createCustomer(requestDto);

        assertEquals(responseDto, result);
        verify(userRequestMapper).toUser(requestDto);
        verify(userServicePort).createCustomer(userModel);
        verify(userResponseMapper).toResponse(UserMessages.CUSTOMER_CREATED, userModel.getId());
    }

    @Test
    void getUserInfoShouldDelegateToUserServiceAndMapResponse() {
        Long userId = 1L;
        UserModel userModel = new UserModel();
        GetUserInfoResponseDto responseDto = new GetUserInfoResponseDto();

        when(userServicePort.getUserInfo(userId)).thenReturn(userModel);
        when(userResponseMapper.toResponse(userModel)).thenReturn(responseDto);

        GetUserInfoResponseDto result = userHandler.getUserInfo(userId);

        assertEquals(responseDto, result);
        verify(userServicePort).getUserInfo(userId);
        verify(userResponseMapper).toResponse(userModel);
    }
}