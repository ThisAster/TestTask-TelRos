package com.example.test_telros.user.service;

import com.example.test_telros.user.entity.User;
import com.example.test_telros.user.exception.EntityNotFoundException;
import com.example.test_telros.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    public User updateContactInfoUser(String login, User user) {
        User existingUser = userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        return userRepository.save(existingUser);
    }

    public User updateDetailsInfoUser(String login, User user) {
        User existingUser = userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }

        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }

        if (user.getMiddleName() != null) {
            existingUser.setMiddleName(user.getMiddleName());
        }

        if (user.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }

        if (user.getPhotoKey() != null) {
            existingUser.setPhotoKey(user.getPhotoKey());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userRepository.delete(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
