package com.capgemini.payment.service;

import java.math.BigDecimal;

import com.capgemini.payment.bean.Customer;
import com.capgemini.payment.exception.InvalidInputException;

public interface WalletService {
	
	public Customer createAccount(String name ,String mobileno, BigDecimal amount) throws InvalidInputException;
	public Customer showBalance (String mobileno);
	public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount);
	public Customer depositAmount (String mobileNo,BigDecimal amount );
	public Customer withdrawAmount(String mobileNo, BigDecimal amount);
}
