package com.xunterr.user.dto;

import java.util.List;

public record TokenDetails(
		String username,
		List<String> authorities
) {}
