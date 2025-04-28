package com.foodquart.microserviceuser.domain.spi;

import com.foodquart.microserviceuser.domain.model.UserModel;

public interface IUserPersistencePort {

    UserModel saveUser(UserModel userModel);

    boolean existsByEmail(String email);

    boolean existsByDocumentId(String documentId);
}
