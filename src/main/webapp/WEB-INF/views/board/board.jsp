<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/js/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/resources/js/crypto-js/aes.js"></script>
<script type="text/javascript">
	function encrypt(val) {
		// UTF-8
		var key = CryptoJS.enc.Utf8.parse('12345678abcdefgh');
		var iv = CryptoJS.enc.Utf8.parse('cCEml6750sYhtuBo');
		/* HEX
		var key = CryptoJS.enc.Hex.parse('000102030405060708090a0b0c0d0e0f');
		var iv = CryptoJS.enc.Hex.parse('101112131415161718191a1b1c1d1e1f');
		*/
		return CryptoJS.AES.encrypt(val, key, { iv: iv });
	}

	function go1() {
		$("input[name=id]").val(encrypt("test1"));
		$("input[name=name]").val(encrypt("213987123"));
		return true;
	}

	function go2() {
		location.href = "/board/index?id=" + encodeURIComponent(encrypt("test1")) + "&name=" + encodeURIComponent(encrypt("213987123"));
	}
</script>
</head>
<body>

<form action="/board/index" method="post" onsubmit="return go1();">
	<input type="text" name="id">
	<input type="text" name="name">
	<input type="submit" value="전송">
</form>

<br>
<input type="button" value="전송" onclick="go2(); return false;">

</body>
</html>