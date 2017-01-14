<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/js/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/crypto-js/aes.js"></script>
<script type="text/javascript">
	function encrypt(val) {
		var key = CryptoJS.enc.Utf8.parse('cCEml6750sYhtuBo');
		var iv = CryptoJS.enc.Utf8.parse('12345678abcdefgh');
		return CryptoJS.AES.encrypt(val, key, { iv: iv });
	}

	function go1() {
		$("input[name=id]").val(encrypt("test1"));
		$("input[name=name]").val(encrypt("test2"));
		return true;
	}

	function go2() {
		location.href = "/notice/index?id=" + encodeURIComponent(encrypt("test1")) + "&name=" + encodeURIComponent(encrypt("213987123"));
	}
</script>
</head>
<body>

<form action="/notice/index" method="post" onsubmit="return go1();">
	<input type="text" name="id">
	<input type="text" name="name">
	<input type="submit" value="전송">
</form>

<br>
<input type="button" value="전송" onclick="go2(); return false;">

</body>
</html>