package com.example.demo.entity;

/**
 * 保存每个房屋介绍的详细界面信息
 * @author Administrator
 *
 */
public class DetailEntity {

	private long id;
	private String houseid; //房屋编码
	private String publictime;//发布时间
	private String housetype ;//普通住宅，商业住房，单身公寓
	private String buytimes;//满五年，满两年，两年以内
	private String orient;//南北朝向，东西朝向
	private float firstpay;//参考首付
	private float monthpay;//月供
	private String decoration;//精装修，普通装修，毛坯
	private String addtime;//增加时间
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getPublictime() {
		return publictime;
	}
	public void setPublictime(String publictime) {
		this.publictime = publictime;
	}
	public String getHousetype() {
		return housetype;
	}
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	public String getBuytimes() {
		return buytimes;
	}
	public void setBuytimes(String buytimes) {
		this.buytimes = buytimes;
	}
	public String getOrient() {
		return orient;
	}
	public void setOrient(String orient) {
		this.orient = orient;
	}
	public String getDecoration() {
		return decoration;
	}
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public float getFirstpay() {
		return firstpay;
	}
	public void setFirstpay(float firstpay) {
		this.firstpay = firstpay;
	}
	public float getMonthpay() {
		return monthpay;
	}
	public void setMonthpay(float monthpay) {
		this.monthpay = monthpay;
	}
	
	
	
}
