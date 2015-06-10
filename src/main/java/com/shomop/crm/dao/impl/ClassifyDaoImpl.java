package com.shomop.crm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.shomop.crm.dao.ClassifyDao;
import com.shomop.crm.model.shop.Classify;

@Repository("classifyDao")
@SuppressWarnings("unchecked")
public class ClassifyDaoImpl extends GenericDaoImpl<Classify, Integer> implements ClassifyDao {

	@Override
	public List<Classify> getUnfinishedShopClassifies(int limit) {
		Query query = getCurrentSession().createQuery(
				"from Classify where status=:status order by id");
		query.setInteger("status", 0);
		query.setMaxResults(limit);
		return (List<Classify>) query.list();
	}

     
}
