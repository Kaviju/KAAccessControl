package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
		assertThat(PasswordHasher.hasherWithCode(Pbkdf2Hasher.hasherCode), is((PasswordHasher)testHasher));
	}

	@Test(expected=IllegalArgumentException.class)
	public void hashPasswordWithNullPasswordShouldThrow() {
		testHasher.hashPassword(null);
	}
	
	@Test
	public void hashedPasswordShouldBeginWithHasherCode() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		String hashString = hashedPassword.toString();
		
		assertThat(hashString.startsWith(testHasher.hasherCode()), is(true));
	}

	@Test
	public void twoHashWithSamePasswordAreDifferent() {
		PasswordHash hashedPassword1 = testHasher.hashPassword(testPassword);
		PasswordHash hashedPassword2 = testHasher.hashPassword(testPassword);
		
		assertThat(hashedPassword1.toString().equals(hashedPassword2.toString()), is(false));
	}
	
	@Test
	public void validateGoodPasswordShouldReturnTrue() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertThat(testHasher.verifyPasswordWithHash(testPassword, hashedPassword), is(true));
	}

	@Test
	public void validateWrongPasswordShouldReturnFalse() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertThat(testHasher.verifyPasswordWithHash(testWrongPassword, hashedPassword), is(false));
	}

	@Test(expected=RuntimeException.class)
	public void hashPasswordWithWrongParamsThrow() {
		Pbkdf2Hasher testHasherWithWrongParams = new Pbkdf2Hasher(keyLength, 0);
		
		testHasherWithWrongParams.hashPassword(testPassword);
	}

	@Test
	public void hasCreatedHashReturnTrueIfHashFromHasher() {
		PasswordHash hashedPassword = testHasher.hashPassword(testPassword);
		
		assertThat(testHasher.hasCreatedHash(hashedPassword), is(true));
	}

	@Test
	public void hasCreatedHashReturnFalseIfHashFromOtherHasher() {
		LatinSimpleMD5Hasher otherHasher = new LatinSimpleMD5Hasher();
		
		PasswordHash hashedPassword = otherHasher.hashPassword(testPassword);
		
		assertThat(testHasher.hasCreatedHash(hashedPassword), is(false));
	}

	@Test
	public void hasCreatedHashReturnFalseIfHashFromHasherWithDifferentKeyLength() {
		PasswordHash hashedPassword = testHasherWithOtherKeyLength.hashPassword(testPassword);
		
		assertThat(testHasher.hasCreatedHash(hashedPassword), is(false));
	}
	
	@Test
	public void hasCreatedHashReturnFalseIfHashFromHasherWithDifferentIteration() {
		PasswordHash hashedPassword = testHasherWithOtherIteration.hashPassword(testPassword);
		
		assertThat(testHasher.hasCreatedHash(hashedPassword), is(false));
	}
}
