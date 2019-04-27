<html>
	<head>
		<title>欢迎登录</title>
	</head>

	<body>
		<h1>欢迎进入默认登录页面</h1>
		<form action="${base}/login" method="POST">
			<label>账号：</label><input type="text" name="username" value="0009785"><br>
			<label>密码：</label><input type="password" name="password" value="123456"><br>
			<input type="submit" name="submit" value="登录">
		</form>
	</body>
	
	<script type="text/javascript" src="${base}/resources/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${base}/resources/js/jquery.form.js"></script>
	<@layout.includeScripts scripts="/login.js"/>
</html>