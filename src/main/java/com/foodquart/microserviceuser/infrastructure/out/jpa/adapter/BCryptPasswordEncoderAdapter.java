package com.foodquart.microserviceuser.infrastructure.out.jpa.adapter;

import com.foodquart.microserviceuser.domain.spi.IPasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements IPasswordEncoderPort {

    private final BCryptPasswordEncoder encoder;

    public BCryptPasswordEncoderAdapter() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
