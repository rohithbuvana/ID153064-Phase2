package com.capgemini.payment.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.payment.bean.Customer;
import com.capgemini.payment.service.WalletService;
import com.capgemini.payment.service.WalletServiceImpl;

public class Client {
public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	WalletService service;
	{
		service=new WalletServiceImpl();
	}
	int choice;
	char iterator;
	
	do
	{
		System.out.println("\t Menu\n"
				+ "1.Create Account\n"
				+ "2.Show Balance\n"
				+ "3.Funds Transfer\n"
				+ "4.Deposit Amount\n"
				+ "5.Withdraw Amount\n"
				+ "6.Exit"
				+ "\nEnter your choice: ");
		choice=sc.nextInt();
		switch(choice)
		{
		case 1:
			Customer customer;
			int no;
			System.out.println("Enter the number of customers to be added ");
			no=sc.nextInt();
			for(int i=0;i<no;i++)
			{
			while(true)
			{
			try
			{
			System.out.println("Enter your Name (Only Characters): ");
			String name=(sc.next());
			System.out.println("Enter your Mobile Number (10 digit numbers): ");
			String mobileNo=(sc.next());
			System.out.println("Enter your Balance: ");
			BigDecimal amount=(sc.nextBigDecimal());
			customer=service.createAccount(name, mobileNo, amount);
			break;
			}
			catch(Exception e)
			{
				
				System.err.println(e.getMessage());
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			}
			if(customer !=null)
			{
				System.out.println("Customer account created details are as follows....");
				System.out.println("Name: "+customer.getName());
				System.out.println("Name: "+customer.getMobileNo());
				System.out.println("Name: "+customer.getWallet().getBalance());
			}
			else
			{
				System.out.println("Account cannot be created...");
			}
			}
			break;
		case 2:
			System.out.println("Enter the mobile no of existing account: ");
			String mobileNo=sc.next();
			try
			{
				 customer=service.showBalance(mobileNo);
				 System.out.println("Name : "+customer.getName());
				 System.out.println("Balance : "+customer.getWallet().getBalance());
			}
			catch(Exception e)
			{
				
				System.err.println(e.getMessage());
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case 3:
			System.out.println("Enter the mobile no of the account to which amount is to be withdrawn");
			String sMobileNo=sc.next();
			System.out.println("Enter the mobile no of the account to which amount is to be deposited");
			String tMobileNo=sc.next();
			System.out.println("Enter the amount to be transferred :");
			BigDecimal amount=sc.nextBigDecimal();
			try
			{
			customer=service.fundTransfer(sMobileNo, tMobileNo, amount);
			System.out.println("New Balance after transfer is "+customer.getWallet().getBalance());
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			break;
		case 4:
			System.out.println("Enter the mobile no of the account to which amount is to be deposited");
			mobileNo=sc.next();
			System.out.println("Enter the amount to be deposited :");
			amount=sc.nextBigDecimal();
			try
			{
			customer=service.depositAmount(mobileNo, amount);
			System.out.println("New Balance after deposit is "+customer.getWallet().getBalance());
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			break;
		case 5:
			System.out.println("Enter the mobile no of the account to which amount is to be withdrawn");
			mobileNo=sc.next();
			System.out.println("Enter the amount to be withdrawn :");
			amount=sc.nextBigDecimal();
			try
			{
			customer=service.withdrawAmount(mobileNo, amount);
			System.out.println("New Balance after withdraw is "+customer.getWallet().getBalance());
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			break;
		case 6:
				System.exit(1);
				break;
		default:
			System.out.println("Invalid case");
			break;
			}
		System.out.println("Do you want to continue (Y/N) : ");
		iterator=sc.next().toLowerCase().charAt(0);
	}while(iterator=='y');
}
}
