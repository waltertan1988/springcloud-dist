var loginObject = (function($) {
	var code = "login";
	
    return {
    	init: function() {
    		$("#ajaxLoginBtn").click(function(){
    			$.ajax({
    		        url: BASE + '/admin/index',            
    		        success: function (data) {
    		        	alert(data.body);
    		        },
    		        error: function (jqXHR, textStatus, errorThrown) {
    		        	var data = JSON.parse(jqXHR.responseText);
    		        	alert(data.errorMessage);
    		        }
    		    });
    		});
    		
    		$("#captchaImg").click(function(){
    			this.src = this.src + '?' + Math.random();
    		});
    	}
    };
} (jQuery));

$(function() {
	loginObject.init();
});