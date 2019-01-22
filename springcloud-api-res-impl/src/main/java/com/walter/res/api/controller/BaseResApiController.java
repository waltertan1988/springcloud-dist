package com.walter.res.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/res")
public abstract class BaseResApiController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
}
