package com.xunterr.stream.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.NonNull;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Value("${spring.security.jwt.secret}")
	private String secretKey;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		Authentication authentication = getAuthentication(request);
		if(authentication != null){
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private Authentication getAuthentication(HttpServletRequest request){
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authHeader == null || !authHeader.startsWith("Bearer ")){
			return null;
		}
		Claims tokenDetails = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
				.build()
				.parseClaimsJws(authHeader.replace("Bearer ", ""))
				.getBody();

		if(tokenDetails.getSubject() != null || tokenDetails.containsKey("authorities")){
			List<SimpleGrantedAuthority> authorities = Stream.of(tokenDetails.get("authorities"))
					.map(a -> new SimpleGrantedAuthority(a.toString()))
					.toList();

			return new UsernamePasswordAuthenticationToken(
					tokenDetails.getSubject(),
					null,
					authorities
			);
		}
		return null;
	}
}
