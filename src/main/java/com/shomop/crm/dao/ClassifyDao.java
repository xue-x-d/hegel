package com.shomop.crm.dao;

import java.util.List;

import com.shomop.crm.model.shop.Classify;

public interface ClassifyDao extends GenericDao<Classify, Integer> {

	
	public List<Classify> getUnfinishedShopClassifies(int limit);
}
