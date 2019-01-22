package com.walter.user.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public abstract class BaseUserApiController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
}
