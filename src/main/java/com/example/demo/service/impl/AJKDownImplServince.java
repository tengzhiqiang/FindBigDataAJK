package com.example.demo.service.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.service.IDownHtml;

public class AJKDownImplServince implements IDownHtml {

	@Override
	public String ajkDownPage(String url) {
		
		return httpUtil(url);
	}
	
	@Override
	public String ajkDownSealPage(String url) {
		String content = httpUtil(url);
		Document rootDocument = Jsoup.parse(content);
		Elements elements = rootDocument.select("#content");
		if (elements != null && elements.size() > 0) {
			return elements.get(0).toString();
		}
		return null;
	}
	
	
	public String  httpUtil(String url) {
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

}
