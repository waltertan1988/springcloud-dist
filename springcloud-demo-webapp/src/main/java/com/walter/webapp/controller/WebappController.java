package com.walter.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.walter.base.openapi.OpenApi;

import com.walter.service.stream.RefreshStaticMemoryDataProducer;

@Controller
public class WebappController {
	
	@Autowired
	private RefreshStaticMemoryDataProducer refreshStaticMemoryDataProducer;
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/refreshStaticMemoryData/{msg}")
	@OpenApi
	public Boolean refreshStaticMemoryData(@PathVariable("msg") String msg) {
		refreshStaticMemoryDataProducer.sendMessage(msg);
		return true;
	}
}
