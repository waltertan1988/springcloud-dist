package com.walter.res.api.controller;

import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/res")
public abstract class BaseResApiController {

	protected Logger log = Logger.getLogger(this.getClass());
}
