package com.recody.recodybackend.users.features.jwt;

public interface JwtManager {
    
    String createAccessToken(CreateAccessToken request);
    
    String createRefreshToken(CreateRefreshToken request);
    
    String getExpireTimeFromToken(String token);
    
    String resolveUsername(String token);
    
    Long resolveUserId(String token);
    
    /*
    * 이 메서드를 수행하고 실패하면 JwtException 클래스 상속 예외들이 던져진다. */
    boolean validateToken(String token);
    
    boolean isExpired(String token);
}
