package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetXiciIpAddress {
	
	
	
	public static void main(String[] args) {
		
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(random.nextInt(100));
		}
		
		getListAgents();
	}
	
	
	public static List<AgentVo> getListAgents() {
		List<AgentVo> agentVos = new ArrayList<AgentVo>();
		AgentVo agentVo = null;
		String url = "http://www.xicidaili.com/nn/";
		for (int i = 1; i < 4; i++) {
			
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
		 return agentVos;
	}
	
	public static AgentVo getRandomAgent(List<AgentVo> list) {
		Random random = new Random() ;
		int index = random.nextInt(list.size());
		return list.get(index);
	}
	
}
