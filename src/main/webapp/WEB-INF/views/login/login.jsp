<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
</head>
<body>

	<form id="form" action="/login" method="post">
		<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}"/> <%-- Spring security --%>
		<input type="text" name="username" value="test" />
		<input type="text" name="password" value="test" />
		<input type="submit" value="login" />
	</form>
	
</body>
</html>