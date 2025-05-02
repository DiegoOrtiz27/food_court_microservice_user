package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.response.HasRoleResponseDto;

public interface IRoleHandler {
    HasRoleResponseDto hasRole(Long userId, String roleName);
}
