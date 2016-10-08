<form name="loginForm" action="/login" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="text" name="username" value="test" />
	<input type="text" name="password" value="test" />
	<input type="submit" value="login" />
</form>