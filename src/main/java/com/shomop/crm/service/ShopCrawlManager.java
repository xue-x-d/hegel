package com.shomop.crm.service;

import com.shomop.crm.model.shop.Classify;

/**
 * 获取皇冠店铺
 * @author spencer.xue
 * @date 2015-1-21
 */
public interface ShopCrawlManager {
  
	/**
	 * 同步所有类目信息
	 */
	public void saveAllClassifies();
	
	/**
	 * 同步指定类目店铺信息
	 * @param cId
	 */
	public void saveShopListByCId(Integer cId);
	
	/**
	 * 同步指定类目店铺信息
	 * @param classify
	 */
	public void saveShopListOfClassify(Classify classify);
	
	/**
	 * 处理待同步的类目
	 * @param cId
	 */
	public void saveClassifyOfShopOneByOne();
	
}
