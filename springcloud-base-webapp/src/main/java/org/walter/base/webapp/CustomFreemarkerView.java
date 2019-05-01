package org.walter.base.webapp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.walter.base.security.authenticate.CustomUser;

import freemarker.template.SimpleHash;

public class CustomFreemarkerView extends FreeMarkerView {
	// 定义Freemarker页面可用的全局变量
	protected final String CUSTOM_VARIABLE_BASE = "BASE";
	protected final String CUSTOM_VARIABLE_USER_PRINCIPAL = "USER_PRINCIPAL";
	
	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		model.put(CUSTOM_VARIABLE_BASE, request.getContextPath());
		Authentication principal = (Authentication)request.getUserPrincipal();
		if(null != principal) {
			CustomUser userPrincipal = (CustomUser)principal.getPrincipal();
			model.put(CUSTOM_VARIABLE_USER_PRINCIPAL, userPrincipal);
		}
		return super.buildTemplateModel(model, request, response);
	}
}
