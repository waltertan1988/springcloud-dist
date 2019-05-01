package com.walter.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.walter.base.admin.controller.BaseController;

@Controller
public class WebappController extends BaseController{
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
}
