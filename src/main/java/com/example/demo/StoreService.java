package com.example.demo;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.example.demo.entity.PageEntity;

/**
 * 保存数据
 * @author Administrator
 *
 */
public class StoreService {
	
	public static void main(String[] args) {
		PageEntity page = new PageEntity();
		page.setContent("conten");
		page.setOnpage("1");
		page.setOnurl("url");
		storePage(page);
		
	}

	public static void storePage(PageEntity page) {
		String addtime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO t_page (onpage, onurl, content,addtime) VALUES ('"+page.getOnpage()+"','"+page.getOnurl()+"','"+page.getContent()+"','"+addtime+"')";
		Connection cnn = JdbcServince.getConnection();
		JdbcServince.excute(cnn, sql);
	}
}
