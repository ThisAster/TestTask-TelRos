package com.example.test_telros.db;

import com.example.test_telros.auth.entity.Role;
import com.example.test_telros.auth.repository.RoleRepository;
import com.example.test_telros.user.entity.User;
import com.example.test_telros.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        Role roleUser = roleRepository.save(Role.builder().name("ROLE_USER").build());

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@example.com")
                .firstName("Admin")
                .lastName("User")
                .birthDate(LocalDate.of(1990, 1, 1))
                .roles(Set.of(roleAdmin, roleUser))
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .email("user@example.com")
                .firstName("Normal")
                .lastName("User")
                .birthDate(LocalDate.of(1995, 5, 5))
                .roles(Set.of(roleUser))
                .build();
        userRepository.save(user);

    }
}
