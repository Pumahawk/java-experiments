package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootTest
public class DatabaseH2ConfigurationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void loadContext() throws SQLException { 
    }

    @Test
    public void initDatabaseH2() {

        jdbcTemplate.update("INSERT INTO HELLO VALUES(?, ?)", 2, "Ciao Mondo");

        String name = jdbcTemplate.queryForObject("SELECT NAME FROM HELLO WHERE ID = ?", String.class, 2);
        assertEquals("Ciao Mondo", name);

    }

    @Test
    public void tryTransactionalRoolback() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        jdbcTemplate.update("INSERT INTO HELLO VALUES(?, ?)", 3, "Ciao Mondo");

        transactionManager.rollback(transaction);

        assertThrows(EmptyResultDataAccessException.class, () -> jdbcTemplate.queryForObject("SELECT NAME FROM HELLO WHERE ID = ?", String.class, 3));

    }

    @Test
    public void transactionMultiThread() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
            jdbcTemplate.update("INSERT INTO HELLO VALUES(?, ?)", 4, "Ciao Mondo");
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            transactionManager.commit(transaction);
        });
        CompletableFuture.runAsync(() -> {
            TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            transactionManager.rollback(transaction);
        });
        c1.get();
        String name = jdbcTemplate.queryForObject("SELECT NAME FROM HELLO WHERE ID = ?", String.class, 4);
        assertEquals("Ciao Mondo", name);
    }

    @Configuration
    @Import({
        DatabaseH2Configuration.class
    })
    public static class Conf {
    }

}
