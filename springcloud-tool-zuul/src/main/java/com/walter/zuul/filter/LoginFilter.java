package com.walter.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.walter.base.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Component
public class LoginFilter extends ZuulFilter {
	
	@Value("${custom.security.login.jwt.header}")
	private String JWT_HEADER;
	
	@Value("${custom.security.login.jwt.secret}")
	private String JWT_SECRET;
	
	protected final String PUBLIC_REQUEST = "/public/";
	
	protected final String AUTH_LOGIN_REQUEST = "/auth/login";
	
	protected final String AUTH_LOGOUT_REQUEST = "/auth/logout";
	
	/**
	 *  判断是否要生效
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if(!isPublicRequest(request)) {
			// 对以非public的请求进行拦截
			return true;
		}
		
		return false;
	}

	/**
	 *  拦截后判断是否要放行
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		String jwtTokenValue = request.getHeader(JWT_HEADER);
		
		if(request.getRequestURI().endsWith(AUTH_LOGOUT_REQUEST)) {
			return null;
		}
		
		//检查Jwt是否存在或过期
		if(StringUtils.isEmpty(jwtTokenValue) || null == getClaimsFromJwt(jwtTokenValue)) {
			requestContext.setSendZuulResponse(false);//设置不放行
			requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);//返回401响应码
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 4;
	}

	protected boolean isPublicRequest(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		return requestUri.contains(PUBLIC_REQUEST) || requestUri.equals(AUTH_LOGIN_REQUEST);
	}
	
	/**
	 * 获取Jwt的Claim，Jwt过期会返回null
	 * @param jwt
	 * @return
	 */
	protected Claims getClaimsFromJwt(String jwt) {
		return JwtUtil.getClaims(jwt, JWT_SECRET);
	}
}
