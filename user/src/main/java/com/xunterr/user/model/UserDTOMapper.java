package com.xunterr.user.model;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
	@Override
	public UserDTO apply(User user) {
		return new UserDTO(
				user.getUsername(),
				user.getEmail(),
				user.getStreamKey());
	}
}