var productObject = (function($) {
	var code = "product";
	var LOGIN_PAGE = "http://localhost:7010/auth/public/login.html";
	
    return {
		init: function() {
			$.ajax({
		        url: "http://localhost:7010/gateway/product/admin/product/add?product=沐浴露",
		        type: "GET",
		        error: function(xmlHttpRequest, textStatus, errorThrown){
		        	if("403" == xmlHttpRequest.status){
		        		// 客户端保存当前页面的URL
			        	sessionStorage["CachedUrl"] = window.location.href;
			        	window.location.href = LOGIN_PAGE;
		        	}else{
		        		$("#content_header").html(xmlHttpRequest.status + ":" + xmlHttpRequest.statusText);
		        	}
		        },
		        success: function (data) {
		        	$("#content_header").html(data);
		        }
		    });
    	}
    };
} (jQuery));

$(function() {
	productObject.init();
});