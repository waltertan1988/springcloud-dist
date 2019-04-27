var loginObject = (function($) {
	var code = "login";
	
    return {
    	init: function() {
    		$("#ajaxLoginBtn").click(function(){
    			$.ajax({
    		        url: base + '/admin/index',            
    		        success: function (data) {
    		        	alert(data.body);
    		        },
    		        error: function (jqXHR, textStatus, errorThrown) {
    		        	var data = JSON.parse(jqXHR.responseText);
    		        	alert(data.errorMessage);
    		        }
    		    });
    		});
    	}
    };
} (jQuery));

$(function() {
	loginObject.init();
});