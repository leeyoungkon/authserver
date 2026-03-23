package com.example.authserver.auth.config;

import com.example.authserver.auth.entity.UserAccount;
import com.example.authserver.auth.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new UserAccount("user1", passwordEncoder.encode("pass1")));
                repository.save(new UserAccount("admin", passwordEncoder.encode("admin1234")));
            }
        };
    }
}
