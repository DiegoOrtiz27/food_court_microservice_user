package com.foodquart.microserviceuser.infrastructure.out.jpa.adapter;

import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.RoleEntity;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IRoleRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);

        RoleEntity roleEntity = roleRepository.findById(userModel.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userModel.getRole().getId()));
        userEntity.setRole(roleEntity);

        UserEntity saved = userRepository.save(userEntity);

        return userEntityMapper.toUserModel(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocumentId(String documentId) {
        return userRepository.existsByDocumentId(documentId);
    }
}
