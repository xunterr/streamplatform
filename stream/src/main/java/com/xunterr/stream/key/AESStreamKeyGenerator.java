package com.xunterr.stream.key;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class AESStreamKeyGenerator implements StreamKeyGenerator {
	private final static String CIPHER = "AES";

	@Override
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

	public String getBase64FromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

}
