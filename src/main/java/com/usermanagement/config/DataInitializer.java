package com.usermanagement.config;

import com.usermanagement.entity.Role;
import com.usermanagement.entity.User;
import com.usermanagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // ADMIN
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println(">>> Admin créé : admin@test.com / admin1234");
        }

        // USER
        if (!userRepository.existsByEmail("user@test.com")) {
            User user = new User();
            user.setName("User");
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("user1234"));
            user.setRole(Role.USER);
            user.setActive(true);
            userRepository.save(user);
            System.out.println(">>> User créé : user@test.com / user1234");
        }
    }
}