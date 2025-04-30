package com.foodquart.microserviceuser.infrastructure.configuration;

import com.foodquart.microserviceuser.domain.api.IOwnerServicePort;
import com.foodquart.microserviceuser.domain.api.IQueryOwnerServicePort;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.usecase.CreateOwnerUseCase;
import com.foodquart.microserviceuser.domain.usecase.QueryOwnerUseCase;
import com.foodquart.microserviceuser.infrastructure.out.client.adapter.PasswordEncoderAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.adapter.OwnerJpaAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IOwnerEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IOwnerRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IRoleRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IOwnerRepository ownerRepository;
    private final IOwnerEntityMapper ownerEntityMapper;
    private final IUserEntityMapper userEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, roleRepository, userEntityMapper);
    }

    @Bean
    public IOwnerPersistencePort ownerPersistencePort() {
        return new OwnerJpaAdapter(ownerRepository, ownerEntityMapper, userRepository);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

    @Bean
    public IOwnerServicePort ownerServicePort() {
        return new CreateOwnerUseCase(ownerPersistencePort(), userPersistencePort(), passwordEncoderPort());
    }

    @Bean
    public IQueryOwnerServicePort queryOwnerServicePort() {
        return  new QueryOwnerUseCase(ownerPersistencePort());
    }
}