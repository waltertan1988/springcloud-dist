var loginObject = (function($) {
	var code = "login";
	
    return {
		init: function() {
			// 获取一个图片验证码在缓存中的key并放入到localStorage
			if(!localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.CAPTCHA_CACHE_KEY]){
				$.ajax({
			        url: _GOLBAL_CONSTANT.FORM_LOGIN.GET_CAPTCHA_CACHE_KEY_URL,
			        type: "GET",
			        async: false,
			        success: function (captchaCacheKey) {
			        	localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.CAPTCHA_CACHE_KEY] = captchaCacheKey;
			        }
			    });
			}
			
			$("#captchaImg").click(function(){
				var captchaCacheKey = localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.CAPTCHA_CACHE_KEY];
	        	var captchaImgSrc = _GOLBAL_CONSTANT.FORM_LOGIN.GET_CAPTCHA_URL + "/" + captchaCacheKey + "?" + new Date().getTime();
	        	$("#captchaImg").attr("src", captchaImgSrc);
			});
			
			$("#formLoginBtn").click(function(){
				var ajaxHeader = {};
				ajaxHeader[_GOLBAL_CONSTANT.FORM_LOGIN.CAPTCHA_CACHE_KEY] = localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.CAPTCHA_CACHE_KEY];
				
				$.ajax({
			        url: _GOLBAL_CONSTANT.FORM_LOGIN.LOGIN_PROCESSING_URL,
			        type: "POST",
			        headers: ajaxHeader,
			        data: $("#usernamePasswordCaptchaLoginForm").serialize(),
			        success: function (jwt) {
			        	// 把JWT保存到localStorage
			        	localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.JWT] = jwt;
			        	localStorage.removeItem(_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.CAPTCHA_CACHE_KEY);
			        	
			        	// 重新访问跳转到登录页前的URL页面
			        	var cachedUrl = sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL];
			        	sessionStorage.removeItem(_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL);
			        	window.location.href = cachedUrl;
			        },
			        error: function(xmlHttpRequest, textStatus, errorThrown){
			        	alert("登录失败：" + xmlHttpRequest.status + "：" + xmlHttpRequest.responseText);
			        }
			    });
			});
    	}
    };
} (jQuery));

$(function() {
	loginObject.init();
	$("#captchaImg").click();
});