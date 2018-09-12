package com.example.demo.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.service.IDownHtml;
import com.example.demo.util.HttpContent;

public class AJKDownImplServince implements IDownHtml {

	@Override
	public String ajkDownPage(String url) {
		
		return HttpContent.httpUtil(url);
	}
	
	@Override
	public String ajkDownSealPage(String url) {
		String content = HttpContent.httpUtil(url);
		Document rootDocument = Jsoup.parse(content);
		Elements elements = rootDocument.select("#content");
		if (elements != null && elements.size() > 0) {
			return elements.get(0).toString();
		}
		return null;
	}
	
	
}
