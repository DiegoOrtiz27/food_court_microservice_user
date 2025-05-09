package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IAuthServicePort;
import com.foodquart.microserviceuser.domain.exception.DomainException;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IJwtProviderPort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.util.UserMessages;

public class AuthUseCase implements IAuthServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IJwtProviderPort jwtProviderPort;

    public AuthUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort, IJwtProviderPort jwtProviderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtProviderPort = jwtProviderPort;
    }

    @Override
    public String authenticate(AuthModel authModel) {
        UserModel user = userPersistencePort.findByEmail(authModel.getEmail())
                .orElseThrow(() -> new DomainException(UserMessages.USER_NOT_FOUND));

        if (!passwordEncoderPort.matches(authModel.getPassword(), user.getPassword())) {
            throw new DomainException(UserMessages.INVALID_CREDENTIALS);
        }

        return jwtProviderPort.generateToken(user);
    }
}
