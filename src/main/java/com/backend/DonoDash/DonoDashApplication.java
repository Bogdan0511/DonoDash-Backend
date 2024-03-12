package com.backend.DonoDash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DonoDashApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonoDashApplication.class, args);
	}

}
