package com.xunterr.user.service;

import com.xunterr.user.model.User;
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

	@Value("${spring.security.jwt.secret}")
	private String secretKey;

	public String generateToken(Map<String, Object> claims, User user){
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public <T> T getClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		Claims claims = getClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	public Claims getClaims(String jwtToken){
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
