package com.fourback.runus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })	// 시큐리티 비활성
@OpenAPIDefinition(info = @Info(title = "Runus API", version = "1.0", description = "Runus Application API Documentation"))
public class RunusApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunusApplication.class, args);
	}

}
