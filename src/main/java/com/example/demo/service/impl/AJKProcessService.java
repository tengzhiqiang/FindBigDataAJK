package com.example.demo.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.JdbcServince;
import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IProcessHtml;

public class AJKProcessService implements IProcessHtml {

	@Override
	public PageEntity pageHtml(String html, String url) {
		PageEntity page = new PageEntity();

		Document root = Jsoup.parse(html);
		Elements elements = root.select("#houselist-mod-new");
		if (elements != null && elements.size() > 0) {
			page.setContent(elements.get(0).toString());
		}

		// 页码
		Elements yemas = root.select(".multi-page i.curr");
		if (yemas != null && yemas.size() > 0) {
			page.setOnpage(yemas.get(0).text());
		}
		// 当前url
		page.setOnurl(url);

		return page;
	}

	@Override
	public List<SealEntity> analyseHtml(PageEntity page) {
		String useContentRex = "#houselist-mod-new .list-item";
		if (page == null ) {
			return null;
		}
		String content = page.getContent();
		Document document = Jsoup.parse(content);
		Elements elements = document.select(useContentRex);//获取有用的信息,html信息列表
		
		List<SealEntity> seals = new ArrayList<SealEntity>();
		SealEntity sealEntity = null;
		for (Element element : elements) {
			sealEntity = new SealEntity();
			Elements housedetails = element.select(".house-details .house-title a");
			//获取子引用
			sealEntity.setHerf(housedetails.get(0).attr("href"));
			//获取标题
			sealEntity.setTitle(housedetails.get(0).attr("title"));
//			安居验证
			Elements ajyz = element.select(".house-details .house-title em");
			if (ajyz != null && ajyz.size() > 0) {
				sealEntity.setAjk(1);
			}else {
				sealEntity.setAjk(0);
			}
//			详细信息
			Elements details = element.select(".details-item span");
			if (details != null && details.size() > 0) {
				
				sealEntity.setHousetype(details.get(0).text());
				sealEntity.setArea(details.get(1).text());
				sealEntity.setBlocktype(details.get(2).text());
				sealEntity.setYear(details.get(3).text());
				String address = details.get(5).text();
				String[] addstr = address.split(" ");
				sealEntity.setPlotname(addstr[0]);
				sealEntity.setAddress(addstr[1]);
				String[] zoons = addstr[1].split("-");
				sealEntity.setZoone(zoons[0]);
			}
//			价格
			Elements prices = element.select(".pro-price span");
			sealEntity.setTotleprice(prices.get(0).text());
			sealEntity.setUnitprice(prices.get(1).text());
			seals.add(sealEntity);
		}
		return seals;
	}

	@Override
	public List<PageEntity> getListPage() {
		StringBuilder sql = new StringBuilder(" select * from t_page");
		ResultSet rs = JdbcServince.excutQuery(JdbcServince.getConnection(), sql.toString());
		List<PageEntity> list = rowMapper(rs);
		return list;
	}
	
	public List<PageEntity> rowMapper(ResultSet rs) {
		List<PageEntity> list = new ArrayList<PageEntity>();
		PageEntity pageEntity = null;
		try {
			while (rs.next()) {
				pageEntity = new PageEntity();
				pageEntity.setContent(rs.getString("content"));
				pageEntity.setId(rs.getLong("id"));
				pageEntity.setOnpage(rs.getString("onpage"));
				pageEntity.setOnurl(rs.getString("onurl"));
				list.add(pageEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	


}
