package com.foodquart.microserviceuser.infrastructure.out.jpa.repository;

import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByDocumentId(String email);

    Optional<UserEntity> findByEmail(String email);
}
