package com.foodquart.microserviceuser.application.handler;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;

public interface IOwnerHandler {

    void saveOwner(OwnerRequestDto ownerRequestDto);

    boolean isUserOwner(Long id);
}
