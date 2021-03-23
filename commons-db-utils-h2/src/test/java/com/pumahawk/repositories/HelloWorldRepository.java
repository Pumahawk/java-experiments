package com.pumahawk.repositories;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pumahawk.HelloWorldDao;

@Component
public class HelloWorldRepository {
	
	@Autowired
	private QueryRunner queryRunner;
	
	public HelloWorldDao getById(BigDecimal id) {
		try {
			return queryRunner.query("select * from HelloWorld where id = ?", new BeanHandler<>(HelloWorldDao.class), id.intValue());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
