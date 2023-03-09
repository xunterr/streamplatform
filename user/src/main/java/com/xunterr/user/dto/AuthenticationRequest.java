package com.xunterr.user.dto;

public record AuthenticationRequest(
		String username,
		String password
) {}
