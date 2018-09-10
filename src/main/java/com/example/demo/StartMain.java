package com.example.demo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IDownHtml;
import com.example.demo.service.IProcessHtml;
import com.example.demo.service.IStoreService;
import com.example.demo.service.impl.AJKDownImplServince;
import com.example.demo.service.impl.AJKProcessService;
import com.example.demo.service.impl.StoreService;

public class StartMain {

	private IDownHtml downHtml;
	private IProcessHtml processHtml;
	private IStoreService storeService;
	
	public static String modelUrl ="https://ks.anjuke.com/sale/o5-p昆山/#filtersort";
	
	
	public static void main(String[] args) {
		StartMain stm = new StartMain();
		stm.setDownHtml(new AJKDownImplServince());
		stm.setProcessHtml(new AJKProcessService());
		stm.setStoreService(new StoreService());
		
		
		saveSeal(stm);
	}
	
	
	public static void saveSeal(StartMain stm) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		
		int count =0;
//		获取page列表
		List<PageEntity> pages = stm.getProcessHtml().getListPage();
		for (PageEntity page : pages) {
//			分析每页数据中有多少个seals
			List<SealEntity> seals = stm.getProcessHtml().analyseHtml(page);
//			保存解析的seal
			for (SealEntity seal : seals) {
				stm.getStoreService().storeSeal(seal);
				System.out.println("保存的seal数量：   "+count++);
			}
		}
		
		System.out.println("转换开始："+DateFormatUtils.format(date, pattern));
		System.out.println("转换结束."+DateFormatUtils.format(new Date(), pattern));
	}
	
	/**
	 * 保存列表页内容
	 * @param modelUrl
	 */
	public static void savePage(String modelUrl, StartMain stm) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		
		for(int i=1;i<51;i++) {
			
			String url = modelUrl.replace("昆山", i+"");
			
			String content = stm.getDownHtml().ajkDownPage(url);//下载
			
			PageEntity page = stm.getProcessHtml().pageHtml(content, url);//转换成对象
			
			stm.getStoreService().storePage(page);//保存
			try {
				Thread.sleep(1000*20); //休息20
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("擦剂开始："+DateFormatUtils.format(date, pattern));
		System.out.println("采集结束."+DateFormatUtils.format(new Date(), pattern));
	}


	public IDownHtml getDownHtml() {
		return downHtml;
	}


	public void setDownHtml(IDownHtml downHtml) {
		this.downHtml = downHtml;
	}


	public IProcessHtml getProcessHtml() {
		return processHtml;
	}


	public void setProcessHtml(IProcessHtml processHtml) {
		this.processHtml = processHtml;
	}


	public IStoreService getStoreService() {
		return storeService;
	}


	public void setStoreService(IStoreService storeService) {
		this.storeService = storeService;
	}
	
}
