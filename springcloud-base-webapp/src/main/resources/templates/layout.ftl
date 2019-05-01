<#macro admin title scripts="">
<html>
	<head>
		<title>${title}</title>
	</head>

	<body>
		<div id="content_header">
			<div style="float:right;">
				<div style="display:inline;">欢迎您：${USER_PRINCIPAL.userRealName!"游客"}</div>
				<a href="${BASE}/logout">退出</a>
			</div>
			<@layout.clearfloat/>
		</div>
		<hr>
		<div id="content_body">
			<#nested />	
		</div>
		<div id="content_footer">
		</div>
	</body>
	<script type="text/javascript" src="${BASE}/resources/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${BASE}/resources/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${BASE}/resources/js/jquery.form.js"></script>
	<@includeScripts scripts/>
</html>
</#macro>

<#macro includeScripts scripts>
	<#if scripts?exists && scripts != ''>
		<#list scripts?split(",") as jsItem>
			<script id="${jsItem?trim?replace('.', '_')?replace('/', '_')}" type="text/javascript">
			    var BASE = "${BASE}"
				<#include "/${jsItem?trim}" parse=false />
			</script>
		</#list>
	</#if>
</#macro>

<#macro clearfloat>
	<div style="clear:both;"></div>
</#macro>