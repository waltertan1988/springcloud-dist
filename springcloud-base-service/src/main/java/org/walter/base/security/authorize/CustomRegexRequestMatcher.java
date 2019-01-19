package org.walter.base.security.authorize;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CustomRegexRequestMatcher implements RequestMatcher {

	private RegexRequestMatcher regexRequestMatcher;
	
	private String pattern;
	private String httpMethod;
	private boolean caseInsensitive;
	
	public CustomRegexRequestMatcher(String pattern, String httpMethod, boolean caseInsensitive) {
		regexRequestMatcher = new RegexRequestMatcher(pattern, httpMethod, caseInsensitive);
		this.pattern = pattern;
		this.httpMethod = httpMethod;
		this.caseInsensitive = caseInsensitive;
	}
	
	public CustomRegexRequestMatcher(String pattern, String httpMethod) {
		this(pattern, httpMethod, false);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return regexRequestMatcher.matches(request);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (caseInsensitive ? 1231 : 1237);
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomRegexRequestMatcher other = (CustomRegexRequestMatcher) obj;
		if (caseInsensitive != other.caseInsensitive)
			return false;
		if (httpMethod == null) {
			if (other.httpMethod != null)
				return false;
		} else if (!httpMethod.equals(other.httpMethod))
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}
}
