package com.walter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages="com.walter")
@EnableCircuitBreaker
public class ApiResApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiResApplication.class, args);
	}
}
