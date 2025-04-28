package com.foodquart.microserviceuser.infrastructure.out.jpa.repository;

import com.foodquart.microserviceuser.infrastructure.out.jpa.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOwnerRepository extends JpaRepository<OwnerEntity, Long> {
}
