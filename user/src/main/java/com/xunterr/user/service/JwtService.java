package com.xunterr.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

	@Value("${app.security.jwt.secret}")
	private String secretKey;

	public boolean isTokenValid(String jwtToken, UserDetails user){
		String username = getUsername(jwtToken);
		return username.equals(user.getUsername()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken) {
		return getClaim(jwtToken, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
	}

	public String generateToken(Map<String, Object> claims, UserDetails user){
		System.out.println("generate token");
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 60 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String getUsername(String jwtToken){
		return getClaim(jwtToken, Claims::getSubject);
	}

	private <T> T getClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		Claims claims = getClaims(jwtToken);
		log.info(claims.getSubject());
		return claimsResolver.apply(claims);
	}

	private Claims getClaims(String jwtToken){
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	private Key getSigningKey() {
		byte[] key = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);
	}
}
