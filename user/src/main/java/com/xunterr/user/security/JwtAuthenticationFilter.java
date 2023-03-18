package com.xunterr.user.security;

import com.xunterr.user.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String jwt;
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwt = authHeader.substring(7);
		String sub = jwtService.getClaim(jwt, Claims::getSubject);
		List<SimpleGrantedAuthority> authorities = Stream.of(jwtService.getClaim(jwt, (claims) -> claims.get("authorities")).toString())
				.map(SimpleGrantedAuthority::new)
				.toList();
		if(sub != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					sub,
					null,
					authorities
			);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}
}
