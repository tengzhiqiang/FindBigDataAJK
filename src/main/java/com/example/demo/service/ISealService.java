package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SealEntity;

public interface ISealService {
	public List<SealEntity> getListSeal(int start,int limit, String day) ;
	SealEntity get(long id);
}
