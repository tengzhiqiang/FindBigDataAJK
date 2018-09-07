package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupAnalyse {
	private static String useContentRex = "#houselist-mod-new .list-item";
	
	public static void analyseHtml(String content) {
		Document document = Jsoup.parse(content);
		
		Elements elements = document.select(useContentRex);//获取有用的信息,html信息列表
		for (Element element : elements) {
			System.out.println("**************************************");
			Elements housedetails = element.select(".house-details .house-title a");
			//获取子引用
			System.out.println("子引用："+housedetails.get(0).attr("href"));
			//获取标题
			System.out.println("标题："+housedetails.get(0).attr("title"));
//			详细信息
			Elements details = element.select(".details-item span");
			if (details != null && details.size() > 0) {
				
				System.out.println("房屋类型："+details.get(0).text());
				System.out.println("房屋面积："+details.get(1).text());
				System.out.println("楼层："+details.get(2).text());
				System.out.println("建筑年度："+details.get(3).text());
				String address = details.get(5).text();
				String[] addstr = address.split(" ");
				System.out.println("小区名称："+addstr[0]);
				System.out.println("地址："+addstr[1]);
				String[] zoons = addstr[1].split("-");
				System.out.println("区域块："+zoons[0]);
			}
//			价格
			Elements prices = element.select(".pro-price span");
			System.out.println("总价："+prices.get(0).text());
			System.out.println("单价："+prices.get(1).text());
		}
		
		
	}
	
	

	
}
