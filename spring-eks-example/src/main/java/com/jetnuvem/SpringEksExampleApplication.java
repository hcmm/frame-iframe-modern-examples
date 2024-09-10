package com.jetnuvem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jetnuvem", "com.porto"})
public class SpringEksExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEksExampleApplication.class, args);
	}

}
