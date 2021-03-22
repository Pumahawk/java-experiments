package com.pumahawk;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseH2Configuration {

	@Autowired
	Connection connection;
	
	@Bean
	public DataSource dataSourceH2() {
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setUrl("jdbc:h2:mem:");
		datasource.setUser("SA");
		datasource.setPassword("");
		return datasource;
	}
	
	@Bean
	public Connection connection(DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}
	
	@PostConstruct
	private void initDatabaseH2() throws SQLException {
		connection.setAutoCommit(true);
		connection.createStatement().execute("CREATE TABLE HELLO (ID INT PRIMARY KEY, NAME VARCHAR(255))");
		connection.createStatement().execute("INSERT INTO HELLO VALUES(1, 'Hello, World!')");
	}
}
