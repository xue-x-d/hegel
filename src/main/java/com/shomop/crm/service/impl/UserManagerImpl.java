package com.shomop.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shomop.crm.dao.UserDao;
import com.shomop.crm.model.User;
import com.shomop.crm.service.UserManager;



@Service("userManager")
public class UserManagerImpl implements UserManager {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void save(User user) {
		userDao.saveOrUpdate(user);
	}

}
