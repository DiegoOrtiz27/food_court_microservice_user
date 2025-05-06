package com.foodquart.microserviceuser.domain.usecase;

import com.foodquart.microserviceuser.domain.api.IAuthServicePort;
import com.foodquart.microserviceuser.domain.exception.InvalidCredentialsException;
import com.foodquart.microserviceuser.domain.exception.NoDataFoundException;
import com.foodquart.microserviceuser.domain.model.AuthModel;
import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IJwtProviderPort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;

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
                .orElseThrow(() -> new NoDataFoundException("User not found"));

        if (!passwordEncoderPort.matches(authModel.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }

        return jwtProviderPort.generateToken(user);
    }
}
