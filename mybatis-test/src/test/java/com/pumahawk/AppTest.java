package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pumahawk.mapper.HelloMapper;

/**
 * Unit test for simple App.
 */
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.pumahawk")
@EnableJpaRepositories
public class AppTest {

	@Configuration
	public static class Conf {

		@Bean
		public DataSource datasource() {
			BasicDataSource basicDatasource = new BasicDataSource();
			basicDatasource.setDriverClassName("org.h2.Driver");
			basicDatasource.setUrl("jdbc:h2:mem:test");
			basicDatasource.setUsername("SA");
			basicDatasource.setPassword("");
			return basicDatasource;
		}
		
		@Bean
		public Environment ibatisEnv(DataSource dataSource) {
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("development", transactionFactory, dataSource);
			return environment;
		}
		
		@Bean
		public org.apache.ibatis.session.Configuration ibatisConfiguration(Environment environment) {
			org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
			configuration.addMapper(HelloMapper.class);
			return configuration;
		}

		@Bean
		public SqlSessionFactory sqlSessionFactory(org.apache.ibatis.session.Configuration ibatisConfiguration) {
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(ibatisConfiguration);
			return sqlSessionFactory;
		}
		
		@Bean
		public SqlSession slSession(SqlSessionFactory sqlSessionFactory) {
			return sqlSessionFactory.openSession();
		}
		
		@Bean
		public HelloMapper helloMapper(org.apache.ibatis.session.Configuration ibatisConfiguration, SqlSession sqlSession) {
			return ibatisConfiguration.getMapper(HelloMapper.class, sqlSession);
		}

	}

	@Autowired
	HelloMapper helloMapper;

	@Test
	@Sql(statements = "CREATE MEMORY TEMP TABLE IF NOT EXISTS HELLO (ID INT PRIMARY KEY, NAME VARCHAR(255))")
	public void shouldAnswerWithTrue() {
		assertDoesNotThrow(() -> helloMapper.getHelloById(BigDecimal.ZERO));
	}
}
