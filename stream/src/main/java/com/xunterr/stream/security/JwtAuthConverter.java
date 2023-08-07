package com.xunterr.stream.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

	@Value("${jwt.auth.converter.resourceName}")
	private String resourceName;


	@Override
	public AbstractAuthenticationToken convert(@NonNull Jwt source) {
		Collection<GrantedAuthority> authorities = Stream.concat(
				jwtGrantedAuthoritiesConverter.convert(source).stream(),
				getResourceRoles(source).stream()
		).collect(Collectors.toSet());
		return new JwtAuthenticationToken(
				source,
				authorities,
				source.getClaim(JwtClaimNames.SUB)
		);
	}

	private Collection<? extends GrantedAuthority> getResourceRoles(Jwt jwt){
		Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
		if(resourceAccess == null){
			return Set.of();
		}

		Object resourceObj = resourceAccess.get(resourceName);
		if(resourceObj == null)
			return Set.of();

		var resource = (Map<String, Object>) resourceObj;
		var roles = (Collection<String>) resource.get("roles");

		return roles.stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r))
				.collect(Collectors.toSet());
	}
}
