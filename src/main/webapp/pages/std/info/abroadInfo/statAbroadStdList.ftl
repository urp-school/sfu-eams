<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<#assign deadlineType>${RequestParameters["deadlineType"]?if_exists}</#assign>
	<@table.table id="abroadStdTable" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd text="学号" id="student.code" width="11%"/>
			<@table.sortTd text="姓名" id="student.name"/>
			<@table.sortTd text="学生类别" id="student.type.name"/>
			<@table.sortTd text="院系" id="student.department.name" width="15%"/>
			<@table.sortTd text="专业" id="student.firstMajor.name" width="15%"/>
			<@table.sortTd text="专业方向" id="student.firstAspect.name" width="12%"/>
			<#if deadlineType == "" || deadlineType == "visa">
				<@table.sortTd text="签证编号" id="student.abroadStudentInfo.visaNo" width="10%"/>
			<#elseif deadlineType == "passport">
				<@table.sortTd text="护照编号" id="student.abroadStudentInfo.passportNo" width="10%"/>
			<#else>
				<@table.sortTd text="居住许可证编号" id="student.abroadStudentInfo.resideCaedNo" width="10%"/>
			</#if>
			<@table.sortTd text="到期时间" id="student.firstAspect.name" width="10%"/>
		</@>
		<@table.tbody datas=students;student>
			<@table.selectTd id="stdId" value=student.id/>
			<td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">${student.code}</a></td>
			<td><@i18nName student/></td>
			<td><@i18nName student.type/></td>
			<td><@i18nName (student.department)?if_exists/></td>
			<td><@i18nName (student.firstMajor)?if_exists/></td>
			<td><@i18nName (student.firstAspect)?if_exists/></td>
			<#if deadlineType?exists == false || deadlineType == "visa">
				<td>${(student.abroadStudentInfo.visaNo)?if_exists}</td>
				<td>${(student.abroadStudentInfo.visaDeadline?string("yyyy-MM-dd"))?if_exists}</td>
			<#elseif deadlineType == "passport">
				<td>${(student.abroadStudentInfo.passportNo)?if_exists}</td>
				<td>${(student.abroadStudentInfo.passportDeadline?string("yyyy-MM-dd"))?if_exists}</td>
			<#else>
				<td>${(student.abroadStudentInfo.resideCaedNo)?if_exists}</td>
				<td>${(student.abroadStudentInfo.resideCaedDeadline?string("yyyy-MM-dd"))?if_exists}</td>
			</#if>
		</@>
	</@>
	<form method="post" action="" name="actionForm"></form>
	<script>
		var bar = new ToolBar("bar", "留学生到期统计结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addPrint("<@msg.message key="action.print"/>");
		
		var form = document.actionForm;
		function info() {
			form.action = "studentDetailByManager.do?method=detail";
			submitId(form, "stdId", false);
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>