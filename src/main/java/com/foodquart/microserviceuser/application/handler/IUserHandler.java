package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.request.UserRegisterRequestDto;
import com.foodquart.microserviceuser.application.dto.response.GetUserByEmailResponseDto;

public interface IUserHandler {

    void createOwner(UserRegisterRequestDto userRegisterRequestDto);

    GetUserByEmailResponseDto getUserByEmail(String email);

}
