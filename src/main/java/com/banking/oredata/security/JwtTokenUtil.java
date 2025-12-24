package com.banking.oredata.security;


import com.banking.oredata.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
    private final Key key;
    private static final long jwtExpirationMs = 6000000;

    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor("JWT utility initialized with expiration".getBytes());
        log.info("JWT utility initialized with expiration: {} ms", jwtExpirationMs);
    }

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String token = Jwts.builder()
            .setId(userDetails.getUserId().toString())
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        log.debug("Generated JWT token for user: {}", userDetails.getUsername());
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = parseClaims(token);

            Date expiration = claims.getBody().getExpiration();
            if (expiration.before(new Date())) {
                log.warn("Token has expired");
                return false;
            }

            log.debug("Token validation successful");
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT token is unsupported");
        } catch (MalformedJwtException e) {
            log.warn("JWT token is malformed: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT token is malformed");
        } catch (SecurityException e) {
            log.warn("JWT signature validation failed: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT signature validation failed");
        } catch (IllegalArgumentException e) {
            log.warn("JWT token compact of handler are invalid: {}", e.getMessage());
            throw new JwtAuthenticationException("JWT token compact of handler are invalid");
        } catch (Exception e) {
            log.error("Unexpected error during token validation: {}", e.getMessage());
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
