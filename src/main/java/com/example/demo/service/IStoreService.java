package com.example.demo.service;

import com.example.demo.entity.DetailPage;
import com.example.demo.entity.PageEntity;
import com.example.demo.entity.SealEntity;

public interface IStoreService {
	public  void storePage(PageEntity page);
	public  void storeSeal(SealEntity sealEntity);
	public  void storeDetailPge(DetailPage detailPage);
}
