<html>
<body>
<form method="post" action="noticeSearch.do?method=search" name="actionForm">
	<input type="hidden" name="kind" value="manager"/>
</form>
<script>
	var form = document.actionForm;
	form.action = "noticeSearch.do?method=search";
	form.submit();
</script>
</body>
</html>
