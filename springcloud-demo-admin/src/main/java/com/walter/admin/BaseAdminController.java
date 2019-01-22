package com.walter.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public abstract class BaseAdminController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
}
