package com.pumahawk;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableJdbcRepositories
public class DatabaseH2Configuration extends AbstractJdbcConfiguration{


	@Bean
	public DataSource dataSourceH2() {
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;AUTOCOMMIT=OFF");
		datasource.setUser("SA");
		datasource.setPassword("");
		return datasource;
	}
	
	@Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) { 
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    TransactionManager transactionManager(DataSource dataSource) {                     
        return new DataSourceTransactionManager(dataSource);
    }

	public static void initDatabaseH2(Connection connection) throws SQLException {
		connection.setAutoCommit(true);
		connection.createStatement().execute("CREATE TABLE HELLO (ID INT PRIMARY KEY, NAME VARCHAR(255))");
		connection.createStatement().execute("INSERT INTO HELLO VALUES(1, 'Hello, World!')");
	}
}
