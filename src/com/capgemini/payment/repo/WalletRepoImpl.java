package com.capgemini.payment.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.capgemini.payment.bean.Customer;


public class WalletRepoImpl implements WalletRepo {
	

	Connection con;
	public WalletRepoImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/db";
		String uid="root";
		String pwd="corp123";
		this.con=DriverManager.getConnection(url,uid,pwd);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	

	public boolean save(Customer customer) {
		if(findOne(customer.getMobileNo())==null)
		{
			String query="insert into customer1(name,mobileno,balance) values(\""+customer.getName() +"\",\""+customer.getMobileNo() +"\","+customer.getWallet().getBalance() +")";
			try {
				con.setAutoCommit(false);
				java.sql.Statement stmt= con.createStatement();
				stmt.execute(query);
				con.commit();
				return true;
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
			
		}
		else 
			{
			String query=" update customer1 set balance="+customer.getWallet().getBalance()+" where mobileno="+customer.getMobileNo();
			try {
				con.setAutoCommit(false);
				java.sql.Statement stmt1= con.createStatement();
				stmt1.execute(query);
				con.commit();
				return true;
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
			
	}
	}

	public Customer findOne(String mobileNo) {
		Customer customer=new Customer();
		int flag=0;
		String query="select * from customer1 where mobileno="+ mobileNo ;
		try {
			con.setAutoCommit(false);
			java.sql.Statement stmt= con.createStatement();
			ResultSet rs=stmt.executeQuery(query);
			if(rs.next())
			{
				customer.setName(rs.getString(1));
				customer.setMobileNo(rs.getString(2));
				customer.getWallet().setBalance(rs.getBigDecimal(3));
				flag=1;
			}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
			if (flag == 0) {
				return null;
			}
		
			return customer;
		
	}
}

