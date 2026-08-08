package com.store.local_store.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtUtils {
    private final Date EXPIRATION_TIME = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

    public String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(EXPIRATION_TIME)
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .issuer(JWT_ISSUER)
                .compact();
    }

    public Claims validateToken(String bearerToken) {
        // this part also validates if the token expired
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(this.JWT_SECRET.getBytes()))
                .requireIssuer(this.JWT_ISSUER)
                .build()
                .parseSignedClaims(bearerToken)
                .getPayload();
    }

    public String getSubject(Claims claims) {
        return claims.get("sub", String.class);
    }
}
