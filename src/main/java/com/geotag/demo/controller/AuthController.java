
package com.geotag.demo.controller;

import com.geotag.demo.entity.User;
import com.geotag.demo.repository.UserRepository;
import com.geotag.demo.service.EmailService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (request.name() == null || request.name().isBlank()
                || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("All fields are required");
        }

        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }

        User user = new User(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(
                user.getEmail(),
                user.getName()
        );

        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElse(null);

        if (user == null ||
                !passwordEncoder.matches(
                        request.password(),
                        user.getPassword())) {

            return ResponseEntity.badRequest()
                    .body("Invalid email or password");
        }

        // Send login notification email
        emailService.sendLoginNotification(
                user.getEmail(),
                user.getName()
        );

        return ResponseEntity.ok("Login successful!");
    }

    public record RegisterRequest(
            String name,
            String email,
            String password
    ) {}

    public record LoginRequest(
            String email,
            String password
    ) {}
}
