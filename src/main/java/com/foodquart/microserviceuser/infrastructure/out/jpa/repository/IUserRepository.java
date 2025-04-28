package com.foodquart.microserviceuser.infrastructure.out.jpa.repository;

import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByDocumentId(String email);
}
