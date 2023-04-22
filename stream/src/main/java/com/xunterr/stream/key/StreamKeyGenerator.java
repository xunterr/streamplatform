package com.xunterr.stream.key;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public interface StreamKeyGenerator {
	String generateKey();
}
