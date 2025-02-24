package com.example.code_elevate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CodeElevateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeElevateApplication.class, args);
	}

}
