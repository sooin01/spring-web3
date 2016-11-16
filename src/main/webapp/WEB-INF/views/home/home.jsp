<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
</head>
<body>
	<form method="post" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="logout"/>
	</form>
</body>
</html>