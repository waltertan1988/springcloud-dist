package org.walter.base.security.authenticate.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	public ValidateCodeException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}
