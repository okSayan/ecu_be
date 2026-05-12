package com.example.demo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET =
        "myverysecretkeymyverysecretkey123456789012345678901234567890";

    private static final Key KEY =
        Keys.hmacShaKeyFor(
            SECRET.getBytes());

    // GENERATE TOKEN
    public static String generateToken(
            String username) {

        return Jwts.builder()

            .setSubject(username)

            .setIssuedAt(new Date())

            .setExpiration(
                new Date(
                    System.currentTimeMillis()
                    + 86400000
                )
            )

            .signWith(KEY)

            .compact();
    }

    // EXTRACT USERNAME
    public static String extractUsername(
            String token) {

        return Jwts.parserBuilder()

            .setSigningKey(KEY)

            .build()

            .parseClaimsJws(token)

            .getBody()

            .getSubject();
    }
}