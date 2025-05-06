package com.foodquart.microserviceuser.domain.spi;

import com.foodquart.microserviceuser.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistencePort {

    UserModel saveUser(UserModel userModel);

    boolean existsByEmail(String email);

    boolean existsByDocumentId(String documentId);

    Optional<UserModel> findByEmail(String email);
}
