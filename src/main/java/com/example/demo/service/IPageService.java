package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.PageEntity;

public interface IPageService {
	public List<PageEntity> getListPage(String day);
}
