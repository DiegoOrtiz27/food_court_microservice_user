package com.foodquart.microserviceuser.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserByEmailResponseDto {
    private Long id;
    private String email;
}
