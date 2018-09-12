package com.example.demo.xici;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.util.HttpContent;

/**
 * 获取西刺ip代理
 * @author Administrator
 *
 */
public class HttpAgent {
	protected static  final String xici = "http://www.xicidaili.com/nn/";
	
	
	public static void main(String[] args) throws IOException {
		String content ="";
		for (int i=1;i<2 ;i++) {
			content = HttpContent.httpUtil(xici+i);
			if (content != null) {
				Document root = Jsoup.parse(content);
				Elements tbodys = root.select("tbody");
				Elements trs = tbodys.get(0).select("tr");
				trs.remove(0);
				for (Element element : trs) {
					Elements tds = element.select("td");
					System.out.println("Ip:"+tds.get(1).text());
					System.out.println("port:"+tds.get(2).text());
					Elements aas = tds.get(3).select("a");
					if (aas != null && aas.size() > 0) {
						System.out.println("addtess:"+tds.get(3).select("a").get(0).text());
					}else {
						System.out.println("addtess:"+tds.get(3).text());
					}
				}
			}
		}

	}
	
	
	public static String readeFile(String url) throws IOException {
		File file = new File(url);
		if (!file.exists()) {
			return null;
		}
//		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line ="";
		String content="";
		while ((line = br.readLine()) != null) {
			
			content += line;
		}
		return content;
	}

}
