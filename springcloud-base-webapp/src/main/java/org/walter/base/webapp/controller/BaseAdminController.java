package org.walter.base.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin")
@Slf4j
public abstract class BaseAdminController {

	protected String getRelativePath(String path) {
		String prefix = this.getClass().getSuperclass().getAnnotation(RequestMapping.class).value()[0];
		
		if(path.matches("^" + prefix + "/[.]+")) {
			return path;
		}else if(path.startsWith("/")) {
			return prefix + path;
		}else {
			return prefix + "/" + path;
		}
	}
	
	protected String toJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
