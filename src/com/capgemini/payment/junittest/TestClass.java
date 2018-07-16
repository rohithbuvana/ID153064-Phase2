package com.capgemini.payment.junittest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.payment.bean.Customer;
import com.capgemini.payment.bean.Wallet;
import com.capgemini.payment.exception.InsufficientBalanceException;
import com.capgemini.payment.exception.InvalidInputException;
import com.capgemini.payment.service.WalletService;
import com.capgemini.payment.service.WalletServiceImpl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
public class TestClass {
WalletService service;
Connection con;
	@Before
	public  void initData(){
		service=new WalletServiceImpl();
		try{
			Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/db";
		String uid="root";
		String pwd="corp123";
		con=DriverManager.getConnection(url,uid,pwd);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		String query="insert into customer1(name,mobileno,balance) values('Amit','9900112212',9000)";
		String query1="insert into customer1(name,mobileno,balance) values('Ajay','9963242422',6000)";
		String query2="insert into customer1(name,mobileno,balance) values('Yogini','9922950519',7000)";
		try {
			con.setAutoCommit(false);
			java.sql.Statement stmt= con.createStatement();
			stmt.execute(query);
			stmt.execute(query1);
			stmt.execute(query2);
			con.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testCreateAccount()
	{
		 Customer expectedCustomer=new Customer("Bachan", "9900112213",new Wallet(new BigDecimal(8000)));
		 Customer actualCustomer=service.createAccount("Bachan", "9900112213", new BigDecimal(8000));
		 assertEquals(expectedCustomer.getMobileNo(),actualCustomer.getMobileNo() );
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountMobileException()
	{
		 service.createAccount("Bachan", "9900112", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountMobileException2()
	{
		 service.createAccount("Bachan", "abcdefghij", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountMobileException3()
	{
		 service.createAccount("Bachan", "990011abc", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountNameException()
	{
		 service.createAccount("Bachan123", "990011221", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountNameException2()
	{
		 service.createAccount("Bachan@capgemini", "990011221", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountNameException3()
	{
		 service.createAccount("990011221", "990011221", new BigDecimal(8000));
	}
	@Test (expected=InvalidInputException.class)
	public void testCreateAccountAlreadyExistException()
	{
		 service.createAccount("Amit", "9900112212", new BigDecimal(9000));
	}
	@Test 
	public void testShowBalance()
	{
		Customer customer=service.showBalance("9900112212");
		 assertEquals(new BigDecimal(9000),customer.getWallet().getBalance() );
	}
	@Test 
	public void testShowBalance2()
	{
		Customer customer=service.showBalance("9900112212");
		 assertEquals("Amit",customer.getName() );
	}
	@Test  (expected=InvalidInputException.class)
	public void testShowBalanceDoesNotException()
	{
		service.showBalance("9900112214");
	}
	@Test 
	public void testDepositAmount()
	{
		Customer customer=service.depositAmount("9900112212", new BigDecimal(9000));
		 assertEquals(service.showBalance("9900112212").getWallet().getBalance(),customer.getWallet().getBalance() );
	}
	@Test 
	public void testDepositAmount2()
	{
		Customer customer=service.depositAmount("9900112212", new BigDecimal(9000));
		 assertEquals(service.showBalance("9900112212").getName(),customer.getName());
	}
	@Test  (expected=InvalidInputException.class)
	public void testDepositAmountDoesNotExistsException()
	{
		service.depositAmount("9900112213", new BigDecimal(9000));
	}
	@Test 
	public void testWithdrawAmount()
	{
		Customer customer=service.withdrawAmount("9900112212", new BigDecimal(6000));
		 assertEquals(service.showBalance("9900112212").getWallet().getBalance(),customer.getWallet().getBalance() );
	}
	@Test  (expected=InvalidInputException.class)
	public void testWithdrawAmountDoesNotExistsException()
	{
		service.withdrawAmount("9900112213", new BigDecimal(9000));
	}
	@Test  (expected=InsufficientBalanceException.class)
	public void testWithdrawAmountInsufficientBalanceException()
	{
		service.withdrawAmount("9900112212", new BigDecimal(9001));
	}
	@Test
	public void testFundTransfer()
	{
		Customer customer=service.fundTransfer("9900112212", "9963242422",new BigDecimal(2000));
		assertEquals(service.showBalance("9900112212").getWallet().getBalance(),customer.getWallet().getBalance() );
	}
	@Test  (expected=InsufficientBalanceException.class)
	public void testFundTransferInsufficientBalanceException()
	{
		service.fundTransfer("9900112212", "9963242422",new BigDecimal(9001));
	}
	@Test  (expected=InvalidInputException.class)
	public void testFundTransferSourceNumberException()
	{
		service.fundTransfer("9900112213", "9963242422",new BigDecimal(2000));
	}
	@Test  (expected=InvalidInputException.class)
	public void testFundTransfertargetNumberException()
	{
		service.fundTransfer("9900112212", "9963242423",new BigDecimal(2000));
	}
	@Test
	public void testFundTransferDebitCheck()
	{
		service.fundTransfer("9900112212", "9963242422",new BigDecimal(2000));
		Customer customer=service.showBalance("9963242422");
		assertEquals(new BigDecimal(8000),customer.getWallet().getBalance() );
	}
	@After
	public  void clear(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/db";
		String uid="root";
		String pwd="corp123";
		con=DriverManager.getConnection(url,uid,pwd);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		String query="delete from customer1 where mobileno='9900112212'";
		String query1="delete from customer1 where mobileno='9963242422'";
		String query2="delete from customer1 where mobileno='9922950519'";
		String query3="delete from customer1 where mobileno='9900112213'";
		try {
			con.setAutoCommit(false);
			java.sql.Statement stmt= con.createStatement();
			stmt.execute(query);
			stmt.execute(query1);
			stmt.execute(query2);
			stmt.execute(query3);
			con.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
	}
}
