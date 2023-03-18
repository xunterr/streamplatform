package com.xunterr.user.controller;

import com.xunterr.user.dto.*;
import com.xunterr.user.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	AuthenticationService authenticationService;

	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO register(@RequestBody RegisterRequest request){
		return authenticationService.register(request);
	}

	@PostMapping("/authenticate")
	public AuthenticationResponse auth(@RequestBody AuthenticationRequest request){
		return authenticationService.authenticate(request);
	}
}
