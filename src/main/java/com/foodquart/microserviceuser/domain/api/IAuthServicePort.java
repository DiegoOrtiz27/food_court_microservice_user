package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.model.AuthModel;

public interface IAuthServicePort {

    String authenticate(AuthModel authModel);
}
