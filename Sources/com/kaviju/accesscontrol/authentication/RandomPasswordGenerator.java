package com.kaviju.accesscontrol.authentication;

import java.util.Random;

public class RandomPasswordGenerator {
	static final String possibleChars = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ@#$%?&*()-+";
	static Random rnd = new Random();

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
}
