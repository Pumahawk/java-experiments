package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = DatabaseH2Configuration.class)
public class ConvertResultSetToClassWithJacksonTests {

	@Autowired
	private Connection connection;
	
	@Test
	public void convertHelloWorld() throws SQLException {
		
		ResultSet result = connection.createStatement().executeQuery("SELECT * FROM HELLO WHERE ID = '1'");
		
		HelloDao hello = toObj(result, HelloDao.class);
		
		assertNotNull(hello);
		assertEquals("Hello, World!", hello.getName());
	}
	
	private <T> T toObj(ResultSet result, Class<T> clazz) throws SQLException {
		Map<String, Object> map = resoultSetToMap(result);
		ObjectMapper om = new ObjectMapper();
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return om.convertValue(map, clazz);
	}
	
	private Map<String, Object> resoultSetToMap(ResultSet resultSet) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		ResultSetMetaData md = resultSet.getMetaData();
		int columnCounter = md.getColumnCount();
		
		if (resultSet.next()) {
			for (int i = 1; i <= columnCounter; i++) {
				map.put(md.getColumnName(i), resultSet.getObject(i));
			}
		}
		
		return map;
	}
	
}
