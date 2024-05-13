package com.temelio.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(description = "Temelio Demo"))
public class TemelioDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemelioDemoApplication.class, args);
	}

}
