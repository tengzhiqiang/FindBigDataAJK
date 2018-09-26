package com.example.demo;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class TestProxy {

	
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		httpUtil();
	}
	
	
	public static void httpUtil() throws ClientProtocolException, IOException {
		String ip ="117.131.75.134";
		int port =80;
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		HttpHost proxy=new HttpHost(ip, port);
		RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
		HttpGet request = new HttpGet("https://ks.anjuke.com/sale/yushana/?from=SearchBar");
		request.setConfig(requestConfig);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		CloseableHttpResponse response = client.execute(request);
		System.out.println(response.getStatusLine().getStatusCode());
	}
}
