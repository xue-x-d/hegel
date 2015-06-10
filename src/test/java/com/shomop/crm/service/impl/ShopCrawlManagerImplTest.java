package com.shomop.crm.service.impl;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.shomop.crm.service.ShopCrawlManager;
import com.shomop.crm.test.BaseTestCase;

public class ShopCrawlManagerImplTest extends BaseTestCase{

	@Autowired
	private ShopCrawlManager shopCrawlManager ;

	//@Test
	@Rollback(false)
	public void testSynAllClassifies() {
		shopCrawlManager.saveAllClassifies();
	}

	@Test
	@Rollback(false)
	public void testSynShopListByClassify() {
		//shopCrawlManager.saveShopListByCId(1);
		Timer timer = new Timer("synCrownShopThread", true);
		// 下载5页需要5分钟
		timer.schedule(new SynCrownShopThread(shopCrawlManager), 60 * 1000L, 10 * 60 * 1000L);
		try {
			Thread.sleep(2*86400*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class SynCrownShopThread extends TimerTask {

	private final ShopCrawlManager shopCrawlManager;

	public SynCrownShopThread(ShopCrawlManager shopCrawlManager) {
		this.shopCrawlManager = shopCrawlManager;
	}

	@Override
	public void run() {
		shopCrawlManager.saveClassifyOfShopOneByOne();
	}
}
