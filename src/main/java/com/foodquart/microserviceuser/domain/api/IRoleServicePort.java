package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.util.Role;

public interface IRoleServicePort {
    boolean hasRole(Long userId, Role expectedRole);
}
