package org.walter.base.webapp;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.walter.base.openapi.OpenApiResponse;
import org.walter.base.openapi.OpenApiResponse.ErrorCodeEnum;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomExceptionResolver implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		if(StringUtils.isEmpty(request.getHeader("x-requested-with"))){
			// 非Ajax请求
			ModelAndView mv = new ModelAndView();
			mv.addObject("exception", ex);
			mv.setViewName("error");
	        return mv;
		}else{
			// Ajax请求
			try {
				PrintWriter out = response.getWriter();
				OpenApiResponse openApiResponse = new OpenApiResponse();
				openApiResponse.setSuccess(false);
				openApiResponse.setErrorCode(ErrorCodeEnum.ERR_500);
				openApiResponse.setErrorMessage(ex.getStackTrace().toString());
				out.print(new ObjectMapper().writeValueAsString(openApiResponse));
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
}
