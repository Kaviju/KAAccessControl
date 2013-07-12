package com.kaviju.accesscontrol.authentication;

import java.security.MessageDigest;

public class LatinSimpleMD5Hasher extends PasswordHasher {

	public static final String hasherCode = "LatinSimpleMD5";

	@Override
	public String hasherCode() {
		return hasherCode;
	}

	@Override
	public PasswordHash hashPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Cannot hash a null password.");
		}
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(password.getBytes("ISO-8859-1"));
			byte[] hash = m.digest();

			return new PasswordHash(this, hash);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to hash password with LatinSimpleMD5Hasher", e);
		}
	}

	@Override
	public boolean verifyPasswordWithHash(String password, PasswordHash hash) {
		PasswordHash passwordHash = hashPassword(password);
		return hash.hashEquals(passwordHash);
	}
}
