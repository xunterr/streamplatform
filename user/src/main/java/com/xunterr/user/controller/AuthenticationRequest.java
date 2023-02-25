package com.xunterr.user.controller;

public record AuthenticationRequest(
		String username,
		String password
) {}
