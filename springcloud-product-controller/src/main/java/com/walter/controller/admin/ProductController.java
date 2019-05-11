package com.walter.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.walter.base.controller.admin.BaseAdminController;

@RestController
public class ProductController extends BaseAdminController{
	
	@GetMapping("/product/add")
	public String add(@RequestParam("product")String product) {
		return product;
	}
}
