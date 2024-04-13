package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.*;

import er.extensions.foundation.ERXStringUtilities;


public class PasswordHashTest {
	
	private static final String password = "password";
	private static final String wrongPassword = "passworD";
	private static final String testHasherCode = "Test";
	private static final String testSaltString = "aa11bb22";
	private static final byte[] testSalt = ERXStringUtilities.hexStringToByteArray(testSaltString);
	private static int testIterations = 1000;
	private static PasswordHasher testHasher;
	private static final String testHashString = "a0b2c3d4";
	private static final byte[] testHash = ERXStringUtilities.hexStringToByteArray(testHashString);
	private static final String validCompleteHashString = testHasherCode+":"+testHashString+":"+testSaltString+":"+testIterations;

	@BeforeClass
	static public void initTestHasher() {
		testHasher = mock(PasswordHasher.class);
		when(testHasher.hasherCode()).thenReturn(testHasherCode);
		PasswordHasher.registerHasher(testHasher);
	}
	
	@Test
	public void createHashWithAllVales() {
		PasswordHash hash = new PasswordHash(testHasher, testHash, testSalt, testIterations);
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertArrayEquals(testSalt, hash.salt);
		assertEquals(testIterations, hash.iterations);
	}
	
	@Test
	public void createHashWithHashAndSaltOnly() {
		PasswordHash hash = new PasswordHash(testHasher, testHash, testSalt);
		
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertArrayEquals(testSalt, hash.salt);
		assertEquals(0, hash.iterations);
	}

	@Test
	public void createHashWithHashOnly() {
		PasswordHash hash = new PasswordHash(testHasher, testHash);
		
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertNull(hash.salt);
		assertEquals(0, hash.iterations);
	}

	@Test(expected=IllegalArgumentException.class)
	public void createHashWithNullHashShoudFail() {
		new PasswordHash(testHasher, null, testSalt, testIterations);
	}

	@Test
	public void createWithValidHashString() {
		PasswordHash.parseHash(validCompleteHashString);
	}
	
	private static final String invalidCompleteHashString1 = testHasherCode+":"+testHashString+"z:"+testSaltString+":"+testIterations;
	private static final String invalidCompleteHashString2 = testHasherCode+":"+testHashString+":z"+testSaltString+":"+testIterations;
	private static final String invalidCompleteHashString3 = testHasherCode+":"+testHashString+":"+testSaltString+":z"+testIterations;

	@Test(expected=IllegalArgumentException.class)
	public void createWithInvalidHashString1ShouldFail() {
		PasswordHash.parseHash(invalidCompleteHashString1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void createWithInvalidHashString2ShouldFail() {
		PasswordHash.parseHash(invalidCompleteHashString2);
	}
	@Test(expected=IllegalArgumentException.class)
	public void createWithInvalidHashString3ShouldFail() {
		PasswordHash.parseHash(invalidCompleteHashString3);
	}

	@Test(expected=IllegalArgumentException.class)
	public void createWithHashOnlyStringWithoutDefaultHasherShouldFail() {
		PasswordHash.parseHash(testHashString);
	}

	@Test
	public void createWithHashOnlyStringWithDefaultHasher() {
		PasswordHash hash = PasswordHash.parseHashWithDefaultHasher(testHashString, testHasher);
		
		assertArrayEquals(testHash, hash.hash);
	}

	private static final String hashStringWithHashOnly = testHasherCode+":"+testHashString;
	private static final String hashStringWithHashAndSaltOnly = testHasherCode+":"+testHashString+":"+testSaltString;
	
	@Test
	public void createWithPartialHashString() {
		PasswordHash hash = PasswordHash.parseHash(hashStringWithHashOnly);
		
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertNull(hash.salt);
		assertEquals(0, hash.iterations);
	}

	@Test
	public void createWithPartialHashString2() {
		PasswordHash hash = PasswordHash.parseHash(hashStringWithHashAndSaltOnly);
		
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertArrayEquals(testSalt, hash.salt);
		assertEquals(0, hash.iterations);
	}

	@Test
	public void createWithCompleteHashString() {
		PasswordHash hash = PasswordHash.parseHash(validCompleteHashString);
		assertEquals(testHasher, hash.hasher);
		assertArrayEquals(testHash, hash.hash);
		assertArrayEquals(testSalt, hash.salt);
		assertEquals(testIterations, hash.iterations);
	}
	
	@Test
	public void toStringShouldReturnAValidCompleteHash() {
		PasswordHash hash = PasswordHash.parseHash(validCompleteHashString);
		String completeHashString = hash.toString();
		
		assertEquals(validCompleteHashString, completeHashString);
	}
	
	@Test
	public void toStringOfHashStringWithHashOnlyShouldReturnValidHashString() {
		PasswordHash hash = PasswordHash.parseHash(hashStringWithHashOnly);
		String hashString = hash.toString();
		
		assertEquals(hashStringWithHashOnly, hashString);
	}
	
	@Test
	public void toStringOfHashStringWithHashAndSaltOnlyShouldReturnValidHashString() {
		PasswordHash hash = PasswordHash.parseHash(hashStringWithHashAndSaltOnly);
		String hashString = hash.toString();
		
		assertEquals(hashStringWithHashAndSaltOnly, hashString);
	}
	
	@Test
	public void toStringFromaHashShouldReturnAValidCompleteHashToCreateNewHash() {
		PasswordHash hash = PasswordHash.parseHash(validCompleteHashString);
		String completeHashString = hash.toString();
		
		PasswordHash.parseHash(completeHashString);
	}
	
	@Test
	public void hashAsHexStringReturnTheHexStringOfTheHash() {
		PasswordHash hash = PasswordHash.parseHash(validCompleteHashString);
		
		assertEquals(testHashString, hash.hashAsHexString());
	}
	
	@Test
	public void hashEqualsShouldCompareHashArrays() {
		byte[] testHashCopy = Arrays.copyOf(testHash, testHash.length);
		PasswordHash hash = new PasswordHash(testHasher, testHash);
		PasswordHash hashWithHashCopy = new PasswordHash(testHasher, testHashCopy);
		PasswordHash hashWithWrongHash = new PasswordHash(testHasher, testSalt);
		
		assertTrue(hash.hashEquals(hashWithHashCopy));
		assertFalse(hash.hashEquals(hashWithWrongHash));
	}

	@Test
	public void verifyPasswordCallHasherVerifyPassword() {
		PasswordHash hash = new PasswordHash(testHasher, testHash);

		when(testHasher.verifyPasswordWithHash(password, hash)).thenReturn(true);
		when(testHasher.verifyPasswordWithHash(wrongPassword, hash)).thenReturn(false);
		
		hash.verifyPassword(password);
		
		verify(testHasher).verifyPasswordWithHash(password, hash);
		assertTrue(hash.verifyPassword(password));
		assertFalse(hash.verifyPassword(wrongPassword));
	}
}
