package com.pumahawk;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfiguration {

	@Bean
	public DataSource dataSource() {
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		datasource.setUser("SA");
		datasource.setPassword("");
		return datasource;
	}
    
}
