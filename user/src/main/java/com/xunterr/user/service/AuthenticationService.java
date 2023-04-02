package com.xunterr.user.service;

import com.xunterr.user.dto.AuthenticationRequest;
import com.xunterr.user.dto.AuthenticationResponse;
import com.xunterr.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);
		User user = (User) userDetailsService.loadUserByUsername(request.username());	//strange place
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", user.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList());
		return new AuthenticationResponse(user.getId(), jwtService.generateToken(claims, user));
	}
}
