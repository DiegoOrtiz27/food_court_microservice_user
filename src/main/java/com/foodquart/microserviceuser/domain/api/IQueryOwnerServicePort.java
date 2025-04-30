package com.foodquart.microserviceuser.domain.api;

public interface IQueryOwnerServicePort {
    boolean isUserOwner(Long userId);
}
