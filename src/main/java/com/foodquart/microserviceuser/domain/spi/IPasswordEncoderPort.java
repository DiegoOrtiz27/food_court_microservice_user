package com.foodquart.microserviceuser.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
