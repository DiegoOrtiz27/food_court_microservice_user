package com.foodquart.microserviceuser.domain.api;

import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.UserModel;

public interface IOwnerServicePort {
    void saveOwner(OwnerModel ownerModel, UserModel userModel);
}
