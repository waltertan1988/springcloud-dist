package com.walter.user.api.controller;

import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public abstract class BaseUserApiController {

	protected Logger log = Logger.getLogger(this.getClass());
}
