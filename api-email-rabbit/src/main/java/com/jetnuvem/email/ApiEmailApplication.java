package com.jetnuvem.email;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jetnuvem", "com.porto"})
//@EnableAspectJAutoProxy
public class ApiEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEmailApplication.class, args);
	}

}
