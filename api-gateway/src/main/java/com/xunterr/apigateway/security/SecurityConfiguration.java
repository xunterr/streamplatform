package com.xunterr.apigateway.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@Slf4j
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfiguration {

	private final ReactiveAuthenticationManager authenticationManager;
	private final ServerSecurityContextRepository securityContextRepository;

	@Bean
	@Primary
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
		log.info("Filter chain has been initialized");
		http
				.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.authorizeExchange()
				.pathMatchers("user/api/v1/auth/**").permitAll()
				.pathMatchers("stream/api/v1/events/**").permitAll()
				.anyExchange().authenticated();

		return http.build();
	}

}