package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.LoginRequestDto;
import com.foodquart.microserviceuser.application.dto.response.LoginResponseDto;
import com.foodquart.microserviceuser.application.mapper.IAuthRequestMapper;
import com.foodquart.microserviceuser.application.mapper.IAuthResponseMapper;
import com.foodquart.microserviceuser.domain.api.IAuthServicePort;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private IAuthServicePort authServicePort;

    @Mock
    private IAuthRequestMapper authRequestMapper;

    @Mock
    private IAuthResponseMapper authResponseMapper;

    @InjectMocks
    private AuthHandler authHandler;

    @Test
    void authenticateShouldMapRequestAndDelegateToAuthService() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@example.com");
        loginRequestDto.setPassword("password");

        AuthModel authModel = new AuthModel("test@example.com", "password");
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken("mockedToken");

        when(authRequestMapper.toAuth(loginRequestDto)).thenReturn(authModel);
        when(authServicePort.authenticate(authModel)).thenReturn(String.valueOf(authModel));
        when(authResponseMapper.toResponse(String.valueOf(authModel))).thenReturn(loginResponseDto);

        LoginResponseDto result = authHandler.authenticate(loginRequestDto);

        assertEquals(loginResponseDto, result);
        verify(authRequestMapper).toAuth(loginRequestDto);
        verify(authServicePort).authenticate(authModel);
        verify(authResponseMapper).toResponse(String.valueOf(authModel));
    }
}