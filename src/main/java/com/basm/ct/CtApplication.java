package com.basm.ct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"com.basm.ct"})
public class CtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtApplication.class, args);
	}
}
