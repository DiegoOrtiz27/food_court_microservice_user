package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.model.UserModel;

public interface IUserServicePort {
    UserModel createOwner(UserModel userModel);

    UserModel createEmployee(UserModel userModel);

    UserModel createCustomer(UserModel userModel);

    UserModel getUserInfo(Long userId);

}
