package com.example.authserver.auth.dto;

public record LoginResponse(String tokenType, String accessToken) {
}
