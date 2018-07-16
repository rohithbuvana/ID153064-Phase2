package com.capgemini.payment.repo;

import com.capgemini.payment.bean.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);
	
	public Customer findOne(String mobileNo);
}
