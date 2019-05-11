const _GOLBAL_CONSTANT = {
	SESSION_STORAGE_KEY : {
		LOGIN_CACHED_URL : "loginCachedUrl"
	},
	
	LOCAL_STORAGE_KEY : {
		JWT : "jwt",
		CAPTCHA_CACHE_KEY: "captchaCacheKey"
	},

	FORM_LOGIN : {
		LOGIN_PAGE : "http://localhost:7010/auth/public/login.html",
		LOGIN_PROCESSING_URL : "http://localhost:7010/gateway/auth/login",
		GET_CAPTCHA_CACHE_KEY_URL : "http://localhost:7010/gateway/auth/public/getCaptchaCacheKey",
		GET_CAPTCHA_URL : "http://localhost:7010/gateway/auth/public/getCaptcha",
		JWT_HEADER_KEY : "Jwt",
		CAPTCHA_CACHE_KEY : "captcha-cache-key"
	},
	
	FORM_LOGOUT: {
		LOGOUT_PROCESSING_URL : "http://localhost:7010/gateway/auth/logout"
	}
};