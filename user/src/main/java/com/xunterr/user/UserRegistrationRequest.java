package com.xunterr.user;

public record UserRegistrationRequest(
        String username,
        String email,
        String password
) {
}
