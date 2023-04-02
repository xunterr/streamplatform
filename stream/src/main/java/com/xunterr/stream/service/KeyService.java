package com.xunterr.stream.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class KeyService {
	private final static String CIPHER = "AES";
	public String generateKey() {
		SecureRandom rand = new SecureRandom();
		KeyGenerator generator;
		try {
			generator = KeyGenerator.getInstance(CIPHER);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No such encryption algorithm");
		}
		generator.init(256, rand);
		Key key = generator.generateKey();
		return getBase64FromKey(key);
	}

	private String getBase64FromKey(Key key){
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	private Key getKeyFromBase64(String key){
		byte[] decoded = Base64.getDecoder().decode(key);
		return new SecretKeySpec(decoded, 0, decoded.length, CIPHER);
	}
}
