package com.xunterr.user.controller;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
		@NotBlank(message = "username cannot be blank")
		String username,
		@NotBlank(message = "email cannot be blank")
		String email,
		@NotBlank(message = "password cannot be blank")
		String password
) {}
