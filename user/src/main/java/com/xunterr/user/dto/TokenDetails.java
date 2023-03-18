package com.xunterr.user.dto;

import java.util.List;
import java.util.UUID;

public record TokenDetails(
		UUID id,
		List<String> authorities
) {}
