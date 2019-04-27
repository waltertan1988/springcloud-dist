package org.walter.base.security.authenticate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		if(StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
			// 非ajax请求，重定向到之前的页面
			super.onAuthenticationSuccess(request, response, authentication);
		}else {
			PrintWriter out = response.getWriter();
			out.print(new ObjectMapper().writeValueAsString("SUCCESS"));
			out.flush();
		}
	}
}
