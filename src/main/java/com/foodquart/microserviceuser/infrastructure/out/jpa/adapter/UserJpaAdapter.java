package com.foodquart.microserviceuser.infrastructure.out.jpa.adapter;

import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel saveUser(UserModel user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
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


    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUserModel);
    }
}
