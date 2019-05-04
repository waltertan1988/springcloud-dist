package com.walter.controller.publilc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.walter.base.controller.publilc.BasePublicController;

@RestController
public class IndexController extends BasePublicController{
	
	@GetMapping("/index")
	public String index(@RequestParam String helloTo) {
		return "Hello " + helloTo;
	}
}
