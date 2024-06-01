package com.bibliotheque.bibliotheque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Bibliotheque Api", version = "beta", description = "Mon Api Bibliotheque"))
public class BibliothequeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliothequeApplication.class, args);
	}

}