package org.walter.base.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.walter.base.openapi.OpenApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController extends BaseController{
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@GetMapping("/loginPageDecision")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public OpenApiResponse loginPageDecision(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null) {
			log.info("引发重定向到登录页面的请求：{}", savedRequest.getRedirectUrl());
		}
		
		OpenApiResponse openApiResponse = new OpenApiResponse();
		if(StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
			// 非Ajax请求，重定向到登录页面
			openApiResponse.setSuccess(true);
			redirectStrategy.sendRedirect(request, response, "/loginPage");
		}else {
			// Ajax请求，返回json数据
			openApiResponse.setSuccess(false);
			String errMsg = HttpStatus.UNAUTHORIZED + ":" + HttpStatus.UNAUTHORIZED.getReasonPhrase();
			openApiResponse.setErrorMessage(errMsg);
		}
		
		return openApiResponse;
	}
	
	@GetMapping("/loginPage")
	public String loginPage(){
		return "/login";
	}
}
