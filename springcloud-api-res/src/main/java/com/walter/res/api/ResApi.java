package com.walter.res.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.walter.base.entity.JpaSysMenu;

@FeignClient(name="res", path="/api/res")
public interface ResApi {

	@GetMapping("/listMenu/{username}")
	public List<JpaSysMenu> listMenu(@PathVariable("username") String username);
}
