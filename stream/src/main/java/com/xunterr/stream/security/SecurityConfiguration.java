package com.xunterr.stream.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.UnauthorizedEntryPoint;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorCodeMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorMessageMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.HttpStatusMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthConverter jwtAuthConverter;

	@Bean
	public UnauthorizedEntryPoint unauthorizedEntryPoint(HttpStatusMapper httpStatusMapper, ErrorCodeMapper errorCodeMapper,
														 ErrorMessageMapper errorMessageMapper, ObjectMapper objectMapper) {

		return new UnauthorizedEntryPoint(httpStatusMapper, errorCodeMapper, errorMessageMapper, objectMapper);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, UnauthorizedEntryPoint unauthorizedEntryPoint) throws Exception{
		http
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/api/v1/events/**").permitAll()
				.anyRequest().authenticated();

		http
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedEntryPoint);	//default auth entry point for Spring Security

		http
				.oauth2ResourceServer()
				.jwt(Customizer.withDefaults())
				.authenticationEntryPoint(unauthorizedEntryPoint); //for AuthenticationFailureHandler

		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);


		return http.build();
	}

}