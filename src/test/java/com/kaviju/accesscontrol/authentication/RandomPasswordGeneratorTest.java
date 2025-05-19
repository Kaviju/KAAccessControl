package com.kaviju.accesscontrol.authentication;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomPasswordGeneratorTest {
	static final String possibleAlphaChars = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	static final String possibleChars = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ@#$%?&*()-+";
	static final String possibleCodeChars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	static final String possibleDigits = "0123456789";

	@Test
	public void testAlphanumericPassword() {
		String alphaPassword = RandomPasswordGenerator.newAlphanumericPassword(10);
		
		assertEquals(alphaPassword.length(), 10);
		for (int i = 0; i < alphaPassword.length(); i++) {
			assertNotEquals(possibleAlphaChars.indexOf(alphaPassword.charAt(i)), -1);
		}
		// Generate some to validate randomness
		for (int i = 0; i < 10; i++) {
			assertNotEquals(alphaPassword, RandomPasswordGenerator.newAlphanumericPassword(10));			
		}
	}

	@Test
	public void testPassword() {
		String password = RandomPasswordGenerator.newPassword(10);
		
		assertEquals(password.length(), 10);
		for (int i = 0; i < password.length(); i++) {
			assertNotEquals(possibleChars.indexOf(password.charAt(i)), -1);
		}
		// Generate some to validate randomness
		for (int i = 0; i < 10; i++) {
			assertNotEquals(password, RandomPasswordGenerator.newPassword(10));			
		}
	}

	@Test
	public void testCode() {
		String password = RandomPasswordGenerator.newCode(10);
		
		assertEquals(password.length(), 10);
		for (int i = 0; i < password.length(); i++) {
			assertNotEquals(possibleCodeChars.indexOf(password.charAt(i)), -1);
		}
		// Generate some to validate randomness
		for (int i = 0; i < 10; i++) {
			assertNotEquals(password, RandomPasswordGenerator.newCode(10));			
		}
	}

	@Test
	public void testRandomDigits() {
		String digits = RandomPasswordGenerator.newDigitsPassword(10);
		
		assertEquals(digits.length(), 10);
		for (int i = 0; i < digits.length(); i++) {
			assertNotEquals(possibleDigits.indexOf(digits.charAt(i)), -1);
		}
		// Generate some to validate randomness
		for (int i = 0; i < 10; i++) {
			assertNotEquals(digits, RandomPasswordGenerator.newDigitsPassword(10));			
		}
	}

}
