package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupAnalyse {
	private static String useContentRex = "";
	
	public static void analyseHtml(String content) {
		Document document = Jsoup.parse(content);
		
		Elements elements = document.select(useContentRex);//获取有用的信息,html信息列表
		for (Element element : elements) {
			
//		获取herf
			System.out.println("子引用："+element.attr("href"));
//			详细信息
			Elements details = document.select(".details-item span");
			if (details != null && details.size() > 0) {
				
				System.out.println("房屋类型："+elements.get(0).text());
				System.out.println("房屋面积："+elements.get(1).text());
				System.out.println("楼层："+elements.get(2).text());
				System.out.println("建筑年度："+elements.get(2).text());
			}
		}
		
		
	}
	
	

	
}
