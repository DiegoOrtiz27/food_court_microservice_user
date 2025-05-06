package com.foodquart.microserviceuser.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponseDto {
    private Long userId;
    private String response;
}
