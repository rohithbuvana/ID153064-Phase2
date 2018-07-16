package com.capgemini.payment.service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.payment.bean.Customer;
import com.capgemini.payment.bean.Wallet;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidInputException;
import com.capgemini.payment.repo.WalletRepo;
import com.capgemini.payment.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {
private WalletRepo repo;
{
	repo=new WalletRepoImpl();
}
public WalletServiceImpl(WalletRepo repo) {
	super();
	this.repo = repo;
}

	public WalletServiceImpl() {
}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(mobileNo);
		if(mobileNo.length() != 10 || !matcher.matches()) {
			throw new InvalidInputException("Invalid mobile no.......length should be 10\nRenter");
		}
		 pattern = Pattern.compile("[A-Z a-z]*");
	     matcher = pattern.matcher(name);
	     if(!matcher.matches()) {
				throw new InvalidInputException("Invalid name...... should be composed only of characters no special characters or numbers\nRenter.... ");
			}
	     if(repo.findOne(mobileNo)!=null)
	     {
	    	 throw new InvalidInputException("Mobile number already exists\nRenter.... "); 
	     }
		Customer customer=new Customer(name,mobileNo,new Wallet(amount));
		
		boolean creation=repo.save(customer);
				if(creation)
					return customer;
				else
					return null;
		}

	public Customer showBalance(String mobileNo) {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no \n Renter... ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		withdrawAmount(sourceMobileNo,amount);
		depositAmount(targetMobileNo,amount);
		return showBalance(sourceMobileNo);
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer=repo.findOne(mobileNo);
		if(customer==null)
			throw new InvalidInputException("Invalid mobile no to deposit ");
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
		repo.save(customer);
		return repo.findOne(mobileNo);
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer=repo.findOne(mobileNo);
		if(customer==null)
			throw new InvalidInputException("Invalid mobile no to withdraw ");
		if(customer.getWallet().getBalance().subtract(amount).compareTo(new BigDecimal(0))<0)
			throw new InsufficientBalanceException("Insufficient Balance to withdraw....");
		customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
		repo.save(customer);
		return repo.findOne(mobileNo);

}
	
	}
