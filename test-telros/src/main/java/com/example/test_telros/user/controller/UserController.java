package com.example.test_telros.user.controller;

import com.example.test_telros.photo.service.PhotoStorageService;
import com.example.test_telros.user.mapper.UserMapper;
import com.example.test_telros.user.dto.UserContactDTO;
import com.example.test_telros.user.dto.UserDetailsDTO;
import com.example.test_telros.user.entity.User;
import com.example.test_telros.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PhotoStorageService photoStorageService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/contact")
    public ResponseEntity<UserContactDTO> getCurrentUserContact() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUserByLogin(username);

        return ResponseEntity.ok(userMapper.toContactDto(user));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/details")
    public ResponseEntity<UserDetailsDTO> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUserByLogin(username);

        return ResponseEntity.ok(userMapper.toDetailsDto(user, photoStorageService));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}/contact")
    public ResponseEntity<UserContactDTO> getUserContactInfo(@PathVariable String username) {
        User user = userService.getUserByLogin(username);
        UserContactDTO userContactDTO = userMapper.toContactDto(user);
        return ResponseEntity.ok(userContactDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}/details")
    public ResponseEntity<UserDetailsDTO> getUserDetailsInfo(@PathVariable String username) {
        User user = userService.getUserByLogin(username);
        UserDetailsDTO userDetailsDTO = userMapper.toDetailsDto(user, photoStorageService);
        return ResponseEntity.ok(userDetailsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{username}/contact")
    public ResponseEntity<UserContactDTO> updateContactInfoUser(@PathVariable String username, @RequestBody @Validated UserContactDTO userContactDTO) {
        User user = userService.getUserByLogin(username);
        User updatedUser = userService.updateContactInfoUser(user.getUsername(), userMapper.toUser(userContactDTO));
        return ResponseEntity.ok(userMapper.toContactDto(updatedUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{username}/details")
    public ResponseEntity<UserDetailsDTO> updateUserDetailsInfo(@PathVariable String username, @RequestBody @Validated UserDetailsDTO userDetailsDTO) {
        User user = userService.getUserByLogin(username);
        User updatedUser = userService.updateDetailsInfoUser(user.getUsername(), userMapper.toUser(userDetailsDTO));
        return ResponseEntity.ok(userMapper.toDetailsDto(updatedUser, photoStorageService));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        User user = userService.getUserByLogin(username);
        userService.deleteUserByUsername(user.getUsername());
        return ResponseEntity.ok("User with username: " + user.getUsername() + " deleted.");
    }
}
