package com.direct.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class DirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectApplication.class, args);
	}

	@Bean 
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

}
