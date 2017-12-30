package com.helloworld;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

public class HttpClientTest {

	@Test
	public void testGet() throws InterruptedException, ExecutionException {
		String url = "https://karthikdivi.com/test/sampleGetCall";
		String response = HttpClient.getInstance().get(url).get();
		System.out.println("Response:" + response);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testPost() throws InterruptedException, ExecutionException {
		String url = "https://karthikdivi.com/test/samplePostCall";
		String body = "{\"foo\" : 1}";
		String response = HttpClient.getInstance().post(url, body).get();
		System.out.println("Response:" + response);
		Assert.assertNotNull(response);
	}
}
