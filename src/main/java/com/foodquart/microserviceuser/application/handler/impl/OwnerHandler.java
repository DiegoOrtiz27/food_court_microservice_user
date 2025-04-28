package com.foodquart.microserviceuser.application.handler.impl;

import com.foodquart.microserviceuser.application.dto.request.OwnerRequestDto;
import com.foodquart.microserviceuser.application.handler.IOwnerHandler;
import com.foodquart.microserviceuser.application.mapper.IOwnerRequestMapper;
import com.foodquart.microserviceuser.domain.api.IOwnerServicePort;
import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerHandler implements IOwnerHandler {

    private final IOwnerServicePort ownerServicePort;
    private final IOwnerRequestMapper ownerRequestMapper;

    @Override
    public void saveOwner(OwnerRequestDto ownerRequestDto) {
        UserModel user = ownerRequestMapper.toUserModel(ownerRequestDto);
        OwnerModel owner = ownerRequestMapper.toOwnerModel(ownerRequestDto, user);
        ownerServicePort.saveOwner(owner, user);
    }
}
