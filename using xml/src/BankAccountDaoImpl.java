package com.capgemini.bankapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.util.Dbutil;
import com.capgemini.bankappDao.BankAccountDao;
import com.capgemini.model.BankAccount;

public class BankAccountDaoImpl implements BankAccountDao {

	private Connection connection;

	public BankAccountDaoImpl(Connection connection)
	{
		this.connection=connection;
	}

	@Override
	public double getBalance(long accountId)  {
		// TODO Auto-generated method stub
		String query = "select account_balance from bankaccounts where account_id=" + accountId;
		double balance = -1;

	
		try (
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery();) {
			if(result.next())
			balance = result.getDouble(1);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;

	}

	@Override
	public void updateBalance(long accountId, double newBalance) {
				
		String query="update bankaccounts set account_balance=? where account_id=?";
		
		try(PreparedStatement statement=connection.prepareStatement(query) )
		{
			statement.setDouble(1, newBalance);
			statement.setLong(2, accountId);
			int result=statement.executeUpdate();
			System.out.println(result);
			
			//connection commit....
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public boolean deleteBankaccount(long accountId) {

		String query = "delete from bankaccounts where account_id=" + accountId;
		int result;
	
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			result = statement.executeUpdate();

			if (result == 1) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean addNewbankAccount(BankAccount account)  {

		String query="insert into bankaccounts(customer_name,account_type,account_balance) values(?,?,?)";
		
		
		try(PreparedStatement statement=connection.prepareStatement(query)	)
		{
			statement.setString(1, account.getAccountHolderName());
			statement.setString(2, account.getAccountType());
			statement.setDouble(3, account.getAccountbalance());
			
			int result=statement.executeUpdate();
			if(result==1)
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<BankAccount> findAllbankAccounts() {

		String query="select * from bankaccounts";
		List<BankAccount> accountList=new ArrayList<BankAccount>();
	
		try(PreparedStatement statement=connection.prepareStatement(query))
		{
			ResultSet rs=statement.executeQuery();
		
			while(rs.next())
			{
				long id=rs.getInt(1);
				String name=rs.getString(2);
				String type=rs.getString(3);
				double balance=rs.getDouble(4);
				BankAccount bank=new BankAccount(id,name,type,balance);
				accountList.add(bank);
				
			}
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return accountList;
	}

	@Override
	public BankAccount searchAccount(long accountId) {
		String query="select * from bankaccounts where account_id="+accountId;
		BankAccount bank=null;
		
		try(PreparedStatement statement=connection.prepareStatement(query))
		{
		ResultSet result=statement.executeQuery();

		result.next();
		long id2=result.getInt(1);
		String name1=result.getString(2);
		String type1=result.getString(3);
		double balance1=result.getDouble(4);
		
		bank=new BankAccount(id2,name1,type1,balance1);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bank;
	}

	@Override
	public boolean updateAccount(long accountId, String accountHolderName, String accountType) {
		// TODO Auto-generated method stub
		
		String query="update bankaccounts set account_type=?,customer_name=? where account_id="+accountId;
	
		
		try(PreparedStatement statement=connection.prepareStatement(query) )
		{
			statement.setString(1, accountType);
			statement.setString(2, accountHolderName);
			int result=statement.executeUpdate();
		
			System.out.println(result);
			if(result==1)
			{
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return false;
	}
	
}
