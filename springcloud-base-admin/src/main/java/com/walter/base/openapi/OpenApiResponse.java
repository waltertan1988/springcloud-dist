package com.walter.base.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OpenApiResponse {
	@Getter private boolean isSuccess;
	@Getter private Object body;
	@Getter private String errorCode;
	@Getter private String errorMessage;
}
