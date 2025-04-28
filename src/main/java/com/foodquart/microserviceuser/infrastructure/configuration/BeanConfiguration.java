package com.foodquart.microserviceuser.infrastructure.configuration;

import com.foodquart.microserviceuser.domain.api.IOwnerServicePort;
import com.foodquart.microserviceuser.domain.spi.IOwnerPersistencePort;
import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import com.foodquart.microserviceuser.domain.spi.IUserPersistencePort;
import com.foodquart.microserviceuser.domain.usecase.CreateOwnerUseCase;
import com.foodquart.microserviceuser.infrastructure.out.jpa.adapter.OwnerJpaAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IOwnerEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IOwnerRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IRoleRepository;
import com.foodquart.microserviceuser.infrastructure.out.jpa.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUserPersistencePort userPersistencePort(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            IUserEntityMapper userEntityMapper
    ) {
        return new UserJpaAdapter(userRepository, roleRepository, userEntityMapper);
    }


    @Bean
    public IOwnerPersistencePort ownerPersistencePort(
            IOwnerRepository ownerRepository,
            IOwnerEntityMapper ownerEntityMapper,
            IUserRepository userRepository
    ) {
        return new OwnerJpaAdapter(ownerRepository, ownerEntityMapper, userRepository);
    }

    @Bean
    public IOwnerServicePort ownerServicePort(
            IOwnerPersistencePort ownerPersistencePort,
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort
    ) {
        return new CreateOwnerUseCase(ownerPersistencePort, userPersistencePort, passwordEncoderPort);
    }
}