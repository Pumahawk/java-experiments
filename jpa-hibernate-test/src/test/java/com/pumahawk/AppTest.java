package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@ComponentScan(basePackages = "com.pumahawk")
@EnableJpaRepositories
public class AppTest {
	
	@Configuration
	public static class Conf {
		
		@Autowired
		DataSource dataSource;
		
		@Bean
		public DataSource datasource() {
			return DataSourceBuilder
				.create()
				.driverClassName("org.h2.Driver")
				.url("jdbc:h2:mem:test")
				.username("SA")
				.password("")
				.build();
		}
		
		@Bean
		public PlatformTransactionManager transactionManager() {
		    JpaTransactionManager transactionManager = new JpaTransactionManager();
		    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		    return transactionManager;
		}
		
		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			
			em.setDataSource(dataSource);
			em.setPackagesToScan("com.pumahawk");
			
			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			vendorAdapter.setGenerateDdl(true);
		    em.setJpaVendorAdapter(vendorAdapter);
			return em;
		}
	}
	
	@Autowired
	HelloRepository helloRepository;
	
	@Test
	public void shouldAnswerWithTrue() {
		HelloDAO helloDAO = new HelloDAO();
		
		helloDAO.setId(BigDecimal.ZERO);
		helloDAO.setName("NameDao");
		assertDoesNotThrow(() -> helloRepository.save(helloDAO));
	}
}
