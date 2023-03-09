package com.xunterr.apigateway.security;

import java.util.List;

public record TokenDetails(
		String username,
		List<String> authorities
) {}
