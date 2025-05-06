package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.exception.*;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.ValidationUtil;


public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        userModel.setEmail(userModel.getEmail().toLowerCase());
        ValidationUtil.validateRequiredFields(userModel);
        ValidationUtil.validateFormat(userModel);
        ValidationUtil.validateByRole(userModel);

        if (userPersistencePort.existsByEmail(userModel.getEmail())) {
            throw new EmailAlreadyExistsException(userModel.getEmail());
        }
        if (userPersistencePort.existsByDocumentId(userModel.getDocumentId())) {
            throw new DocumentIdAlreadyExistsException(userModel.getDocumentId());
        }

        String encryptedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);

        return userPersistencePort.saveUser(userModel);

    }

}