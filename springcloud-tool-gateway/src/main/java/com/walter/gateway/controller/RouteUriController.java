package com.walter.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouteUriController extends BaseGatewayController{

	@ResponseBody
	@GetMapping("/hystrixRouteResult")
	public String hystrixRouteResult() {
		
		System.out.println(1/0);
		
		return "Spring Cloud Gateway Hystrix Routed Result!";
	}
}
