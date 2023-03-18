package com.xunterr.user.service;

import com.xunterr.user.dto.*;
import com.xunterr.user.exception.AlreadyTakenException;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.model.Role;
import com.xunterr.user.model.User;
import com.xunterr.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {
	@Autowired
	UserRepository repository;
	UserDTOMapper userDTOMapper;
	PasswordEncoder passwordEncoder;
	JwtService jwtService;
	AuthenticationManager authenticationManager;

	public UserDTO register(RegisterRequest request) {
		if(repository.findByUsername(request.username()).isPresent()){
			throw new AlreadyTakenException("Username already taken");
		}
		if(repository.findByEmail(request.email()).isPresent()){
			throw new AlreadyTakenException("Email already taken");
		}
		User user = User.builder()
				.username(request.username())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.role(Role.USER)
				.build();
		repository.saveAndFlush(user);

		return new UserDTO(user);
 	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);
		User user = repository.findByUsername(request.username())
				.orElseThrow(()->new EntityNotFoundException(request.username(), "User Not Found"));
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", "ROLE_USER");
		return new AuthenticationResponse(user.getId(), jwtService.generateToken(claims, user));
	}

	public TokenDetails isTokenValid(String token){
		String username = jwtService.getUsername(token);
		User user = repository.findByUsername(username)
				.orElseThrow(()-> new EntityNotFoundException(jwtService.getUsername(token), "User Not Found"));

		if(!jwtService.isTokenValid(token, user)){
			throw new IllegalStateException("Token is not valid");
		}
		List<String> authorities = new ArrayList<>();
		authorities.add(jwtService.getClaims(token).get("authorities").toString());
		return new TokenDetails(user.getId(), authorities);
	}
}
