package com.xunterr.user.controller;

import com.xunterr.user.dto.*;
import com.xunterr.user.service.AuthenticationService;
import com.xunterr.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	private final AuthenticationService authenticationService;
	private final UserService userService;
	private final UserDTOMapper mapper;

	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO register(@RequestBody RegisterRequest request){
		return mapper.toDto(userService.create(request));
	}

	@PostMapping("/authenticate")
	public AuthenticationResponse auth(@RequestBody AuthenticationRequest request){
		return authenticationService.authenticate(request);
	}
}
