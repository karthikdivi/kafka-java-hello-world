package com.helloworld;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClient {
	private static HttpClient instance = new HttpClient();
	private OkHttpClient client;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private HttpClient() {
		client = new OkHttpClient();
	}

	public static HttpClient getInstance() {
		return instance;
	}

	public CompletableFuture<String> get(String url) {
		CompletableFuture<String> result = new CompletableFuture<>();
		Request request = new Request.Builder().url(url).build();
		try {
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					try (ResponseBody responseBody = response.body()) {
						if (!response.isSuccessful()) {
							System.out.println("Unsuccessful response. response:" + response);
							throw new IOException("Unexpected code " + response);
						}
						// FIXME check for 4xx, 5xx http status codes.
						result.complete(responseBody.string());
					}
				}

				@Override
				public void onFailure(Call call, IOException e) {
					result.completeExceptionally(e);
				}
			});
		} catch (Exception e) {
			result.completeExceptionally(e);
		}
		return result;
	}

	public CompletableFuture<String> post(String url, String body) {
		CompletableFuture<String> result = new CompletableFuture<>();
		RequestBody requestBody = RequestBody.create(JSON, body);
		Request request = new Request.Builder().url(url).post(requestBody).build();
		try {
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					try (ResponseBody responseBody = response.body()) {
						if (!response.isSuccessful()) {
							throw new IOException("Unexpected code " + response);
						}
						// FIXME check for 4xx, 5xx http status codes.
						result.complete(responseBody.string());
					}
				}

				@Override
				public void onFailure(Call call, IOException e) {
					result.completeExceptionally(e);
				}
			});
		} catch (Exception e) {
			result.completeExceptionally(e);
		}
		return result;
	}
}
