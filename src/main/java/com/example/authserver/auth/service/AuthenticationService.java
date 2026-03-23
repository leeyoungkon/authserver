package com.example.authserver.auth.service;

import com.example.authserver.auth.dto.LoginRequest;
import com.example.authserver.auth.entity.UserAccount;
import com.example.authserver.auth.jwt.JwtTokenProvider;
import com.example.authserver.auth.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticate(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.id() == null || loginRequest.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id and password are required");
        }

        UserAccount userAccount = userAccountRepository.findByLoginId(loginRequest.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.password(), userAccount.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return jwtTokenProvider.generateToken(userAccount.getLoginId());
    }
}
