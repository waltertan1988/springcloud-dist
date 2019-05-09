var roleListObject = (function($) {
	var code = "roleList";
	
    return {
		init: function() {
			var ajaxHeader = {};
			ajaxHeader[_GOLBAL_CONSTANT.FORM_LOGIN.JWT_HEADER_KEY] = sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.JWT];
			$.ajax({
		        url: "http://localhost:7010/gateway/auth/admin/role/list",
		        type: "GET",
		        headers: ajaxHeader,
		        error: function(xmlHttpRequest, textStatus, errorThrown){
		        	var errText = xmlHttpRequest.status + "-" + xmlHttpRequest.statusText + ":" + xmlHttpRequest.responseText;
		        	if("403" == xmlHttpRequest.status){
		        		alert(errText);
		        		// 客户端保存当前页面的URL到SessionStorage并跳转到登录页面
			        	sessionStorage[_GOLBAL_CONSTANT.SESSION_STORAGE_KEY.LOGIN_CACHED_URL] = window.location.href;
			        	window.location.href = _GOLBAL_CONSTANT.FORM_LOGIN.LOGIN_PAGE;
		        	}else{
		        		$("#content_header").html(errText);
		        	}
		        },
		        success: function (data) {
		        	$("#content_header").html(JSON.stringify(data));
		        }
		    });
    	}
    };
} (jQuery));

$(function() {
	roleListObject.init();
});