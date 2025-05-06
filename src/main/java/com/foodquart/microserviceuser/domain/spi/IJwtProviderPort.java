package com.foodquart.microserviceuser.domain.spi;

import com.foodquart.microserviceuser.domain.model.UserModel;

public interface IJwtProviderPort {
    String generateToken(UserModel user);
    boolean validateToken(String token);
    Long getIdFromToken(String token);
    String getRoleFromToken(String token);
}
