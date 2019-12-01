package com.javawomen.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class ChallengeManager {
	private String token;
	private String host;

	public ChallengeManager() {
	}

	public ChallengeManager(String host, String token) {
		this.token = token;
		this.host = host;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String acceptChallenge() {
		String challengeRequest = null;
		HttpClient httpclient = HttpClients.createDefault();
		String url = String.format("%s/v1/challenge/dev-ps/generate-data?token=%s", host, token);
		System.out.println(url);
		HttpGet httpget = new HttpGet(url);
		
		try {
			HttpResponse response = httpclient.execute(httpget);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() / 100 == 2) {
				HttpEntity entity = response.getEntity();
				challengeRequest = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
		}

		return challengeRequest;
	}

	public String submitChallenge(File f) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = String.format("%s/v1/challenge/dev-ps/submit-solution?token=%s", host, token);
		HttpPost uploadFile = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.addBinaryBody(
		    "answer",
		    new FileInputStream(f),
		    ContentType.MULTIPART_FORM_DATA,
		    f.getName()
		);

		HttpEntity multipart = builder.build();
		uploadFile.setEntity(multipart);
		CloseableHttpResponse response = httpClient.execute(uploadFile);
		HttpEntity responseEntity = response.getEntity();		
		response.close();
		return  EntityUtils.toString(responseEntity);
	}
}
