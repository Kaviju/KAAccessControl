package com.kaviju.accesscontrol.authentication;

import java.security.SecureRandom;
import java.util.HashMap;

public abstract class PasswordHasher {
	private static final HashMap<String,PasswordHasher> hashers = new HashMap<String, PasswordHasher>();
	
	static {
		
	}
	
	public static void registerHasher(PasswordHasher hasher) {
		String hasherCode = hasher.hasherCode();
		if (hasherCode == null) {
			throw new IllegalArgumentException("Cannot register an hasher with null code.");
		}
		hashers.put(hasherCode, hasher);
	}
	
	public static PasswordHasher hasherWithCode(String code) {
		PasswordHasher hasher = hashers.get(code);
		if (hasher == null) {
			throw new IllegalArgumentException("Hasher with code "+code+" not found.");
		}
		return hasher;
 	}

	abstract public String hasherCode();
	
	abstract public PasswordHash hashPassword(String password);
	abstract public boolean verifyPasswordWithHash(String password, PasswordHash hash);
	abstract public boolean hasCreatedHash(PasswordHash hash);
		
	
	protected byte[] createRandomSalt(int saltLength) {
		SecureRandom randomGenerator = new SecureRandom();
		byte[] salt = new byte[saltLength];
		randomGenerator.nextBytes(salt);
		return salt;
	}
 }
