package com.example.demo.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.JdbcServince;
import com.example.demo.entity.PageEntity;
import com.example.demo.service.IPageService;

public class PageImpService implements IPageService {

	@Override
	public List<PageEntity> getListPage(String day) {
		StringBuilder sql = new StringBuilder(" select * from t_page where addtime like '"+day+"%'");
		ResultSet rs = JdbcServince.excutQuery(JdbcServince.getConnection(), sql.toString());
		List<PageEntity> list = rowPageMapper(rs);
		return list;
	}
	
	public List<PageEntity> rowPageMapper(ResultSet rs) {
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
