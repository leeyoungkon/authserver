package com.example.authserver.auth.controller;

import com.example.authserver.auth.dto.LoginRequest;
import com.example.authserver.auth.service.AuthenticationService;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Map<String, String>> validate(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader
    ) {
        String loginId = authenticationService.validateAccessToken(authorizationHeader);

        return ResponseEntity.ok()
                .header("X-Authenticated-User", loginId)
                .body(Map.of("valid", "true", "loginId", loginId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody LoginRequest registerRequest) {
        authenticationService.register(registerRequest);
    }
}
