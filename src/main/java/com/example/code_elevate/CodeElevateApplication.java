package com.example.code_elevate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@EnableRetry
public class CodeElevateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeElevateApplication.class, args);
	}

}
