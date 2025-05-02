package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.model.UserModel;

import java.util.Optional;

public interface IUserServicePort {
    void saveUser(UserModel userModel);

    Optional<UserModel> getUserByEmail(String email);

}
