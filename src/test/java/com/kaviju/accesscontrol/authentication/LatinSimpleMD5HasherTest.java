package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;


import org.junit.*;

public class LatinSimpleMD5HasherTest {
	static private final String testPassword = "password";
	static private final String testWrongPassword = "passworD";

	static private LatinSimpleMD5Hasher testHasher;

	@BeforeClass
	static public void initPasswordHasher() {
		testHasher = new LatinSimpleMD5Hasher();
		PasswordHasher.registerHasher(testHasher);
	}

	@Test
	public void getFromPasswordHasher() {
		assertEquals(testHasher, PasswordHasher.hasherWithCode(LatinSimpleMD5Hasher.hasherCode));
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
	public void twoHashWithSamePasswordAreEqualsBecauseWeAreNotSalted() {
		PasswordHash hashedPassword1 = testHasher.hashPassword(testPassword);
		PasswordHash hashedPassword2 = testHasher.hashPassword(testPassword);
		
		assertTrue(hashedPassword1.toString().equals(hashedPassword2.toString()));
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
}
