package com.xunterr.apigateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


@Lazy
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		return validateToken(token)
				.bodyToMono(TokenDetails.class)
				.map(this::getAuthorities);
	}

	private UsernamePasswordAuthenticationToken getAuthorities(TokenDetails tokenDetails) {
		return new UsernamePasswordAuthenticationToken(
				tokenDetails.username(), null,
				tokenDetails.authorities().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList())
		);
	}

	private ResponseSpec validateToken(String token){
		log.info("Validating token: " + token);
		return WebClient.create("http://user:8080")
				.get()
				.uri((uriBuilder) -> uriBuilder.path("/api/v1/auth/token/validate").queryParam("token", token).build())
				.retrieve()
				.onStatus(
						httpStatusCode -> !httpStatusCode.equals(HttpStatus.OK),
						response -> Mono.error(new IllegalStateException("Token is not valid"))
				);
	}
}
