package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.DetailPage;
import com.example.demo.entity.SealEntity;
import com.example.demo.util.AgentVo;

public interface IDetailPageService {
	List<DetailPage> getList(int start ,int limit);
	void save(SealEntity sealEntity,List<AgentVo> list);
}
