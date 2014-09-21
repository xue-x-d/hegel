package com.shomop.crm.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.shomop.crm.model.si.SI_Item;
import com.shomop.crm.test.BaseTestCase;

public class SI_ItemTest extends BaseTestCase {

	
	@Test
	@Rollback(value=false)
	public void testFloat(){
		Session session = getCurrentSession();
		SI_Item item = new SI_Item();
		item.setTitle("title");
		item.setCid(1L);
		item.setDescription("si_item");
		item.setItemStatus(1);
		item.setPicUrl("pic");
		item.setPrice(10.233222);
		item.setShopId(1L);
		session.save(item);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testGet(){
		Session session = getCurrentSession();
		Query query = session.createQuery("from SI_Item where price=:price");
		query.setDouble("price", 10.233222);
		List<SI_Item> items = (List<SI_Item>) query.list();
		for (SI_Item item : items) {
			System.out.println(item.getNumIid() + " "+item.getPrice());
		}
	}
	
	public static void main(String[] args) {
		new BigDecimal(0.1);
		System.out.println((int)1023.99999999999999);
	}
}
