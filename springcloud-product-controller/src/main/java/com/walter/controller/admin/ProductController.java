package com.walter.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ProductController {
	
	@GetMapping("/product/add")
	public String list(@RequestParam("product")String product) {
		return product;
	}
}
