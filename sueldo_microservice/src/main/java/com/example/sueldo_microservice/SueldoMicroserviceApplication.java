package com.example.sueldo_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SueldoMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SueldoMicroserviceApplication.class, args);
		System.out.println("Running.");
	}

}
