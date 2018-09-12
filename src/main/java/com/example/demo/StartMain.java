package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.example.demo.entity.DetailPage;
import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;
import com.example.demo.service.IDownHtml;
import com.example.demo.service.IProcessHtml;
import com.example.demo.service.IStoreService;
import com.example.demo.service.impl.AJKDownImplServince;
import com.example.demo.service.impl.AJKProcessService;
import com.example.demo.service.impl.StoreService;
import com.example.demo.util.AgentVo;
import com.example.demo.util.GetXiciIpAddress;
import com.example.demo.util.HttpContent;

public class StartMain {

	private IDownHtml downHtml;
	private IProcessHtml processHtml;
	private IStoreService storeService;
	
	public static String modelUrl ="https://ks.anjuke.com/sale/o5-p昆山/#filtersort";
	
	
	public static void main(String[] args) throws IOException {
		StartMain stm = new StartMain();
		stm.setDownHtml(new AJKDownImplServince());
		stm.setProcessHtml(new AJKProcessService());
		stm.setStoreService(new StoreService());
		
		
		getDetailPage(stm);
	}
	
	public static void getDetailPage(StartMain stm) throws IOException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		
		int start=0,limit =50,i=0;
		List<SealEntity> seals = stm.getProcessHtml().getListSeal(start, limit);
		DetailPage detailPage = null;
		
		List<AgentVo> agents = GetXiciIpAddress.getListAgents();
		AgentVo agentVo = null;
		while(seals != null){
			
//			for (SealEntity seal : seals) {
//				detailPage = new DetailPage();
//				String content = stm.getDownHtml().ajkDownSealPage(seal.getHerf());
//				detailPage.setContent(content);
//				detailPage.setFatherid(seal.getId());
//				stm.getStoreService().storeDetailPge(detailPage);
//				try {
//					Thread.sleep(1000*4);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			try {
//				Thread.sleep(1000*10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			
			for (SealEntity seal : seals) {
				agentVo = GetXiciIpAddress.getRandomAgent(agents);
				HttpContent.httpAgent(agentVo);
				detailPage = new DetailPage();
				String content = stm.getDownHtml().ajkDownSealPage(seal.getHerf());
				String pathString = seal.getId()+"";
				if (content != null) {
					pathString = stm.creatFile("D:\\2018\\", seal.getId(), content);
				}
				detailPage.setFatherid(seal.getId());
				detailPage.setContent(pathString);
				stm.getStoreService().storeDetailPge(detailPage);
			}
			try {
				System.out.println("休息10s**********");
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
			start = limit * i;
			seals = stm.getProcessHtml().getListSeal(start, limit);
		}
		System.out.println("转换开始："+DateFormatUtils.format(date, pattern));
		System.out.println("转换结束."+DateFormatUtils.format(new Date(), pattern));
	}
	
	public String creatFile(String path, long fatherid, String content){
		FileWriter fw = null;
		
		String name = "sealPage_" + fatherid + ".txt";
		File file = new File(path + name);
		file.delete();
		try {
			file.createNewFile();
			fw = new FileWriter(file);
			
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file.getName();
	}
	
	
	/**
	 * 解析列表界面，并保存
	 * @param stm
	 */
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
