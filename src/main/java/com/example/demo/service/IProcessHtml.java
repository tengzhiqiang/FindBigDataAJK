package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;

public interface IProcessHtml {

	public PageEntity pageHtml(String html,String url);
	public List<SealEntity> analyseHtml(PageEntity page);
}
