package com.kaviju.accesscontrol.authentication;

import java.util.Random;

public class RandomPasswordGenerator {
	static final String possibleAlphaChars = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	static final String possibleChars = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ@#$%?&*()-+";
	static final String possibleDigits = "0123456789";
	static Random rnd = new Random();

	static public String newAlphanumericPassword(int len) {
		{
			StringBuilder password = new StringBuilder( len );
			for( int i = 0; i < len; i++ ) {
				int nextCharIndex = rnd.nextInt(possibleAlphaChars.length());
				password.append(possibleAlphaChars.charAt(nextCharIndex));
			}
			return password.toString();
		}		
	}

	static public String newPassword(int len) {
		{
			StringBuilder password = new StringBuilder( len );
			for( int i = 0; i < len; i++ ) {
				int nextCharIndex = rnd.nextInt(possibleChars.length());
				password.append(possibleChars.charAt(nextCharIndex));
			}
			return password.toString();
		}		
	}

	static public String newDigitsPassword(int len) {
		{
			StringBuilder password = new StringBuilder( len );
			for( int i = 0; i < len; i++ ) {
				int nextCharIndex = rnd.nextInt(possibleDigits.length());
				password.append(possibleDigits.charAt(nextCharIndex));
			}
			return password.toString();
		}		
	}
}
