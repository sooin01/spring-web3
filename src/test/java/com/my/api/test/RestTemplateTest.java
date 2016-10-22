package com.my.api.test;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

public class RestTemplateTest {
	
	public static void main(String[] args) throws Exception {
//		String url = "https://localhost:8443/";
		String url = "https://http2.akamai.com/";
		
		X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
            	
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
            	
            }
        };
		
		TrustManager[] certs = new TrustManager[] {
				trustManager
		};
		
		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        };
		
		Logger logger = new Logger() {
			@Override
			public void log(String message) {
				System.out.println(message);
			}
		};
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger);
		logging.setLevel(Level.BASIC);
		
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, certs, new SecureRandom());
		
		OkHttpClient client = new OkHttpClient.Builder()
				.sslSocketFactory(ctx.getSocketFactory(), trustManager)
				.hostnameVerifier(hostnameVerifier)
				.addInterceptor(logging)
				.build();
		
//		Request request = new Request.Builder().url(url).build();
//		client.newCall(request).enqueue(new Callback() {
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				System.out.println(response.toString());
//			}
//			
//			@Override
//			public void onFailure(Call call, IOException e) {
//				e.printStackTrace();
//			}
//		});

		RestTemplate http2Template = new RestTemplate(new OkHttp3ClientHttpRequestFactory(client));
		ResponseEntity<String> http2Response = http2Template.getForEntity(url, String.class);
	}
	
}
