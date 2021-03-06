package com.walter.base.security.authorize;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomHttp403ForbiddenEntryPoint extends Http403ForbiddenEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		PrintWriter out = response.getWriter();
		out.print(new ObjectMapper().writeValueAsString(exception.getMessage()));
		out.flush();
	}
}
