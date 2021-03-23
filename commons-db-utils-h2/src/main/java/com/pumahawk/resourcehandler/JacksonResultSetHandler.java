package com.pumahawk.resourcehandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonResultSetHandler<T> implements ResultSetHandler<T> {
	
	private Class<T> type;
	
	public JacksonResultSetHandler(Class<T> type) {
		this.type = type;
	}

	@Override
	public T handle(ResultSet rs) throws SQLException {
		ObjectMapper om = new ObjectMapper();
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		ObjectNode node = convert(rs);
		return node != null ? om.convertValue(node, type) : null;
	}
	
	private ObjectNode convert(ResultSet rs) throws SQLException {
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();

		ResultSetMetaData md = rs.getMetaData();
		int columnCounter = md.getColumnCount();
		
		if (rs.next()) {
			for (int i = 1; i <= columnCounter; i++) {
				node.set(md.getColumnLabel(i), mapper.convertValue(rs.getObject(i), JsonNode.class));
			}
			return node;
		} else {
			return null;
		}
		
	}

}
