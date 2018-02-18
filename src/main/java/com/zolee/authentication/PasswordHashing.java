package com.zolee.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

// !!from: https://dzone.com/articles/storing-passwords-java-web
@Service
public class PasswordHashing {

	public static final String SALT = "G65Xerw5ewys9mc46ibm9aE1lJn8xo8n3oLcn3wq8743896n7598vn7489";


	public static String generateHashCode(String inputText) {
		String saltedText = inputText + SALT;
		StringBuilder resultHashCode = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(saltedText.getBytes());
			char[] tempChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int i = 0; i < hashedBytes.length; ++i) {
				byte tempByte = hashedBytes[i];
				resultHashCode.append(tempChars[(tempByte & 0xf0) >> 4]);
				resultHashCode.append(tempChars[tempByte & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}
		return resultHashCode.toString();
	}

}