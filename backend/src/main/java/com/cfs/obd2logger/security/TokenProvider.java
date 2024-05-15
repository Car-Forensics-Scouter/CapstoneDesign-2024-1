package com.cfs.obd2logger.security;

import com.cfs.obd2logger.config.JwtProperties;
import com.cfs.obd2logger.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

@Component
public class TokenProvider {
    @Autowired
    JwtProperties jwtProperties;
    public String create(UserEntity userEntity) {
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
            .setSubject(String.valueOf(userEntity.getId()))
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}