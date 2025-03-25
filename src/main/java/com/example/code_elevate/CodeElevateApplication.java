package com.example.code_elevate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@EnableRetry
public class CodeElevateApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CodeElevateApplication.class, args);
	}

}
