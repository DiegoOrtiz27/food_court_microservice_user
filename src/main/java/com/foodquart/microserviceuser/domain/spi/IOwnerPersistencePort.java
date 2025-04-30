package com.foodquart.microserviceuser.domain.spi;

import com.foodquart.microserviceuser.domain.model.OwnerModel;

public interface IOwnerPersistencePort {

    OwnerModel saveOwner(OwnerModel ownerModel);

    boolean existsById(Long id);

}
