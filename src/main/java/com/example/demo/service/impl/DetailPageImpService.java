package com.example.demo.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.JdbcServince;
import com.example.demo.entity.DetailPage;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IDetailPageService;
import com.example.demo.util.AgentVo;
import com.example.demo.util.FileUtil;
import com.example.demo.util.HttpContent;

public class DetailPageImpService implements IDetailPageService {

	@Override
	public List<DetailPage> getList(int start ,int limit) {
		List<DetailPage> list =  new ArrayList<DetailPage>();
		String sql = " select * from t_sealpage limit "+start +", "+limit;
		ResultSet rs = JdbcServince.excutQuery(JdbcServince.getConnection(), sql);
		list = detailPageRowMap(rs);
		return list;
	}
	
	public List<DetailPage> detailPageRowMap(ResultSet rs) {
		List<DetailPage> list =  new ArrayList<DetailPage>();
		DetailPage detailPage = null;
		try {
			while (rs.next()) {
				detailPage = new DetailPage();
				detailPage.setContent(rs.getString("content"));
				detailPage.setFatherid(rs.getLong("fatherid"));
				detailPage.setId(rs.getLong("id"));
				list.add(detailPage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(SealEntity seal,List<AgentVo> list) {
		DetailPage detailPage = new DetailPage();
		String content = HttpContent.httpUtil(seal.getHerf(),list);
		String pathString = seal.getId()+"";
		if (content != null) {
			pathString = FileUtil.creatFile("D:\\2018\\", seal.getId(), content);
		}
		detailPage.setFatherid(seal.getId());
		detailPage.setContent(pathString);
		new StoreService().storeDetailPge(detailPage);
		
	}

}
