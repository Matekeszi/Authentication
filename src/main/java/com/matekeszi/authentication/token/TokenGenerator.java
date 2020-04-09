package com.matekeszi.authentication.token;

import com.matekeszi.authentication.domain.TokenEntity;
import com.matekeszi.authentication.repository.TokenRepository;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenGenerator {
    public static String getUserNameFromToken(String token){
        return token.split(":")[0];
    }
    public static String createToken(UserDetails userDetails){
        StringBuffer token = new StringBuffer(userDetails.getUsername());
        token.append(":").append(System.currentTimeMillis()+(1000*60*60));
        return token.toString();
    }

    public static boolean validateToken(String authToken, TokenRepository tokenRepository) {
        final TokenEntity tokenEntity = tokenRepository.findByToken(authToken);
        if(tokenEntity == null) throw new SecurityException("Token not found!");
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        return expires >= System.currentTimeMillis() && tokenEntity.getToken().equals(authToken);
    }

    public static TokenEntity createTokenEntity(String token){
        String username = getUserNameFromToken(token);
        String[] parts = token.split(":");
        long expires = Long.parseLong(parts[1]);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setExpiration(expires);
        tokenEntity.setToken(token);
        tokenEntity.setUsername(username);
        return tokenEntity;
    }
}
