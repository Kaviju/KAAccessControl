package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
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

	@Test(expected=IllegalArgumentException.class)
	public void getUnknowHasherShouldThrow() {
		PasswordHasher.hasherWithCode("testUnknown");
	}
}
