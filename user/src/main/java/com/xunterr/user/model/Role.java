package com.xunterr.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	private final String roleName;

}
