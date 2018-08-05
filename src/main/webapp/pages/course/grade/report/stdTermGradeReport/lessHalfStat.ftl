<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="studentList" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="stdId" sortable="true"/>
			<@table.sortTd text="学号" id="stdTC.std.code"/>
			<@table.sortTd text="姓名" id="stdTC.std.name"/>
			<@table.sortTd text="学生类别" id="stdTC.std.type.name"/>
			<@table.sortTd text="院系" id="stdTC.std.department.name"/>
			<@table.sortTd text="专业" id="stdTC.std.firstMajor.name"/>
			<@table.sortTd text="专业方向" id="stdTC.std.firstAspect.name"/>
			<@table.sortTd text="已得学分" id="stdTC.credits"/>
			<@table.sortTd text="总学分" id="stdTC.totalCredits"/>
		</@>
		<@table.tbody datas=stdTCs;stdTC>
			<@table.selectTd id="stdId" value=stdTC.std.id/>
			<td><a href="studentDetailByManager.do?method=detail&stdId=${stdTC.std.id}">${stdTC.std.code}</a></td>
			<td width="9%"><@i18nName stdTC.std/></td>
			<td><@i18nName (stdTC.std.type)?if_exists/></td>
			<td><@i18nName (stdTC.std.department)?if_exists/></td>
			<td><@i18nName (stdTC.std.firstMajor)?if_exists/></td>
			<td width="12%"><@i18nName (stdTC.std.firstAspect)?if_exists/></td>
			<td width="9%">${stdTC.credits?default(0)?string("#.##")}</td>
			<td width="7%">${stdTC.totalCredits?default(0)?string("#.##")}</td>
		</@>
	</@>
	<form method="post" action="" name="actionForm"></form>
	<script>
		var bar = new ToolBar("bar", "学分不过半学生列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("学生信息", "info()");
		bar.addPrint("<@msg.message key="action.print"/>");
		
		var form = document.actionForm;
		function info() {
			form.action = "studentDetailByManager.do?method=detail";
			submitId(form, "stdId", false);
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>