package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;

import org.junit.*;

public class Pbkdf2HasherTest {
	private static final int keyLength = 24;
	private static final int nbIterations = 1000;
	static private final String testPassword = "password";
	static private final String testWrongPassword = "passworD";

	static private Pbkdf2Hasher testHasher;

	@BeforeClass
	static public void initPasswordHasher() {
		testHasher = new Pbkdf2Hasher(keyLength, nbIterations);
		PasswordHasher.registerHasher(testHasher);
	}

	@Test
	public void getFromPasswordHasher() {
		assertEquals(testHasher, PasswordHasher.hasherWithCode(testHasher.hasherCode));
	}

	@Test(expected=IllegalArgumentException.class)
	public void hashPasswordWithNullPasswordShouldThrow() {
		testHasher.hashPassword(null);
	}
	
	@Test
	public void hashedPasswordShouldBeginWithHasherCode() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		String hashString = hashedPassword.toString();
		
		assertTrue(hashString.startsWith(testHasher.hasherCode()));
	}

	@Test
	public void twoHashWithSamePasswordAreDifferent() {
		PasswordHash hashedPassword1 = testHasher.hashPassword(testPassword);
		PasswordHash hashedPassword2 = testHasher.hashPassword(testPassword);
		
		assertFalse(hashedPassword1.toString().equals(hashedPassword2.toString()));
	}
	
	@Test
	public void validateGoodPasswordShouldReturnTrue() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertTrue(testHasher.verifyPasswordWithHash(testPassword, hashedPassword));
	}

	@Test
	public void validateWrongPasswordShouldReturnFalse() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertFalse(testHasher.verifyPasswordWithHash(testWrongPassword, hashedPassword));
	}

	@Test(expected=RuntimeException.class)
	public void hashPasswordWithWrongParamsThrow() {
		Pbkdf2Hasher testHasherWithWrongParams = new Pbkdf2Hasher(keyLength, 0);
		
		testHasherWithWrongParams.hashPassword(testPassword);
	}
}
