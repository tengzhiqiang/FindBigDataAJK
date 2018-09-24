package com.example.demo.service.impl;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.service.IDownHtml;
import com.example.demo.util.AgentVo;
import com.example.demo.util.HttpContent;

public class AJKDownImplServince implements IDownHtml {

	@Override
	public String ajkDownPage(String url, List<AgentVo> list) {
		return HttpContent.httpUtil(url,list);
	}
	
	@Override
	public String ajkDownSealPage(String url, List<AgentVo> list) {
		String content = HttpContent.httpUtil(url,list);
		if (content == null || content.length() < 1000) {
			return null;
		}
		Document rootDocument = Jsoup.parse(content);
		Elements elements = rootDocument.select("#content");
		if (elements != null && elements.size() > 0) {
			return elements.get(0).toString();
		}
		return null;
	}
	
	
}
