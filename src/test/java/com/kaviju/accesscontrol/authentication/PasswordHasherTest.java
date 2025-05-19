package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.*;

public class PasswordHasherTest {
	
	private static final String testHasherCode = "test";
	protected static final byte[] hashedPassword = new byte[] {(byte) 0xa1, (byte) 0xa1, (byte) 0xa1, (byte) 0xa1};

	static private PasswordHasher testHasher;

	@BeforeClass
	static public void initPasswordHasher() {
		testHasher = mock(PasswordHasher.class);
		when(testHasher.hasherCode()).thenReturn(testHasherCode);
		when(testHasher.hashPassword(any(String.class))).thenReturn(new PasswordHash(testHasher, hashedPassword));
				
		PasswordHasher.registerHasher(testHasher);
	}

	@Test(expected=IllegalArgumentException.class)
	public void registerHasherWithNullCodeShouldThrow() {
		PasswordHasher testHasher = mock(PasswordHasher.class);
		when(testHasher.hasherCode()).thenReturn(null);
		when(testHasher.hashPassword(any(String.class))).thenReturn(new PasswordHash(testHasher, hashedPassword));
				
		PasswordHasher.registerHasher(testHasher);
	}

	@Test
	public void getKnownHasher() {
		assertEquals(testHasher, PasswordHasher.hasherWithCode(testHasherCode));
	}

	@Test
	public void equalsShouldWorksAsExpected() {
		Pbkdf2Hasher pbkdf2Hasher = new Pbkdf2Hasher(128, 10);
		PasswordHash pkdfHash1a = pbkdf2Hasher.hashPassword("qwerty");
		PasswordHash pkdfHash1b = pbkdf2Hasher.hashPassword("asdfgh");
		PasswordHash pkdfHash2 = new Pbkdf2Hasher(128, 12).hashPassword("asdfgh");
		
		assertEquals(pkdfHash1a.hashCode(), pkdfHash1a.hashCode());
		assertNotEquals(pkdfHash1a.hashCode(), pkdfHash1b.hashCode());
		assertEquals(pkdfHash1a.equals(pkdfHash1a), true);
		assertEquals(pkdfHash1a.equals(pkdfHash1b), false);
		assertEquals(pkdfHash1a.equals(pkdfHash2), false);
		assertEquals(pkdfHash1a.fromSameHashingSpecs(pkdfHash1b), true);
		assertEquals(pkdfHash1a.fromSameHashingSpecs(pkdfHash2), false);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getUnknowHasherShouldThrow() {
		PasswordHasher.hasherWithCode("testUnknown");
	}
}
