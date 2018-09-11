package com.example.demo.service.impl;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.example.demo.JdbcServince;
import com.example.demo.entity.DetailPage;
import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IStoreService;

public class StoreService implements IStoreService {

	String pattern = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public void storePage(PageEntity page) {
		String addtime = DateFormatUtils.format(new Date(), pattern);
		String sql = "INSERT INTO t_page (onpage, onurl, content,addtime) VALUES ('"+page.getOnpage()+"','"+page.getOnurl()+"','"+page.getContent()+"','"+addtime+"')";
		Connection cnn = JdbcServince.getConnection();
		JdbcServince.excuteUpdate(cnn, sql);

	}

	@Override
	public void storeSeal(SealEntity sealEntity) {
		String addtime = DateFormatUtils.format(new Date(), pattern);
		String sql = " INSERT INTO t_seal (title, herf, housetype,area,blocktype,year,address,totleprice,unitprice,zoone,plotname,ajk,addtime) VALUES ( ";
		sql += "'"+sealEntity.getTitle()+"',";
		sql += "'"+sealEntity.getHerf()+"',";
		sql += "'"+sealEntity.getHousetype()+"',";
		sql += "'"+sealEntity.getArea()+"',";
		sql += "'"+sealEntity.getBlocktype()+"',";
		sql += "'"+sealEntity.getYear()+"',";
		sql += "'"+sealEntity.getAddress()+"',";
		sql += "'"+sealEntity.getTotleprice()+"',";
		sql += "'"+sealEntity.getUnitprice()+"',";
		sql += "'"+sealEntity.getZoone()+"',";
		sql += "'"+sealEntity.getPlotname()+"',";
		sql += "'"+sealEntity.getAjk()+"',";
		sql += "'"+addtime+"'";
		sql += ")";
		JdbcServince.excuteUpdate(JdbcServince.getConnection(), sql);
	}

	@Override
	public void storeDetailPge(DetailPage detailPage) {
		String addtime = DateFormatUtils.format(new Date(), pattern);
		String sql = "INSERT INTO t_sealpage (fatherid, content,addtime) VALUES ('"+detailPage.getFatherid()+"','"+detailPage.getContent()+"','"+addtime+"')";
		Connection cnn = JdbcServince.getConnection();
		JdbcServince.excuteUpdate(cnn, sql);
		
	}

}
