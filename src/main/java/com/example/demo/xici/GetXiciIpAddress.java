package com.example.demo.xici;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.util.AgentVo;
import com.example.demo.util.HttpContent;

public class GetXiciIpAddress {
	
	
	
	public static void main(String[] args) {
		
		getXshuIpAddress(new ArrayList<>());
	}
	
	
	public static List<AgentVo> getListAgents() {
		List<AgentVo> agentVos = new ArrayList<AgentVo>();
		AgentVo agentVo = null;
		String url = "http://www.xicidaili.com/nn/";
		for (int i = 1; i < 5; i++) {
			
			String content = HttpContent.httpUtil(url+i);
			Document root = Jsoup.parse(content);
			Elements roots = root.select("tbody");
			Elements trs = roots.get(0).select("tr");
			trs.remove(0);
			for (Element element : trs) {
				agentVo = new AgentVo();
				Elements tds = element.select("td");
				agentVo.setIp(tds.get(1).text());
				agentVo.setPort(tds.get(2).text());
				agentVos.add(agentVo);
			}
		}
		System.err.println("获取到的IP数量："+agentVos.size());
		 return agentVos;
	}
	
	public static AgentVo getRandomAgent(List<AgentVo> list) {
		Random random = new Random() ;
		int index = random.nextInt(list.size());
		return list.get(index);
	}
	
	/**
	 * 获取66代理的ip
	 * @param list
	 * @return
	 */
	public static List<AgentVo> get66Ip(List<AgentVo> list) {
		String url = "http://www.66ip.cn/nmtq.php?getnum=50&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=1&api=66ip";

		String content = HttpContent.httpUtil(url);

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
	
	
	/**
	 * 获取小舒IP代理
	 * @param list
	 * @return
	 */
	public static List<AgentVo> getXshuIpAddress(List<AgentVo> list) {
		String common = "http://www.xsdaili.com/dayProxy/ip/";
		
//		计算今天是什么数字，然后组装成
		String count = 1111+".html";
		
		String url = common+count;
		
		String content = HttpContent.httpUtil(url);
		Document root = Jsoup.parse(content);
		Elements elements = root.select(".cont");
		System.out.println(elements.get(0).toString());
		String[] strs = elements.get(0).toString().split("<br>");
		AgentVo agentVo = null;
		for (int i = 1;i<strs.length-1;i++) {
			String tem = strs[i].substring(0,strs[i].indexOf("@"));
			String[] array = tem.split(":");
			if (array.length>1) {
				agentVo = new AgentVo();
				agentVo.setIp(array[0]);
				agentVo.setPort(array[1]);
				list.add(agentVo);
			}
		}
		return list;
	}
	
}
