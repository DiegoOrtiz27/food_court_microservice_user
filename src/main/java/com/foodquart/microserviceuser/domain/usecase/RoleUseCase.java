package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IRoleServicePort;
import com.foodquart.microserviceuser.domain.exception.NoDataFoundException;
import com.foodquart.microserviceuser.domain.util.Role;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleUseCase implements IRoleServicePort {
    private final IUserPersistencePort userPersistencePort;

    @Override
    public boolean hasRole(Long userId, Role expectedRole) {
        UserModel user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new NoDataFoundException("The user was not found"));
        return user.getRole() == expectedRole;
    }
}
