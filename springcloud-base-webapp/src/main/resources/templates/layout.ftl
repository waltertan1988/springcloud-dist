<#macro admin title scripts="">
<html>
	<head>
		<title>${title}</title>
	</head>

	<body>
		<div id="content_head">
			<div style="float:right;"><a href="${base}/logout">退出</a></div>
			<@layout.clearfloat/>
		</div>
		<div id="content_body">
			<#nested />	
		</div>
	</body>
	<script type="text/javascript" src="${base}/resources/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.form.js"></script>
	<@includeScripts scripts/>
</html>
</#macro>

<#macro includeScripts scripts>
	<#if scripts?exists && scripts != ''>
		<#list scripts?split(",") as jsItem>
			<script id="${jsItem?trim?replace('.', '_')?replace('/', '_')}" type="text/javascript">
				<#include "/${jsItem?trim}" parse=false />
			</script>
		</#list>
	</#if>
</#macro>

<#macro clearfloat>
	<div style="clear:both;"></div>
</#macro>