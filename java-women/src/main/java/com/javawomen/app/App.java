package com.javawomen.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.javawomen.app.cryptography.JuliusCaesarCryptography;

public class App 
{
    private static Scanner scanner;

	public static void main( String[] args ) throws IOException
    {
    	scanner = new Scanner(System.in);
        System.out.print("Host: ");
        String host = scanner.next();
        System.out.print("Token: ");
        String token = scanner.next();
        Gson gson = new Gson();
        
    	ChallengeManager challengeManager = new ChallengeManager(host, token);
    	String challengeRequest = challengeManager.acceptChallenge();
    	ChallengeRequestVO challenge = gson.fromJson(challengeRequest, ChallengeRequestVO.class);
    	
    	String decrypted = JuliusCaesarCryptography.decrypt(challenge.getNumeroCasas(), challenge.getCifrado());
    	challenge.setDecifrado(decrypted);
    	challenge.setResumoCriptografico(JuliusCaesarCryptography.cryptographicSummary(decrypted));
    	
    	String fileData = gson.toJson(challenge);
    	
    	String filePath = String.format("%s/resources/answer.json", System.getProperty("user.dir"));
    	FileOutputStream fos = new FileOutputStream(filePath);
    	fos.write(fileData.getBytes());
    	fos.flush();
    	fos.close();
    	    	
    	File file = new File(filePath);
    	String response = challengeManager.submitChallenge(file);
    	System.out.println(response);
    
    }
}
