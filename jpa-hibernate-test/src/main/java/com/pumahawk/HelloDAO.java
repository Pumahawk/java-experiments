package com.pumahawk;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hello")
public class HelloDAO {

	@Id
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
