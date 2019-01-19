package com.walter.base.openapi;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

public class OpenApiJsonViewResponseBodyAdvice extends JsonViewResponseBodyAdvice {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)
				&& (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), OpenApi.class)
						|| returnType.hasMethodAnnotation(OpenApi.class));
	}

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
		bodyContainer.setValue(new OpenApiResponse(true, bodyContainer.getValue(), null, null));
	}
}
