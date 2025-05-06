package com.foodquart.microserviceuser.infrastructure.configuration;

import com.foodquart.microserviceuser.domain.api.IAuthServicePort;
import com.foodquart.microserviceuser.domain.api.IUserServicePort;
import com.foodquart.microserviceuser.domain.spi.IJwtProviderPort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.usecase.AuthUseCase;
import com.foodquart.microserviceuser.domain.usecase.UserUseCase;
import com.foodquart.microserviceuser.infrastructure.out.client.adapter.PasswordEncoderAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IJwtProviderPort jwtProviderPort;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(
                userRepository,
                userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(
                userPersistencePort(),
                passwordEncoderPort());
    }

    @Bean
    public IAuthServicePort authServicePort() {
        return new AuthUseCase(
                userPersistencePort(),
                passwordEncoderPort(),
                jwtProviderPort);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

}