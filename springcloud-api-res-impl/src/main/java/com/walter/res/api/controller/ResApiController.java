package com.walter.res.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.walter.base.entity.JpaSysMenu;

@RestController
public class ResApiController extends BaseResApiController {
	
	@GetMapping("/listMenu/{username}")
	public List<JpaSysMenu> listMenu(@PathVariable("username") String username) {
		return new ArrayList<JpaSysMenu>();
	}
}
