package com.recody.recodybackend.commonbootutils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Slf4j
@Component
class DefaultJwtManager implements JwtManager {
    
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;
    private static final String USER_ID_NAME = "userId";
    
    private final Key secretKey;
    
    public DefaultJwtManager(@Value("${users.jwt.secret}") String secret,
                             @Value("${users.jwt.access-token.expiration-time}") long accessTokenExpirationTime,
                             @Value("${users.jwt.refresh-token.expiration-time}") long refreshTokenExpirationTime) {
        log.debug("DefaultJwtManager initiating with secret");
        if (secret.length() != 4) {
            throw new IllegalArgumentException("the secret key string must be length of 4");
        }
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpirationTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpirationTime;
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM.getJcaName());
    }
    
    @Override
    public String createAccessToken(CreateAccessToken command){
        Objects.requireNonNull(command.getEmail(), "email is required");
        String token = doCreateToken(command.getEmail(), command.getUserId(), ACCESS_TOKEN_EXPIRE_TIME);
        log.debug("created access token");
        return token;
    }
    
    @Override
    public String createRefreshToken(CreateRefreshToken command){
        Objects.requireNonNull(command.getEmail(), "email is required");
        String token = doCreateToken(command.getEmail(), command.getUserId(), REFRESH_TOKEN_EXPIRE_TIME);
        log.debug("created refresh token");
        return token;
    }
    
    private String doCreateToken(String email, Long userId, long expirationTime) {
        return Jwts.builder().setSubject(email).claim("userId", userId)
                   .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(ALGORITHM, secretKey).compact();
    }
    
    
    @Override
    public String getExpireTimeFromToken(String token) {
        Objects.requireNonNull(token, "token is required");
        String expirationTime = expirationTimeOf(token).toString();
        log.debug("resolved token expirationTime: {}", expirationTime);
        return expirationTime;
    }
    
    
    /*
    * 토큰을 검증한다.
     * 토큰이 파싱이 가능하면 true 를 반환한다.
     * Expected Exceptions
     *   JwtException 클래스 상속 예외들
     * */
    @Override
    public boolean validateToken(String token) {
        boolean isValid = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token) != null;
        log.debug("validated Token: {}", isValid);
        return isValid;
    }
    
    
    @Override
    public String resolveUsername(String token) {
        Objects.requireNonNull(token);
        String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        log.debug("resolved subject");
        return subject;
    }
    
    
    @Override
    public Long resolveUserId(String token) {
        Long userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(USER_ID_NAME, Long.class);
        log.debug("resolved userId");
        return userId;
    }
    

    @Override
    public boolean isExpired(String token) {
        return !expirationTimeOf(token).isBefore(now());
    }
    
    
    private ZonedDateTime expirationTimeOf(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .toInstant()
                .atZone(TimeZone.getDefault().toZoneId());
    }
    
    
    private ZonedDateTime now() {
        return Instant.now().atZone(TimeZone.getDefault().toZoneId());
    }
}
