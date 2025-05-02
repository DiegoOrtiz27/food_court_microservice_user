package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.LoginRequestDto;
import com.foodquart.microserviceuser.application.dto.response.LoginResponseDto;
import com.foodquart.microserviceuser.application.handler.IAuthHandler;
import com.foodquart.microserviceuser.application.mapper.IAuthRequestMapper;
import com.foodquart.microserviceuser.application.mapper.IAuthResponseMapper;
import com.foodquart.microserviceuser.domain.api.IAuthServicePort;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;
    private final IAuthRequestMapper authRequestMapper;
    private final IAuthResponseMapper authResponseMapper;

    @Override
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        AuthModel authModel = authRequestMapper.toAuth(loginRequestDto);
        return authResponseMapper.toResponse(authServicePort.authenticate(authModel));
    }
}
