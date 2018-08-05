<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="bar" width="100%"></table>
	<table width="100%" class="frameTable">
		<form method="post" action="" name="actionForm" onsubmit="return false;">
			<tr id="f_year">
				<td align="right" style="width:35%" class="darkColumn">年份：</td>
				<td align="left" class="darkColumn"><input type="text" name="year" value="${year}" maxlength="4"/>&nbsp;<button onclick="account()">统计</button></td>
			</tr>
		</form>
	<table>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td><iframe name="display" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	<table>
 	<script>
		var bar = new ToolBar("bar", "教室场地借用率统计", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("统计", "account()");
		
		var form = document.actionForm;
		function account() {
			var a_fields = {
				'year':{'l':'年份', 'r':true, 't':'f_year', 'f':'unsigned'}
			}; 
			var v = new validator(form, a_fields, null);
            if (v.exec()) {		
				form.action = "roomApplyAccount.do?method=account";
				form.target = "display";
				form.submit();
			} else {
				alert("你输入了错误的年份，系统默认指定年份：" + (form['year'].value = "${year}"));
			}
		}
		account();
 	</script>
</body>
<#include "/templates/foot.ftl"/>