var loginObject = (function($) {
	var code = "login";
	
    return {
		init: function() {
			$("#formLoginBtn").click(function(){
				$.ajax({
			        url: _GOLBAL_CONSTANT.FORM_LOGIN.LOGIN_PROCESSING_URL,
			        type: "POST",
			        data: $("#usernamePasswordCaptchaLoginForm").serialize(),
			        error: function(xmlHttpRequest, textStatus, errorThrown){
			        	alert("登录失败："+xmlHttpRequest.status + "：" + xmlHttpRequest.responseText);
			        },
			        success: function (jwt) {
			        	sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.JWT] = jwt;
			        	var cachedUrl = sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL];
			        	sessionStorage.removeItem(_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL);
			        	window.location.href = cachedUrl;
			        }
			    });
			});
    	}
    };
} (jQuery));

$(function() {
//	loginObject.init();
});