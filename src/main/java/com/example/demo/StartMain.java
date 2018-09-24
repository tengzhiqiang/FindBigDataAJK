package com.example.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IDetailPageService;
import com.example.demo.service.IDownHtml;
import com.example.demo.service.IPageService;
import com.example.demo.service.IProcessHtml;
import com.example.demo.service.ISealService;
import com.example.demo.service.IStoreService;
import com.example.demo.service.impl.AJKDownImplServince;
import com.example.demo.service.impl.AJKProcessService;
import com.example.demo.service.impl.DetailPageImpService;
import com.example.demo.service.impl.PageImpService;
import com.example.demo.service.impl.SealImpService;
import com.example.demo.service.impl.StoreService;
import com.example.demo.util.AgentVo;
import com.example.demo.xici.GetXiciIpAddress;

public class StartMain {

	private IDownHtml downHtml;
	private IProcessHtml processHtml;
	private IStoreService storeService;
	private IDetailPageService detailPageService;
	private IPageService pageService;
	private ISealService sealService;

	public static String modelUrl ="https://ks.anjuke.com/sale/o5-p昆山/#filtersort";
	
	
	public static void main(String[] args) throws IOException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		StartMain stm = new StartMain();
		stm.setDownHtml(new AJKDownImplServince());
		stm.setProcessHtml(new AJKProcessService());
		stm.setStoreService(new StoreService());
		stm.setDetailPageService(new DetailPageImpService());
		stm.setPageService(new PageImpService());
		stm.setSealService(new SealImpService());
		
		List<AgentVo> agentVos = GetXiciIpAddress.getListAgents();
		
		savePage(modelUrl, stm, agentVos);
		
		saveSeal(stm,agentVos);
		
		
		System.out.println("转换开始："+DateFormatUtils.format(date, pattern));
		System.out.println("转换结束."+DateFormatUtils.format(new Date(), pattern));
		
		
//		Runtime.getRuntime().exec("shutdown -s -t 60");
		
	}
	
	/**
	 * 解析列表界面，并保存
	 * @param stm
	 */
	public static void saveSeal(StartMain stm,List<AgentVo> agentVos) {
		int count =0;
		String day = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
//		获取page列表
		List<PageEntity> pages = stm.getPageService().getListPage(day);
		for (PageEntity page : pages) {
//			分析每页数据中有多少个seals
			List<SealEntity> seals = stm.getProcessHtml().analyseHtml(page);
//			保存解析的seal
			for (SealEntity seal : seals) {
				
				String content = stm.getDownHtml().ajkDownSealPage(seal.getHerf(),agentVos);
				Document root = Jsoup.parse(content);
				Elements elements = root.select(".houseInfoBox");
				if (elements != null && elements.size() > 0) {
					Elements h4s = elements.get(0).select("h4 span.house-encode");
						String[] houseInfo = h4s.get(0).text().split("，");
						if (houseInfo != null && houseInfo.length > 0) {
							seal.setHouseid(houseInfo[0].split("：")[1]);
							seal.setPublictime(houseInfo[1].split("：")[1]);
						}
					Elements uls = elements.get(0).select(".houseInfo-wrap ul.houseInfo-detail-list.clearfix li .houseInfo-content");
					if (uls.size()> 0) {
						seal.setHouseclass(uls.get(9).text());
						seal.setBuytimes(uls.get(12).text());
						seal.setOrient(uls.get(7).text());
						seal.setFirstpay(Float.valueOf(uls.get(5).text().replace("万", "")));
						if (uls.get(8).text() != null && uls.get(8).text().length() >0) {
							seal.setMonthpay(Float.valueOf(uls.get(8).text().replace("元", "")));
						}
						seal.setDecoration(uls.get(11).text());
					}
				}
				
				stm.getStoreService().storeSeal(seal);
				System.out.println("保存的seal数量：   "+count++);
			}
		}
		
	}
	
	/**
	 * 保存列表页内容
	 * @param modelUrl
	 */
	public static void savePage(String modelUrl, StartMain stm ,List<AgentVo> agentVos) {
		String content = null;
		for(int i=1;i<51;i++) {
			
			String url = modelUrl.replace("昆山", i+"");
			content = stm.getDownHtml().ajkDownPage(url,agentVos);//下载
//			while是为了防止请求失败
			PageEntity page = stm.getProcessHtml().pageHtml(content, url);//转换成对象
			while ( page.getContent() == null) {
				try {
					System.err.print("***********************休息60s，保存page出现网络错误*******");
					Thread.sleep(1000*60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				content = stm.getDownHtml().ajkDownPage(url,agentVos);//下载
				page = stm.getProcessHtml().pageHtml(content, url);//转换成对象
			}
			
			stm.getStoreService().storePage(page);//保存
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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

	public IPageService getPageService() {
		return pageService;
	}

	public void setPageService(IPageService pageService) {
		this.pageService = pageService;
	}

	public ISealService getSealService() {
		return sealService;
	}

	public void setSealService(ISealService sealService) {
		this.sealService = sealService;
	}
	public IDetailPageService getDetailPageService() {
		return detailPageService;
	}


	public void setDetailPageService(IDetailPageService detailPageService) {
		this.detailPageService = detailPageService;
	}
	
}
