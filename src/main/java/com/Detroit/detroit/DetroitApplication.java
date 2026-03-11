package com.Detroit.detroit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DetroitApplication {
	public static void main(String[] args) {
		SpringApplication.run(DetroitApplication.class, args);
	}
}