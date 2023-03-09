package com.xunterr.user.controller;

import com.xunterr.user.dto.AuthenticationRequest;
import com.xunterr.user.dto.AuthenticationResponse;
import com.xunterr.user.dto.RegisterRequest;
import com.xunterr.user.dto.TokenDetails;
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

	@GetMapping("/token/validate")
	public TokenDetails validateToken(@RequestParam String token){
		return authenticationService.isTokenValid(token);
	}

	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AuthenticationResponse register(@RequestBody RegisterRequest request){
		return new AuthenticationResponse(authenticationService.register(request));
	}

	@PostMapping("/authenticate")
	public AuthenticationResponse auth(@RequestBody AuthenticationRequest request){
		return new AuthenticationResponse(authenticationService.authenticate(request));
	}
}
