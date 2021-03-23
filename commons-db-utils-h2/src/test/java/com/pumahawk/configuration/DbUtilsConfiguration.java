package com.pumahawk.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbUtilsConfiguration {
	
	@Bean
	public QueryRunner queryRunner(DataSource dataSource) {
		return new QueryRunner(dataSource);
	}

}
