package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder
        encoder =
            new BCryptPasswordEncoder();

    // HASH PASSWORD
    public static String hash(
            String password) {

        return encoder.encode(password);
    }

    // VERIFY PASSWORD
    public static boolean matches(
            String rawPassword,
            String hashedPassword) {

        return encoder.matches(
            rawPassword,
            hashedPassword);
    }
}