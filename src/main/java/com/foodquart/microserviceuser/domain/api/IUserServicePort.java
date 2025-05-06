package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.model.UserModel;


public interface IUserServicePort {
    UserModel saveUser(UserModel userModel);

}
