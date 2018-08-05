<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
	<table id="bar"></table>
	<table class="formTable" width="60%" align="center">
		<form method="post" action="" name="abroadForm" onsubmit="return false;">
		<input name="abroadStudentInfo.id" type="hidden" value="${student.abroadStudentInfo.id}">
			<tr>
				<td class="darkColumn" colspan="2" style="font-weight:bold; text-align:center">修改签证记录信息</td>
			</tr>
			<tr>
				<td class="title" style="text-align:right"><@msg.message key="std.code"/>：</td>
				<td id="stdCode">${(student.code)}</td>
			</tr>
			<tr>
				<td class="title" style="text-align:right" id="f_resideCaedDeadline"><font color="red"><b>*</b></font><@msg.message key="info.passportDeadline.resideCaedDeadline"/>：</td>
				<td><input type="text" name="abroadStudentInfo.resideCaedDeadline" maxlength="10" onfocus="calendar()" value="${(student.abroadStudentInfo.resideCaedDeadline?string("yyyy-MM-dd"))?default('')}"/></td>
			</tr>
			<tr>
				<td class="title" style="text-align:right" id="f_passportDeadline"><font color="red"><b>*</b></font><@msg.message key="info.passportDeadline.passportDeadline"/>：</td>
				<td><input type="text" name="abroadStudentInfo.passportDeadline" onfocus="calendar()" maxlength="10" value="${(student.abroadStudentInfo.passportDeadline?string("yyyy-MM-dd"))?default('')}"/></td>
			</tr>
			<tr>
				<td class="title" style="text-align:right" id="f_visaDeadline"><font color="red"><b>*</b></font><@msg.message key="info.passportDeadline.visaDeadline"/>：</td>
				<td><input type="text" name="abroadStudentInfo.visaDeadline" onfocus="calendar()" maxlength="10" value="${(student.abroadStudentInfo.visaDeadline?string("yyyy-MM-dd"))?default('')}"/></td>
			</tr>
			<tr>
				<td colspan="2" class="darkColumn" style="text-align:center">
					<button onclick="save()">提交</button>
					<button onclick="this.form.reset()">重置</button>
				</td>
			</tr>
		</form>
	</table>
	<#list 1..15 as i><br></#list>
	<script>
		var bar = new ToolBar("bar", "修改签证", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.save"/>", "save()");
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
		
		var form = document.abroadForm;
		function save() {
			var a_fields = {
				'abroadStudentInfo.resideCaedDeadline':{'l':'<@msg.message key="info.passportDeadline.resideCaedDeadline"/>', 'r':true, 't':'f_resideCaedDeadline'},
				'abroadStudentInfo.passportDeadline':{'l':'<@msg.message key="info.passportDeadline.passportDeadline"/>', 'r':true, 't':'f_passportDeadline'},
				'abroadStudentInfo.visaDeadline':{'l':'<@msg.message key="info.passportDeadline.visaDeadline"/>', 'r':true, 't':'f_visaDeadline'}
			};
			var v = new validator(form, a_fields, null);
			if (v.exec()) {
				form.action = "abroadInfo.do?method=save";
				form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>