package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
public class TransactionalAnnotationTests {

    @Autowired
    private TransactionalUtil transactionalUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    public void usingTransactionalAnnotationTests() {

        int id = 6;

        transactionalUtil.transactional(() -> {
            jdbcTemplate.update("INSERT INTO HELLO2 VALUES(?, ?)", id, "Ciao Mondo");
        });

        String name = jdbcTemplate.queryForObject("SELECT NAME FROM HELLO2 WHERE ID = ?", String.class, id);
        assertEquals("Ciao Mondo", name);

    }
    
    @Test
    public void usingTransactionalAnnotationExceptionTests() {

        int id = 7;

        try {
            transactionalUtil.transactional(() -> {
                jdbcTemplate.update("INSERT INTO HELLO2 VALUES(?, ?)", id, "Ciao Mondo");
                throw new RuntimeException();
            });
        } catch (Exception ex) {
        }

        assertThrows(EmptyResultDataAccessException.class, () -> jdbcTemplate.queryForObject("SELECT NAME FROM HELLO2 WHERE ID = ?", String.class, id));

    }
    
    @Configuration
    @Import({
        DatasourceConfiguration.class,
        TransactionalUtil.class,
    })
    @EnableTransactionManagement
    public static class Conf {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @PostConstruct
        public void initDatabaseH2() {
            jdbcTemplate.execute("CREATE TABLE HELLO2 (ID INT PRIMARY KEY, NAME VARCHAR(255))");
            jdbcTemplate.execute("INSERT INTO HELLO2 VALUES(1, 'Hello, World!')");
        }

        @Bean
        public TransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
}
