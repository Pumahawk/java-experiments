package com.pumahawk;

import java.math.BigDecimal;

public class HelloDAO {

	private BigDecimal id;
	
	private String name;
	
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public BigDecimal getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
