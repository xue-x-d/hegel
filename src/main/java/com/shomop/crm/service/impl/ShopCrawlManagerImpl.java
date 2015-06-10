package com.shomop.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shomop.crm.dao.ClassifyDao;
import com.shomop.crm.dao.CrownShopDao;
import com.shomop.crm.model.shop.Classify;
import com.shomop.crm.service.ShopCrawlManager;
import com.shomop.util.ShopCrawlerUtil;

@Service("shopCrawlManager")
public class ShopCrawlManagerImpl implements ShopCrawlManager{
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private ClassifyDao classifyDao;
	@Autowired
	private CrownShopDao crownShopDao;
	
	@Override
	public void saveAllClassifies() {
		try {
			List<Classify> classifies = ShopCrawlerUtil.fetchClassifyList();
			classifyDao.saveOrIgnoreByJdbc(classifies,false);
			// synShopList(classifyDao.findAll());
		} catch (Exception e) {
			logger.error(">>> error in syn classify.",e);
		}
	}

	@Override
	public void saveShopListByCId(Integer cId) {
		List<Classify> list = new ArrayList<Classify>();
		list.add(classifyDao.find(cId));
		synShopList(list);
	}
	
	
	@Override
	public void saveShopListOfClassify(Classify classify) {
		if(classify == null || classify.getStatus() != 0){
			return;
		}
		List<Classify> list = new ArrayList<Classify>();
		list.add(classify);
		synShopList(list);
	}
	
	@Override
	public void saveClassifyOfShopOneByOne() {
		synShopList(classifyDao.getUnfinishedShopClassifies(1));
	}
	
	
	/**
	 * 同步失败自动尝试3次
	 * @param classifies
	 */
	private void synShopList(List<Classify> classifies) {
		int retryTimes = 0;
		for (int index = 0; index < classifies.size(); index++) {
			Classify classify = classifies.get(index);
			int result = ShopCrawlerUtil.fetchShopListByCId(classify,
					crownShopDao,20);
			try {
				if (result == -1) {
					if (retryTimes++ > 3) {
						classifyDao.saveOrUpdate(classify);
						logger.error(">>> 同步中断超过3次，需要等待了。。");
						break;
					}
					index--;
					Thread.sleep(10000L * retryTimes);
					logger.debug("<<< classify name:" + classify.getName()
							+ " syn failed.forbidden by taobao.retry time: "
							+ retryTimes);
				} else if (result == 0) {
					classifyDao.saveOrUpdate(classify);
					System.out.println("本次任务完成了。休息一下！time: "
							+ System.currentTimeMillis());
					break;
				} else {
					retryTimes = 0;
					Thread.sleep(2000L);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
