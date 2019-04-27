package org.walter.base.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public abstract class BaseAdminController extends BaseController {

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
}
