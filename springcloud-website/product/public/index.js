var indexObject = (function($) {
	var code = "index";
	
    return {
		init: function() {
			$.ajax({
		        url: "http://localhost:7010/gateway/product/public/index?helloTo=WalterTanAndCathyChen",
		        type: "GET",
		        success: function (data) {
		        	alert(data);
		        }
		    });
    	}
    };
} (jQuery));

$(function() {
	indexObject.init();
});