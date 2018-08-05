<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="result" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="evaluateResultId"/>
			<@table.sortTd text="学号" id="evaluateResult.student.code"/>
			<@table.sortTd text="姓名" id="evaluateResult.student.name"/>
			<@table.sortTd text="课程序号" id="evaluateResult.task.seqNo"/>
			<@table.sortTd text="课程代码" id="evaluateResult.task.course.code"/>
			<@table.sortTd text="课程名称" id="evaluateResult.task.course.name"/>
			<@table.sortTd text="被评教教师" id="evaluateResult.teacher.name"/>
			<@table.sortTd text="评教状态" id="evaluateResult.statState"/>
		</@>
		<@table.tbody datas=evaluateResults; result>
			<@table.selectTd id="evaluateResultId" value=result.id/>
			<td>${result.student.code}</td>
			<td width="10%"><A href="#" onclick="info(${result.id})"><@i18nName result.student/></A></td>
			<td>${result.task.seqNo}</td>
			<td>${result.task.course.code}</td>
			<td><@i18nName result.task.course/></td>
			<td width="10%"><@i18nName (result.teacher)?if_exists/></td>
			<td width="10%">${(result.statState?string("<font color=\"blue\">参加评教</font>", "<font color=\"red\">取消评教</font>"))?default("没有评教")}</td>
			<input type="hidden" id="state${result.id}" value="${(result.statState?string(1, 0))?default(0)}"/>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="evaluateResult.do" entity="evaluateResult">
        <input type="hidden" name="isEvaluate" valeu=""/>
	</@>
	<script>
		var bar = new ToolBar("bar", "学生评教结果明细列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看", "info()");
		bar.addItem("参加评教", "batchUpdateState(1)");
		bar.addItem("取消评教", "batchUpdateState(0)");
		
		function batchUpdateState(isEvaluate) {
			form["isEvaluate"].value = isEvaluate;
			multiAction("batchUpdateState");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>