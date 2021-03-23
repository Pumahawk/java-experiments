package com.pumahawk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pumahawk.configuration.H2DatabaseConfiguration;
import com.pumahawk.repositories.HelloWorldRepository;

@Configuration
@ComponentScan(basePackageClasses = {
	H2DatabaseConfiguration.class,
	HelloWorldRepository.class,
})
public class IntegrationContextConfiguration {

}
