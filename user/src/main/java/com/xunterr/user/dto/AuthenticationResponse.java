package com.xunterr.user.dto;

import java.util.UUID;

public record AuthenticationResponse(
		UUID id,
		String token
) {}
