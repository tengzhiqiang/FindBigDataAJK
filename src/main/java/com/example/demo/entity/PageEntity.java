package com.example.demo.entity;

public class PageEntity {

	private long id;
	private String onpage;//当前页
	private String onurl;//当前url
	private String content;//html网页内容
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOnpage() {
		return onpage;
	}
	public void setOnpage(String onpage) {
		this.onpage = onpage;
	}
	public String getOnurl() {
		return onurl;
	}
	public void setOnurl(String onurl) {
		this.onurl = onurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
