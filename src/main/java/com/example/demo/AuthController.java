package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    // register
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }

        // hash pass
        user.setPassword(PasswordUtil.hash(user.getPassword()));
        repo.save(user);
        return "User registered successfully";
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User user) {

        User existing = repo.findByUsername(
                user.getUsername());

        if (existing == null) {

            return ResponseEntity
                    .status(401)
                    .body("User not found");
        }

        if (!PasswordUtil.matches(
                user.getPassword(),
                existing.getPassword())) {

            return ResponseEntity
                    .status(401)
                    .body("Wrong password");
        }

        String token = JwtUtil.generateToken(
                existing.getUsername());

        return ResponseEntity.ok(token);
    }
}