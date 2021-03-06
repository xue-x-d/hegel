package com.shomop.crm.dao;

import com.shomop.crm.model.crm.User;


public interface UserDao extends GenericDao<User, String> {
	
	public User findUserByName(String name);
	
	public User loadUser(String id);
}
