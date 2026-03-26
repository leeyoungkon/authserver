package com.example.authserver.auth.controller;

import com.example.authserver.auth.dto.LoginRequest;
import com.example.authserver.auth.dto.LoginResponse;
import com.example.authserver.auth.service.AuthenticationService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

 /*    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.authenticate(loginRequest);
        return new LoginResponse("Bearer", token);
    }  */

     @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
    String token = authenticationService.authenticate(request);
    return ResponseEntity.ok(Map.of("token", token));
    }   

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody LoginRequest registerRequest) {
        authenticationService.register(registerRequest);
    }
}
