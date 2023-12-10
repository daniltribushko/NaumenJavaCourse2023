package com.example.naumen2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Naumen2023Application {

	public static void main(String[] args) {
		SpringApplication.run(Naumen2023Application.class, args);
	}

}
