package com.zorvyn.finance;

import com.zorvyn.finance.model.User;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public DataSeeder(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!repo.existsByEmail("admin@zorvyn.com")) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@zorvyn.com")
                    .password(encoder.encode("Admin@123"))
                    .role("ADMIN")
                    .phone("9999999999")
                    .status("ACTIVE")
                    .build();
            repo.save(admin);
            System.out.println("Default admin created.");
        }
    }

}

