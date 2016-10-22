package com.my.api.test;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

public class OkHttp3Test {

	public static void main(String[] args) throws Exception {
		String url = "https://localhost:8443/web/home";
//		String url = "https://http2.akamai.com/";
//		String url = "https://dl.am9.de/";
		
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
		
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.sslSocketFactory(ctx.getSocketFactory(), trustManager)
				.hostnameVerifier(hostnameVerifier)
				.addInterceptor(logging)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(call);
				System.out.println(response);
			}
		});
	}

}
