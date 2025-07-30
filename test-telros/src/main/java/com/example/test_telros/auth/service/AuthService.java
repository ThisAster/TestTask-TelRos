package com.example.test_telros.auth.service;

import com.example.test_telros.auth.dto.RegisterRequestDTO;
import com.example.test_telros.auth.entity.Role;
import com.example.test_telros.auth.exception.EntityAlreadyExistsException;
import com.example.test_telros.auth.exception.EntityNotFoundException;
import com.example.test_telros.auth.repository.RoleRepository;
import com.example.test_telros.user.entity.User;
import com.example.test_telros.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User registerUser(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new EntityAlreadyExistsException("Username is already in use");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EntityAlreadyExistsException("Email is already in use");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .middleName(registerRequest.getMiddleName())
                .birthDate(registerRequest.getBirthDate())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Error: Role is not found."));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}
