var roleListObject = (function($) {
	var code = "roleList";
	
    return {
		init: function() {
			var ajaxHeader = {};
			ajaxHeader[_GOLBAL_CONSTANT.FORM_LOGIN.JWT_HEADER_KEY] = localStorage[_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.JWT];
			$.ajax({
		        url: "http://localhost:7010/gateway/auth/admin/role/list",
		        type: "GET",
		        headers: ajaxHeader,
		        success: function (data) {
		        	$("#content_body").html(JSON.stringify(data));
		        },
		        error: function(xmlHttpRequest, textStatus, errorThrown){
		        	var errText = xmlHttpRequest.status + "-" + xmlHttpRequest.statusText + ":" + xmlHttpRequest.responseText;
		        	if("401" == xmlHttpRequest.status){
		        		alert(errText);
		        		// 客户端保存当前页面的URL到sessionStorage并跳转到登录页面
		        		sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL] = window.location.href;
			        	window.location.href = _GOLBAL_CONSTANT.FORM_LOGIN.LOGIN_PAGE;
		        	}else{
		        		$("#content_body").html(errText);
		        	}
		        }
		    });
			
			$("#logoutBtn").click(function(){
				$.ajax({
			        url: "http://localhost:7010/gateway/auth/logout",
			        type: "POST",
			        headers: ajaxHeader,
			        success: function (data) {
			        	localStorage.removeItem(_GOLBAL_CONSTANT.LOCAL_STORAGE_KEY.JWT);
			        	// 客户端保存当前页面的URL到localStorage并跳转到登录页面
			        	sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL] = window.location.href;
			        	window.location.href = _GOLBAL_CONSTANT.FORM_LOGIN.LOGIN_PAGE;
			        }
			    });
			});
    	}
    };
} (jQuery));

$(function() {
	roleListObject.init();
});