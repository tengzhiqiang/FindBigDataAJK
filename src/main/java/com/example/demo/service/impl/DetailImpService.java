package com.example.demo.service.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.example.demo.JdbcServince;
import com.example.demo.entity.DetailEntity;
import com.example.demo.service.IDetailService;

public class DetailImpService implements IDetailService {

	@Override
	public void save(DetailEntity detail) {
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String sql = "INSERT INTO t_detail (houseid, publictime, housetype,buytimes,orient,firstpay,monthpay,decoration,addtime) VALUES (";
		sql += "'"+detail.getHouseid()+"',";
		sql += "'"+detail.getPublictime()+"',";
		sql += "'"+detail.getHousetype()+"',";
		sql += "'"+detail.getBuytimes()+"',";
		sql += "'"+detail.getOrient()+"',";
		sql += "'"+detail.getFirstpay()+"',";
		sql += "'"+detail.getMonthpay()+"',";
		sql += "'"+detail.getDecoration()+"',";
		sql += "'"+time+"'";
		sql += ")";
		JdbcServince.excuteUpdate(JdbcServince.getConnection(), sql);
	}

}
