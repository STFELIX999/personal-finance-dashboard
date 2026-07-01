package com.stevin.personalfinancedashboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(
            String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60))
                .signWith(
                        getSigningKey(),
                        Jwts.SIG.HS256)
                .compact();
    }
    public String extractUsername(
            String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
    public Date extractExpiration(
            String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration();
    }
    public boolean isTokenExpired(
            String token) {

        return extractExpiration(token)
                .before(new Date());
    }
    public boolean isTokenValid(
            String token,
            String email) {

        String username =
                extractUsername(token);

        return username.equals(email)
                && !isTokenExpired(token);
    }
}
