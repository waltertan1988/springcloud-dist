package com.walter.base.security.authenticate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.walter.base.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Value("${custom.security.login.jwt.secret}")
	private String JWT_SECRET;
	
	@Value("${custom.security.login.jwt.alivedMinutes}")
	private int JWT_ALIVED_MINUTES;
	
	/**
	 * 认证成功后的处理逻辑
	 */
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		if(StringUtils.isEmpty(request.getHeader("X-Requested-With"))) {
			// 非ajax请求，重定向到之前的页面
			super.onAuthenticationSuccess(request, response, authentication);
		}else {
			// 生成并返回JWT给客户端
			String username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
			PrintWriter out = response.getWriter();
			out.print(generateJwt(username));
			out.flush();
		}
	}
	
	/**
	 * 生成JWT
	 * @param username
	 * @return
	 */
	protected String generateJwt(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Claims.SUBJECT, username);
		return JwtUtil.generate(claims, DateUtils.addMinutes(new Date(), JWT_ALIVED_MINUTES), SignatureAlgorithm.HS512, JWT_SECRET);
	}
}
