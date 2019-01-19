<@layout.admin title="出错啦">
<#if exception?exists>
	错误信息：${exception.message!""}
	<p>
		<#list exception.stackTrace as e>
			${e!""}<br>
		</#list>
	</p>
</#if>
</@layout.admin>