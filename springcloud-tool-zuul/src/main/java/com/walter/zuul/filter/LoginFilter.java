package com.walter.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LoginFilter extends ZuulFilter {

	protected final String JWT_HEADER = "JWT"; // JWT的请求头
	
	protected final String PUBLIC_REQUEST = "/public/";
	
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
		String jwtToken = request.getHeader(JWT_HEADER);
		
		if(StringUtils.isEmpty(jwtToken)) {
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
		return request.getRequestURI().contains(PUBLIC_REQUEST);
	}
}
