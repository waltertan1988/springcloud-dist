package com.walter.res.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.walter.base.entity.JpaSysMenu;

import com.walter.res.api.ResApi;

@RestController
public class ResApiController extends BaseResApiController implements ResApi {
	
	@Override
	public List<JpaSysMenu> listMenu(@PathVariable("username")String username) {
		return new ArrayList<JpaSysMenu>();
	}
}
