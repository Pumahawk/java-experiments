package com.pumahawk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pumahawk.repositories.HelloWorldRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationContextConfiguration.class)
public class GetResourceObjectTests {

	@Autowired
	private HelloWorldRepository helloWorldRepository;
	
	@Test
	public void retrieveResourceTest() {
		HelloWorldDao obj = assertDoesNotThrow(() -> helloWorldRepository.getById(BigDecimal.ONE));
		assertNotNull(obj);
		assertEquals("Hello, World!", obj.getMessage());
	}
	
}