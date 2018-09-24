package com.example.demo.entity;

public class SealEntity {
	
	private long id;
	private String title;
	private String herf;//详细内容连接
	private String housetype;//户型
	private String area;//面积
	private String blocktype;//楼层类型
	private String year;//建造时间
	private String address;//地址
	private String totleprice;//总价
	private String unitprice;//单价
	private String zoone;//区域
	private String plotname;//小区名称
	private int ajk;//安选验真
	private String addtime;
	
	private String houseid; //房屋编码
	private String publictime;//发布时间
	private String houseclass ;//普通住宅，商业住房，单身公寓
	private String buytimes;//满五年，满两年，两年以内
	private String orient;//南北朝向，东西朝向
	private float firstpay;//参考首付
	private float monthpay;//月供
	private String decoration;//精装修，普通装修，毛坯
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHerf() {
		return herf;
	}
	public void setHerf(String herf) {
		this.herf = herf;
	}
	public String getHousetype() {
		return housetype;
	}
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBlocktype() {
		return blocktype;
	}
	public void setBlocktype(String blocktype) {
		this.blocktype = blocktype;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTotleprice() {
		return totleprice;
	}
	public void setTotleprice(String totleprice) {
		this.totleprice = totleprice;
	}
	public String getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	public String getZoone() {
		return zoone;
	}
	public void setZoone(String zoone) {
		this.zoone = zoone;
	}
	public int getAjk() {
		return ajk;
	}
	public void setAjk(int ajk) {
		this.ajk = ajk;
	}
	public String getPlotname() {
		return plotname;
	}
	public void setPlotname(String plotname) {
		this.plotname = plotname;
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
	public String getPublictime() {
		return publictime;
	}
	public void setPublictime(String publictime) {
		this.publictime = publictime;
	}
	public String getHouseclass() {
		return houseclass;
	}
	public void setHouseclass(String houseclass) {
		this.houseclass = houseclass;
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
	public float getMonthpay() {
		return monthpay;
	}
	public void setMonthpay(float monthpay) {
		this.monthpay = monthpay;
	}
	public float getFirstpay() {
		return firstpay;
	}
	public void setFirstpay(float firstpay) {
		this.firstpay = firstpay;
	}
	public String getDecoration() {
		return decoration;
	}
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}
	
	
	

}
