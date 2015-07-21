package com.shomop.rmi.service.impl;

import org.apache.log4j.Logger;

import com.shomop.rmi.service.AccountService;

public class MobileAccountServiceImpl implements AccountService {

	private static final Logger LOG = Logger
			.getLogger(MobileAccountServiceImpl.class);

	public int queryBalance(String mobileNo) {
		if (mobileNo != null)
			return 100;
		return 0;
	}

	public String shoopingPayment(String mobileNo, byte protocol) {  
        StringBuffer sb = new StringBuffer().append("Your mobile number is \"").append(  
                mobileNo).append("\", protocol type is \"").append(protocol)  
                .append("\".");  
        LOG.info("Message is: " + sb.toString());  
        return sb.toString();  
    }}