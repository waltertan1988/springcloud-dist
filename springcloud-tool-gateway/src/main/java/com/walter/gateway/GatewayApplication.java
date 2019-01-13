package com.walter.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// 一般路由
				.route("jd_route", r -> r.path("/jd")
						.uri("http://www.163.com"))
				// Hystrix熔断路由
				.route("hystrix_route", r -> r.path("/hystrix")
						.filters(f -> f.hystrix(c -> c.setName("fallbackcmd1")
								.setFallbackUri("forward:/fallback/hystrixRoute")))
						.uri("http://localhost:7201/gateway/hystrixRouteResult"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
