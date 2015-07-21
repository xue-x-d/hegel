package com.shomop.rmi.service;

public interface AccountService {
	int queryBalance(String mobileNo);

	String shoopingPayment(String mobileNo, byte protocol);
}