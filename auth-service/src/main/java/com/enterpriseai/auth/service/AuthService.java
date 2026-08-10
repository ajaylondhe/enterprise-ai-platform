package com.enterpriseai.auth.service;

import com.enterpriseai.auth.dto.AuthResponse;
import com.enterpriseai.auth.dto.LoginRequest;
import com.enterpriseai.auth.dto.RegisterRequest;
import com.enterpriseai.auth.entity.User;
import com.enterpriseai.auth.repository.UserRepository;
import com.enterpriseai.auth.security.JwtService;

import com.enterpriseai.common.exception.ResourceAlreadyExistsException;
import com.enterpriseai.common.exception.ResourceNotFoundException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(
            RegisterRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "User already exists with email: "
                            + request.getEmail()
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("USER");
        user.setActive(true);

        User savedUser =
                userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(
            LoginRequest request) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new BadCredentialsException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }

        if (!user.isActive()) {

            throw new BadCredentialsException(
                    "User account is inactive"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse getUserByEmail(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return toResponse(user);
    }

    private AuthResponse toResponse(
            User user) {

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}