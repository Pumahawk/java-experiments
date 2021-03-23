package com.pumahawk;

import org.springframework.context.annotation.ComponentScan;

import com.pumahawk.configuration.H2DatabaseConfiguration;
import com.pumahawk.repositories.HelloWorldRepository;

@ComponentScan(basePackageClasses = {
	H2DatabaseConfiguration.class,
	HelloWorldRepository.class,
})
public @interface IntegrationWihtH2Test {

}
