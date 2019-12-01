package com.javawomen.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.InputStreamEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.javawomen.app.cryptography.JuliusCaesarCryptography;

public class AppTest {

	@Test
	public void answerChallange() {
		int places = 2;
		String encrypted = "aqw owuv jcxg ejcqu kp aqwt uqwn vq ikxg dktvj vq c fcpekpi uvct. htkgftkej pkgvbuejg";

		String decrypted = JuliusCaesarCryptography.decrypt(places, encrypted);
		assertEquals("decrypt assert", "you must have chaos in your soul to give birth to a dancing star. friedrich nietzsche", decrypted);

		String cryptographicSummary = JuliusCaesarCryptography.cryptographicSummary(decrypted);
		assertEquals("cryptographic summary assert", "9f21a27ad5440a1354d7a612ec6737d83e8b91d7", cryptographicSummary);
	}
	
	@Test
	public void answerDefault() {
		int places = 3;
		String encrypted = "d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr";

		String decrypted = JuliusCaesarCryptography.decrypt(places, encrypted);
		assertEquals("decrypt assert", "a ligeira raposa marrom saltou sobre o cachorro cansado", decrypted);

		String cryptographicSummary = JuliusCaesarCryptography.cryptographicSummary(decrypted);
		assertEquals("cryptographic summary assert", "b5395144deedf4a152cf3180b7deaa5ba0d468bd", cryptographicSummary);
	}


	@Test
	public void answerWithNumber() {
		int places = 3;
		String encrypted = "1d.d";

		String decrypted = JuliusCaesarCryptography.decrypt(places, encrypted);
		assertEquals("decrypt assert", "1a.a", decrypted);

		String cryptographicSummary = JuliusCaesarCryptography.cryptographicSummary(decrypted);
		assertEquals("cryptographic summary assert", "335a4632a3b3729e7bb731132e93d2ede0a0e18b", cryptographicSummary);
	}
}
