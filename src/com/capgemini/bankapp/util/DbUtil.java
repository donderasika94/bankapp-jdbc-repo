package com.capgemini.bankapp.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.*;

public class DbUtil {
	private static String dburl;
	private static String username;
	private static String password;
	
	static Connection connection;
	static final Logger logger = Logger.getLogger(DbUtil.class);

	public static Connection getConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connection == null) {
			connection = DriverManager.getConnection(dburl, username, password);
			connection.setAutoCommit(false);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void commit() {
		try {
			if(connection != null)
				connection.commit();
		}catch(SQLException e) {
			logger.error("SQLException ", e);
		}
		
	}
	public static void rollback() {
		try {
			if(connection != null)
				connection.rollback();
		}catch(SQLException e) {
			logger.error("SQLException ", e);
		}
	}

	static {
		try {
			File propertiesFile = new File("dbConfig.properties");
			FileReader reader = new FileReader(propertiesFile);

			Properties properties = new Properties();
			properties.load(reader);
			reader.close();

			dburl = properties.getProperty("dburl");
			username = properties.getProperty("username");
			password = properties.getProperty("password");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
