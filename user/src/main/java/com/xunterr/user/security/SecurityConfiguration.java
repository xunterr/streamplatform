package com.xunterr.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunterr.user.exception.EntityNotFoundException;
import com.xunterr.user.repository.UserRepository;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.UnauthorizedEntryPoint;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorCodeMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorMessageMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.HttpStatusMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

	private JwtAuthenticationFilter jwtFilter;
	private AuthenticationProvider authenticationProvider;

	@Bean
	public UnauthorizedEntryPoint unauthorizedEntryPoint(HttpStatusMapper httpStatusMapper,
														 ErrorCodeMapper errorCodeMapper,
														 ErrorMessageMapper errorMessageMapper,
														 ObjectMapper objectMapper) {
		return new UnauthorizedEntryPoint(httpStatusMapper, errorCodeMapper, errorMessageMapper, objectMapper);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, UnauthorizedEntryPoint unauthorizedEntryPoint) throws Exception{

		http
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/api/v1/auth/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);

		return http.build();
	}

}
