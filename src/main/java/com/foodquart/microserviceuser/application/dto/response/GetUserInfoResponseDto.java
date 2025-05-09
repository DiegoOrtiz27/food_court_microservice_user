package com.foodquart.microserviceuser.application.dto.response;

import lombok.*;

@Getter
@Setter
public class GetUserInfoResponseDto {
    private Long userId;
    private String phone;
}
