package com.capgemini.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.capgemini.bankapp.service.impl.*;
import com.capgemini.bankapp.service.*;
import com.capgemini.bankapp.util.*;
import java.util.*;
import java.io.*;
import com.capgemini.bankapp.dao.impl.*;
import com.capgemini.bankapp.dao.*;
import java.sql.Connection;



@Configuration
public class BankConfiguration
{

    @Bean
	public BankAccountServiceImpl getServiceImpl()
	{
		return new BankAccountServiceImpl(getDaoImpl());
	}
	
    @Bean
	public BankAccountDaoImpl getDaoImpl()
	{
		return new BankAccountDaoImpl(getDbConnection());
	}
    

    @Bean
	public Connection getDbConnection()
	{
        	Dbutil get=new Dbutil();
		return get.getConnection(getDatabaseDetails());
	}

    @Bean
	public Properties getDatabaseDetails() {
		FileReader reader=null;
		Properties databaseDetails=null;
		try{
			reader=new FileReader("src/dbConfig.properties"); 
      			databaseDetails=new Properties();  
    			databaseDetails.load(reader);
		}catch(Exception e){
			e.printStackTrace();
		 } 
		return databaseDetails;
	}

   

}