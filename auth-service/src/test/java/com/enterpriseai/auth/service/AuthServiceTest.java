package com.enterpriseai.auth.service;

import com.enterpriseai.auth.dto.AuthResponse;
import com.enterpriseai.auth.dto.RegisterRequest;
import com.enterpriseai.auth.entity.User;
import com.enterpriseai.auth.repository.UserRepository;
import com.enterpriseai.auth.security.JwtService;
import com.enterpriseai.common.exception.ResourceAlreadyExistsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request =
                new RegisterRequest();

        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("Password@123");

        when(userRepository.existsByEmail(
                "john@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode(
                "Password@123"))
                .thenReturn("encoded-password");

        User savedUser = new User();

        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword("encoded-password");
        savedUser.setRole("USER");
        savedUser.setActive(true);

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        AuthResponse response =
                authService.register(request);

        assertNotNull(response);

        assertEquals(
                "John Doe",
                response.getName()
        );

        assertEquals(
                "john@example.com",
                response.getEmail()
        );

        assertEquals(
                "USER",
                response.getRole()
        );

        verify(userRepository)
                .existsByEmail("john@example.com");

        verify(passwordEncoder)
                .encode("Password@123");

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {

        RegisterRequest request =
                new RegisterRequest();

        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("Password@123");

        when(userRepository.existsByEmail(
                "john@example.com"))
                .thenReturn(true);

        assertThrows(
                ResourceAlreadyExistsException.class,
                () -> authService.register(request)
        );

        verify(userRepository, never())
                .save(any(User.class));

        verify(passwordEncoder, never())
                .encode(any());

        verifyNoInteractions(jwtService);
    }
}