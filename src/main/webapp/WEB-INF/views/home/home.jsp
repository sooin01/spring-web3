<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
</head>
<body>

<div>	
	<form action="/login" method="post">
		<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="text" name="username" value="test" />
		<input type="text" name="password" value="test" />
		<input type="submit" value="login" />
	</form>
</div>

<div>
	<form action="/logout" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="logout"/>
	</form>
</div>

</body>
</html>