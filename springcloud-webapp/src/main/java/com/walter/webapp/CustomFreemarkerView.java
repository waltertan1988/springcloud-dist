package com.walter.webapp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.template.SimpleHash;

public class CustomFreemarkerView extends FreeMarkerView {
	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		model.put("base", request.getContextPath());
		return super.buildTemplateModel(model, request, response);
	}
}
