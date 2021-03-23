package com.pumahawk.repositories;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pumahawk.HelloWorldDao;
import com.pumahawk.resourcehandler.JacksonResultSetHandler;

@Component
public class HelloWorldRepository {
	
	@Autowired
	private QueryRunner queryRunner;
	
	public HelloWorldDao getById(BigDecimal id) {
		try {
			return queryRunner.query("select * from HelloWorld where id = ?", new JacksonResultSetHandler<>(HelloWorldDao.class), id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<HelloWorldDao> getAll() {
		try {
			return queryRunner.execute("select * from HelloWorld ORDER BY ID ASC", new JacksonResultSetHandler<>(HelloWorldDao.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
