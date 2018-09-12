package com.example.demo.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpContent {
	
	public static String httpUtil(String url) {
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		String content = "";
		
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	
	public static void httpAgent(AgentVo agentVo) {
		System.setProperty("http.maxRedirects", "50");  
        System.getProperties().setProperty("proxySet", "true");  
        // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以  
        System.getProperties().setProperty("http.proxyHost", agentVo.getIp());  
        System.getProperties().setProperty("http.proxyPort", agentVo.getPort());
	}

}
