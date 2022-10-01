package com.recody.recodybackend.commonbootutils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Objects;

@Component(value = "SuperJwtManager")
@Slf4j
public class DefaultSuperJwtManager{
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final String USER_ID_NAME = "userId";
    
    private final Key secretKey;
    
    public DefaultSuperJwtManager(@Value("${users.jwt.secret}") String secret) {
        log.debug("DefaultJwtManager initiating with secret");
        if (secret.length() != 4) {
            throw new IllegalArgumentException("the secret key string must be length of 4");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM.getJcaName());
    }
    
    public String createAccessToken(CreateAccessToken command) {
        Objects.requireNonNull(command.getEmail(), "email is required");
        String token = doCreateToken(command.getEmail(), command.getUserId());
        log.debug("created access token");
        return token;
    }
    
    private String doCreateToken(String email, Long userId) {
        return Jwts.builder()
                   .setSubject(email)
                   .claim(USER_ID_NAME, userId)
                   .setExpiration(null)
                   .signWith(ALGORITHM, secretKey).compact();
    }
    
    public String createRefreshToken(CreateRefreshToken command) {
        Objects.requireNonNull(command.getEmail(), "email is required");
        String token = doCreateToken(command.getEmail(), command.getUserId());
        log.debug("created refresh token");
        return token;
    }
}
