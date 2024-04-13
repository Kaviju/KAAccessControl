package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;

import org.junit.*;

public class Pbkdf2HasherTest {
	private static final int keyLength = 24;
	private static final int nbIterations = 1000;
	static private final String testPassword = "password";
	static private final String testWrongPassword = "passworD";

	static private Pbkdf2Hasher testHasher;
	private static Pbkdf2Hasher testHasherWithOtherKeyLength;
	private static Pbkdf2Hasher testHasherWithOtherIteration;

	@BeforeClass
	static public void initPasswordHasher() {
		testHasher = new Pbkdf2Hasher(keyLength, nbIterations);
		testHasherWithOtherKeyLength = new Pbkdf2Hasher(keyLength+1, nbIterations);
		testHasherWithOtherIteration = new Pbkdf2Hasher(keyLength, nbIterations+10);
		PasswordHasher.registerHasher(testHasher);
	}

	@Test
	public void getFromPasswordHasher() {
		assertEquals(PasswordHasher.hasherWithCode(Pbkdf2Hasher.hasherCode), (PasswordHasher)testHasher);
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

	@Test
	public void hasCreatedHashReturnTrueIfHashFromHasher() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertTrue(testHasher.hasCreatedHash(hashedPassword));
	}

	@Test
	public void hasCreatedHashReturnFalseIfHashFromOtherHasher() {
		LatinSimpleMD5Hasher otherHasher = new LatinSimpleMD5Hasher();
		
		PasswordHash hashedPassword = otherHasher.hashPassword(testPassword);
		
		assertFalse(testHasher.hasCreatedHash(hashedPassword));
	}

	@Test
	public void hasCreatedHashReturnFalseIfHashFromHasherWithDifferentKeyLength() {
		PasswordHash hashedPassword = testHasherWithOtherKeyLength.hashPassword(testPassword);
		
		assertFalse(testHasher.hasCreatedHash(hashedPassword));
	}
	
	@Test
	public void hasCreatedHashReturnFalseIfHashFromHasherWithDifferentIteration() {
		PasswordHash hashedPassword = testHasherWithOtherIteration.hashPassword(testPassword);
		
		assertFalse(testHasher.hasCreatedHash(hashedPassword));
	}
}
