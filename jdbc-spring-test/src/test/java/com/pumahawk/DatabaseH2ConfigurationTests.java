package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ComponentScan(basePackageClasses = App.class)
public class DatabaseH2ConfigurationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void loadContext() throws SQLException { 
    }

    @Test
    public void transactionalTest() throws SQLException {

        Connection c = dataSource.getConnection();

        DatabaseH2Configuration.initDatabaseH2(c);

        c.close();

        Connection c2 = DataSourceUtils.getConnection(dataSource);

        assertTrue(DataSourceUtils.isConnectionTransactional(c2, dataSource));


        c2.prepareStatement("INSERT INTO HELLO VALUES(2343, 'ciao') ").execute();

        c2.commit();

        // Connection c3 = DataSourceUtils.getConnection(dataSource);

        // ResultSet r = c3.prepareStatement("SELECT * FROM HELLO where ID = 2343").executeQuery();
        // assertTrue(r.next());
    }

    @Test
    public void get2() throws SQLException {
        Connection c = DataSourceUtils.getConnection(dataSource);

        ResultSet r = c.prepareStatement("SELECT * FROM HELLO where ID = 2343").executeQuery();
        assertTrue(r.next());
    }

}
