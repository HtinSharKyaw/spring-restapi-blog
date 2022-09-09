package com.example.springblogrestapi.security;

import com.example.springblogrestapi.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    //get username from the token
    public String getUserNameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT Signature");
        }catch (MalformedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }catch (ExpiredJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        }catch (UnsupportedJwtException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
        }catch (IllegalArgumentException e){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"JWT Claims string is empty");
        }
    }
}
