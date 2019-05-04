var loginObject = (function($) {
	var code = "login";
	
    return {
		init: function() {
			$.ajax({
		        url: "http://localhost:7010/gateway/product/public/index?helloTo=WalterTanAndCathyChen",
		        type: "GET",
		        success: function (data) {
		        	$("#content_header").html(data);
		        }
		    });
    	}
    };
} (jQuery));

$(function() {
	loginObject.init();
});