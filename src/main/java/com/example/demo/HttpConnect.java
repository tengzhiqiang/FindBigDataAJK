package com.example.demo;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.example.demo.entity.PageEntity;

public class HttpConnect {
	public static String modelUrl ="https://ks.anjuke.com/sale/o5-p昆山/#filtersort";
	
	
	public static void main(String[] args) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		
//		for(int i=1;i<51;i++) {
			
			String url = modelUrl.replace("昆山", 25+"");
			
			String content = downPage(url);//下载
			
			PageEntity page = JsoupAnalyse.pageHtml(content, url);//转换成对象
			
			StoreService.storePage(page);//保存
			try {
				Thread.sleep(1000*30); //休息1秒钟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//		}
		
		System.out.println("采集结束."+DateFormatUtils.format(new Date(), pattern));
		
	}
	
	
	public static String downPage(String url) {
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
