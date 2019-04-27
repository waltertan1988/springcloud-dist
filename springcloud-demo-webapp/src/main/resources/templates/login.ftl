<html>
	<head>
		<title>欢迎登录</title>
	</head>

	<body>
		<h1>欢迎进入自定义登录页面</h1>
		<form action="${base}/login" method="POST">
			<table>
				<tr><td>账号：</td><td><input type="text" name="username" value="0009785"></td></tr>
				<tr><td>密码：</td><td><input type="password" name="password" value="123456"></td></tr>
				<tr>
					<td>验证码：</td>
					<td>
						<input type="text" name="captcha">
						<img id="captchaImg" src="${base}/getCaptcha" style="cursor:pointer;"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="登录">
						<input type="button" id="ajaxLoginBtn" value="AJAX访问受保护页面">
					</td>
				</tr>
			</table>
		</form>
	</body>
	
	<script type="text/javascript" src="${base}/resources/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.form.js"></script>
	<@layout.includeScripts scripts="/login.js"/>
</html>