package com.example.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.demo.entity.DetailEntity;
import com.example.demo.entity.DetailPage;
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
import com.example.demo.service.impl.DetailImpService;
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
		
		saveSeal(stm);
		
		getDetailPage(stm,agentVos);
		
		System.out.println("转换开始："+DateFormatUtils.format(date, pattern));
		System.out.println("转换结束."+DateFormatUtils.format(new Date(), pattern));
		
		
		Runtime.getRuntime().exec("shutdown -s -t 60");
		
	}
	

	public static void saveDetail(String content) {
//		String url = "https://ks.anjuke.com/prop/view/A1408267077?from=filter&spread=filtersearch&position=1&kwtype=filter&now_time=1536938468#";
//		AgentVo agentVo = GetXiciIpAddress.getRandomAgent(agentVos);
//		HttpContent.httpAgent(agentVo);
//		String content = HttpContent.httpUtil(url);
//		while (content.length() < 1024*10) {
//			content = HttpContent.httpUtil(url);
//		}
		if (content == null) {
			return;
		}
		
		DetailEntity detail = new DetailEntity();
		Document root = Jsoup.parse(content);
		Elements elements = root.select(".houseInfoBox");
		if (elements != null && elements.size() > 0) {
			Elements h4s = elements.get(0).select("h4 span.house-encode");
				String[] houseInfo = h4s.get(0).text().split("，");
				if (houseInfo != null && houseInfo.length > 0) {
					detail.setHouseid(houseInfo[0].split("：")[1]);
					detail.setPublictime(houseInfo[1].split("：")[1]);
				}
			Elements uls = elements.get(0).select(".houseInfo-wrap ul.houseInfo-detail-list.clearfix li .houseInfo-content");
			if (uls.size()> 0) {
				detail.setHousetype(uls.get(9).text());
				detail.setBuytimes(uls.get(12).text());
				detail.setOrient(uls.get(7).text());
				detail.setFirstpay(Float.valueOf(uls.get(5).text().replace("万", "")));
				if (uls.get(8).text() != null && uls.get(8).text().length() >0) {
					detail.setMonthpay(Float.valueOf(uls.get(8).text().replace("元", "")));
				}
				detail.setDecoration(uls.get(11).text());
			}
			new DetailImpService().save(detail);
		}
	}

	
	public static void getDetailPage(StartMain stm,List<AgentVo> agentVos) throws IOException {
		String day = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		int start=0,limit =50,i=0;
		List<SealEntity> seals = stm.getSealService().getListSeal(-1, 1,day);
		if (seals != null && seals.size() > 0) {
			start = (int) (seals.get(0).getId());
		}else {
			start = 0;
		}
		DetailPage detailPage = null;

		while(seals != null && seals.size() > 0){
			for (SealEntity seal : seals) {
				detailPage = new DetailPage();
				String content = stm.getDownHtml().ajkDownSealPage(seal.getHerf(),agentVos);
				
				detailPage.setFatherid(seal.getId());
				detailPage.setContent(content== null?"":content);
				saveDetail(content);
			}
			i++;
			start = limit * i;
			seals = stm.getSealService().getListSeal(start, limit,day);
		}
	}
	
	/**
	 * 解析列表界面，并保存
	 * @param stm
	 */
	public static void saveSeal(StartMain stm) {
		int count =0;
		String day = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
//		获取page列表
		List<PageEntity> pages = stm.getPageService().getListPage(day);
		for (PageEntity page : pages) {
//			分析每页数据中有多少个seals
			List<SealEntity> seals = stm.getProcessHtml().analyseHtml(page);
//			保存解析的seal
			for (SealEntity seal : seals) {
				stm.getStoreService().storeSeal(seal);
				System.out.println("保存的seal数量：   "+count++);
			}
			
			if (count % 100 == 0) {
				try {
					Thread.sleep(1000* 3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
