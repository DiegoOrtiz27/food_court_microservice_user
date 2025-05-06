package com.foodquart.microserviceuser.infrastructure.out.client.adapter;

import com.foodquart.microserviceuser.domain.model.UserModel;
import com.foodquart.microserviceuser.domain.spi.IJwtProviderPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProviderAdapter implements IJwtProviderPort {

    private final Key key;
    private final long expiration;

    public JwtProviderAdapter(
            @Value("${security.jwt.secret}") String secretKey,
            @Value("${security.jwt.expiration}") long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expiration = expiration;
    }

    @Override
    public String generateToken(UserModel user) {
        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        claims.put("role", user.getRole().name());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Long getIdFromToken(String token) {
        return Long.valueOf(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());
    }

    @Override
    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }
}
