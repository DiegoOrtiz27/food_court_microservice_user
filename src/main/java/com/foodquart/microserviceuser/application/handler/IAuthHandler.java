package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.request.LoginRequestDto;
import com.foodquart.microserviceuser.application.dto.response.LoginResponseDto;

public interface IAuthHandler {

    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
}
