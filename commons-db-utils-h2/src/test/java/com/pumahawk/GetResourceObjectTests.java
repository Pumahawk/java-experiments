package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pumahawk.repositories.HelloWorldRepository;

@IntegrationWihtH2Test
public class GetResourceObjectTests {

	@Autowired
	private HelloWorldRepository helloWorldRepository;

	@Test
	public void retrieveResourceTest() {
		HelloWorldDao obj = assertDoesNotThrow(() -> helloWorldRepository.getById(BigDecimal.ONE));
		assertNotNull(obj);
		assertEquals("Hello, World!", obj.getMessage());
	}

	@Test
	public void retrieveResourceCollectionTest() {
		List<HelloWorldDao> obj = assertDoesNotThrow(() -> helloWorldRepository.getAll());
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
		assertEquals("Hello, World!", obj.get(0).getMessage());
	}
	
	@Test
	public void retrieveResourceNotFound() {
		HelloWorldDao obj = assertDoesNotThrow(() -> helloWorldRepository.getById(new BigDecimal(-1)));
		assertNull(obj);
	}
	
}
