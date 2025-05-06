package com.foodquart.microserviceuser.domain.util;

import java.util.Set;

public class SecurityMessages {
    public static final String MISSING_AUTH_HEADER = "Missing or invalid Authorization header";
    public static final String INVALID_EXPIRED_TOKEN = "Invalid or expired token";

    public static final Set<String> PUBLIC_PATHS = Set.of(
            "/api/v1/auth/login",
            "/api/v1/user/createCustomer",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    );

    private SecurityMessages() {
        throw new AssertionError("Utility class should not be instantiated");
    }
}
