package com.walter.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fallback")
public class HystrixFallbackController {

	@ResponseBody
	@GetMapping("/hystrixRoute")
	public String hystrixRoute() {
		return "Spring Cloud Gateway Fallback!";
	}
}
