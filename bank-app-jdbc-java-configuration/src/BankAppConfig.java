package com.capgemini.bankapp.config;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.capgemini.bankapp.util.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.capgemini.bankapp.client.*;
import com.capgemini.bankapp.dao.impl.*;
import com.capgemini.bankapp.service.impl.*;
import com.capgemini.bankapp.dao.*;
import com.capgemini.bankapp.service.*;

@Configuration
public class BankAppConfig{

	public Properties getProperties(){
		Properties properties=null;

		try {
			File propertiesFile = new File("src/dbconfig.properties");
			FileReader fileReader = new FileReader(propertiesFile);

			properties = new Properties();
			properties.load(fileReader);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return properties;
	}

	public Connection getConnection(){
		Connection connection=DbUtil.getConnection(getProperties());
		return connection;
	}

	public BankAccountDaoImpl getBankAccountDaoImpl(){
		BankAccountDaoImpl bankAccountDaoImpl=new BankAccountDaoImpl(getConnection());
		return bankAccountDaoImpl;
	}

	@Bean
	public BankAccountServiceImpl getBankAccountServiceImpl(){
		BankAccountServiceImpl bankAccountServiceImpl=new BankAccountServiceImpl(getBankAccountDaoImpl());
		return bankAccountServiceImpl;
	}
}