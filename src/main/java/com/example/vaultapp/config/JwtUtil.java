package com.example.vaultapp.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    // 🔥 SECRET KEY
    private static final String SECRET =
        "mysecretkeymysecretkeymysecretkey12345";

    private static final Key key =
        Keys.hmacShaKeyFor(
            SECRET.getBytes(
                StandardCharsets.UTF_8
            )
        );

    // 🔥 GENERATE TOKEN
    public static String generateToken(
        String username
    ) {

        return Jwts.builder()

            .subject(username)

            .issuedAt(new Date())

            .expiration(
                new Date(
                    System.currentTimeMillis()
                    + 86400000
                )
            )

            .signWith(key)

            .compact();
    }

    // 🔥 EXTRACT USERNAME
    public static String extractUsername(
        String token
    ) {

        Claims claims = Jwts.parser()

            .verifyWith((javax.crypto.SecretKey) key)

            .build()

            .parseSignedClaims(token)

            .getPayload();

        return claims.getSubject();
    }

    // 🔥 VALIDATE TOKEN
    public static boolean validateToken(
        String token
    ) {

        try {

            Jwts.parser()

                .verifyWith(
                    (javax.crypto.SecretKey) key
                )

                .build()

                .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;

        }

    }

}