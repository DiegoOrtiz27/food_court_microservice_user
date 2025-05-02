package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.response.HasRoleResponseDto;
import com.foodquart.microserviceuser.application.handler.IRoleHandler;
import com.foodquart.microserviceuser.application.mapper.IRoleResponseMapper;
import com.foodquart.microserviceuser.domain.api.IRoleServicePort;
import com.foodquart.microserviceuser.domain.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleHandler implements IRoleHandler {

    private final IRoleServicePort roleServicePort;
    private final IRoleResponseMapper roleResponseMapper;

    @Override
    public HasRoleResponseDto hasRole(Long userId, String roleName) {
        return roleResponseMapper.toResponse(roleServicePort.hasRole(userId, Role.valueOf(roleName)));
    }
}
