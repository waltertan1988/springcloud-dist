var sysAdmRoleObject = (function($) {

	var code = "sysAdmRole";

	return {
		init : function() {
			$("#refreshRole").click(function(){
				var url = BASE + "/admin/sysAdm/role/refresh";
				$.get(url, function(data){
					if(!data.success){
						alert("刷新失败("+data.errorCode+"):"+data.errorMessage);
					}
				});
			});
		}
	};
}(jQuery));

$(function() {
	sysAdmRoleObject.init();
});