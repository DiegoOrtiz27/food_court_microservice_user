package com.foodquart.microserviceuser.infrastructure.out.jpa.adapter;

import com.foodquart.microserviceuser.domain.model.OwnerModel;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.OwnerEntity;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IOwnerEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IOwnerRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OwnerJpaAdapter implements IOwnerPersistencePort {

    private final IOwnerRepository ownerRepository;
    private final IOwnerEntityMapper ownerEntityMapper;
    private final IUserRepository userRepository;

    @Override
    public OwnerModel saveOwner(OwnerModel ownerModel) {
        OwnerEntity ownerEntity = ownerEntityMapper.toOwnerEntity(ownerModel);

        UserEntity userEntity = userRepository.findById(ownerModel.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + ownerModel.getUser().getId()));


        ownerEntity.setUser(userEntity);

        OwnerEntity saved = ownerRepository.save(ownerEntity);
        return ownerEntityMapper.toOwnerModel(saved);
    }

}
