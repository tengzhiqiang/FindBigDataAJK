package com.example.demo.util;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpContent {
	
	
	public static void main(String[] args) {
		httpUtil("https://ks.anjuke.com/sale/");
	}
	
	public static String httpUtil(String url,List<AgentVo> list) {
		
		Random random = new Random();
		int index = random.nextInt(list.size());
		
		HttpClientBuilder builder = HttpClients.custom();
		String content = "";int loop = 0;
		
		try {
			CloseableHttpClient client = builder.build();
			HttpHost proxy=new HttpHost(list.get(index).getIp(), Integer.parseInt(list.get(index).getPort()));
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
			HttpGet request = new HttpGet(url);
			request.setConfig(requestConfig);
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
			CloseableHttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				try {
					loop++;
					System.err.println("链接出现错误，休息5s************************");
					System.out.println("网络状态："+response.getStatusLine().getStatusCode()+",  现在休息一分钟");
					Thread.sleep(1000 * 5);//报错的时候现成休息1分钟
					if (response.getStatusLine().getStatusCode() == 404) {
						return "";
					}else if (loop == 3 ) {//多次请求成功
						return "";
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				httpUtil(url,list);
			}
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
		} catch (IOException e) {
			e.printStackTrace();
//			list.remove(index);
			httpUtil(url,list);
		}
		return content;
	}
	public static String httpUtil(String url) {
		
		HttpClientBuilder builder = HttpClients.custom();
		String content = "";
		
		try {
			CloseableHttpClient client = builder.build();
			RequestConfig requestConfig = RequestConfig.custom().build();
			HttpGet request = new HttpGet(url);
			request.setConfig(requestConfig);
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
			CloseableHttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				try {
					Thread.sleep(1000 * 60);//报错的时候现成休息1分钟
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				httpUtil(url);
			}
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
