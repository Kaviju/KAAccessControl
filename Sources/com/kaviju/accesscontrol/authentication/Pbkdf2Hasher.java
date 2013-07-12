package com.kaviju.accesscontrol.authentication;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pbkdf2Hasher extends PasswordHasher {

	public static final String hasherCode = "PBKDF2";
	private static final String PBKDF2_JavaAlgoName = "PBKDF2WithHmacSHA1";
	private final int newKeyLength;
	private final int newKeyIterations;
	
	public Pbkdf2Hasher(int newKeyLength, int newKeyIterations) {
		this.newKeyLength = newKeyLength;
		this.newKeyIterations = newKeyIterations;
	}

	@Override
	public String hasherCode() {
		return hasherCode;
	}

	@Override
	public PasswordHash hashPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Cannot hash a null password.");
		}
		byte[] salt = createRandomSalt(newKeyLength);
		
		byte[] hashedPassword = hashString(password, salt, newKeyIterations, newKeyLength);
		
		return new PasswordHash(this, hashedPassword, salt, newKeyIterations);
	}

	@Override
	public boolean verifyPasswordWithHash(String password, PasswordHash hash) {		
		int keyLength = hash.hash.length;
		byte[] hashedPassword = hashString(password, hash.salt, hash.iterations, keyLength);
		
		PasswordHash passwordHash = new PasswordHash(this, hashedPassword, hash.salt, hash.iterations);
		return hash.hashEquals(passwordHash);
	}
	
	private byte[] hashString(String password, byte[] salt, int iterations, int keyLength)
	{
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_JavaAlgoName);
			return skf.generateSecret(spec).getEncoded();
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to hash password with Pbkdf2Hasher", e);
		}
	}
}
