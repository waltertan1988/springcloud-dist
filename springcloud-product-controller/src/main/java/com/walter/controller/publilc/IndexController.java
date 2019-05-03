package com.walter.controller.publilc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class IndexController {
	
	@GetMapping("/index")
	@ResponseBody
	public String index(@RequestParam String helloTo) {
		return "Hello " + helloTo;
	}
}
