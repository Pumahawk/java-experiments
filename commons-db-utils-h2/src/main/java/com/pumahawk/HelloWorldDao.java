package com.pumahawk;

import java.math.BigDecimal;

public class HelloWorldDao {

	private BigDecimal id;
	private String message;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
