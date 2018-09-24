package com.example.demo.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.JdbcServince;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.ISealService;

public class SealImpService implements ISealService {

	@Override
	public List<SealEntity> getListSeal(int start,int limit,String day) {
		StringBuilder sql = new StringBuilder("select * from t_seal where 1=1 ");
		if (StringUtils.isNoneBlank(day)) {
			sql.append(" and addtime like '"+day+"%'");
		}
		sql.append(" order by id limit ");
		if (start > 0) {
			sql.append( start+" , ");
		}
		sql.append(limit );
		ResultSet rs = JdbcServince.excutQuery(JdbcServince.getConnection(), sql.toString());
		return rowSealMapper(rs);
	}
	
	public List<SealEntity> rowSealMapper(ResultSet rs) {
		List<SealEntity> list = new ArrayList<SealEntity>();
		SealEntity seal = null;
		try {
			while (rs.next()) {
				seal = new SealEntity();
				seal.setId(rs.getLong("id"));
				seal.setAddress(rs.getString("address"));
				seal.setAddtime(rs.getString("addtime"));
				seal.setAjk(rs.getInt("ajk"));
				seal.setArea(rs.getString("area"));
				seal.setBlocktype(rs.getString("blocktype"));
				seal.setHerf(rs.getString("herf"));
				seal.setPlotname(rs.getString("plotname"));
				seal.setTitle(rs.getString("title"));
				seal.setTotleprice(rs.getString("totleprice"));
				seal.setUnitprice(rs.getString("unitprice"));
				seal.setYear(rs.getString("year"));
				seal.setZoone(rs.getString("zoone"));
				list.add(seal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public SealEntity get(long id) {
		String sql = " select * from t_seal where id = "+id;
		ResultSet rs = JdbcServince.excutQuery(JdbcServince.getConnection(), sql);
		List<SealEntity> seals = rowSealMapper(rs);
		if (seals != null && seals.size() > 0) {
			return seals.get(0);
		}
		return null;
	}


}
