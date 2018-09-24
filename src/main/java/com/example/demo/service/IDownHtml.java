package com.example.demo.service;

import java.util.List;

import com.example.demo.util.AgentVo;

public interface IDownHtml {

	public String ajkDownPage(String url, List<AgentVo> list) ;
	public String ajkDownSealPage(String url, List<AgentVo> list);
}
