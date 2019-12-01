package com.javawomen.app.cryptography;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class JuliusCaesarCryptography {
	
	public static String decrypt(int places, String encrypted) {
		StringBuilder decrypted = new StringBuilder();
		for (char character : encrypted.toCharArray()) {
			if(Character.isLetter(character)) {
				places = places % 26;
				if(character - places < 'a') {
					int newPlaces = places - (character - 'a') - 1; 
					decrypted.append((char) ('z' - newPlaces));
				} else {
					decrypted.append((char)(character - places));
				}				
			} else {
				decrypted.append(character);
			}
		}
		return decrypted.toString();
	}
	
	public static String cryptographicSummary(String decrypted) {		
		return DigestUtils.sha1Hex(decrypted);
	}
}
