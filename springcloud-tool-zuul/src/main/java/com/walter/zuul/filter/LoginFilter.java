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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class LoginFilter extends ZuulFilter {
	
	@Value("${custom.security.login.jwt.header}")
	private String JWT_HEADER;
	
	@Value("${custom.security.login.jwt.secret}")
	private String JWT_SECRET;
	
	protected final String PUBLIC_REQUEST = "/public/";
	
	protected final String AUTH_LOGIN_REQUEST = "/auth/login";
	
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
		
		//检查Jwt是否存在或过期
		if(StringUtils.isEmpty(jwtTokenValue) || null == getClaimsFromJwt(jwtTokenValue)) {
			requestContext.setSendZuulResponse(false);//设置不放行
			requestContext.setResponseStatusCode(HttpStatus.SC_FORBIDDEN);//返回403响应码
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
	    Claims claims;
	    try {
	        claims = Jwts.parser()
	                .setSigningKey(JWT_SECRET)
	                .parseClaimsJws(jwt)
	                .getBody();
	    } catch (Exception e) {
	        claims = null;
	    }
	    return claims;
	}
}
