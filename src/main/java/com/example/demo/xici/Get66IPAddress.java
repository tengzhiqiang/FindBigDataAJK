package com.example.demo.xici;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.example.demo.util.AgentVo;

public class Get66IPAddress {
	
	
	public static void main(String[] args) {
		getIpAddress(new ArrayList<>());
		
	}
	

	public static List<AgentVo> getIpAddress(List<AgentVo> list) {
		String url = "http://www.66ip.cn/nmtq.php?getnum=50&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=1&api=66ip";

		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient client = builder.build();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"); // 设置请求头消息User-Agent
		request.addHeader("Accept-Language","zh-CN,zh;q=0.9");
//		Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36
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
		content = content.substring(content.indexOf("<body>")+6,content.lastIndexOf("<br>"));
		System.out.println(content);
		String[] ipv4 = content.split("<br>");
		System.out.println("**********************分割线*******************");
		AgentVo agentVo = null;
		for (String ipadress : ipv4) {
			agentVo = new AgentVo();
			if (StringUtils.isNoneBlank(ipadress)) {
				String[] iparrays = ipadress.split(":");
				System.out.println(ipadress);
				if (iparrays.length > 1) {
					agentVo.setIp(iparrays[0]);
					agentVo.setPort(iparrays[1]);
				}
			}
		}
		return list;
	}
}
