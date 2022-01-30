package com.pumahawk;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableJdbcRepositories
@Import(DatasourceConfiguration.class)
public class DatabaseH2Configuration extends AbstractJdbcConfiguration{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) { 
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    DataSourceTransactionManager transactionManager(DataSource dataSource) {                     
        return new DataSourceTransactionManager(dataSource);
    }

	@PostConstruct
	public void initDatabaseH2() {
		jdbcTemplate.execute("CREATE TABLE HELLO (ID INT PRIMARY KEY, NAME VARCHAR(255))");
		jdbcTemplate.execute("INSERT INTO HELLO VALUES(1, 'Hello, World!')");
	}

	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
