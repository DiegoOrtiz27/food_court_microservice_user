package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IQueryOwnerServicePort;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueryOwnerUseCase implements IQueryOwnerServicePort {
    private final IOwnerPersistencePort ownerPersistencePort;

    public boolean isUserOwner(Long id) {
        return ownerPersistencePort.existsById(id);
    }
}
