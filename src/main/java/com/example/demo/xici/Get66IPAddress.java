package com.example.demo.xici;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.util.AgentVo;

public class Get66IPAddress {

	public static void main(String[] args) {
		getIpAddress();
	}
	
	
	public static List<AgentVo> getIpAddress() {
		String url = "http://www.66ip.cn/nmtq.php?getnum=50&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=1&api=66ip";

		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		CloseableHttpResponse response = null;
		String content = null;
		try {
			response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200 ) {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document root = Jsoup.parse(content);
		Elements elements = root.select("body");
		System.out.println(elements.get(0).outerHtml());
		return null;
	}
}
