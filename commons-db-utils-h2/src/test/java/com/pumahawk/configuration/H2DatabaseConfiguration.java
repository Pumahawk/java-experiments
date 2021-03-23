package com.pumahawk.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class H2DatabaseConfiguration {
	
	@Value("classpath:h2-ddl.sql")
	private Resource h2DDLFile;
	
	@Value("classpath:h2-dml.sql")
	private Resource h2DMLFile;
	
	@Autowired
	private Connection connection;
	
	@Bean
	public DataSource dataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		dataSource.setUser("SA");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public Connection connection(DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}
	
	@PostConstruct
	public void initDatabase() throws FileNotFoundException, SQLException, IOException {
		RunScript.execute(connection, new FileReader(h2DDLFile.getFile()));
		RunScript.execute(connection, new FileReader(h2DMLFile.getFile()));
	}

}
