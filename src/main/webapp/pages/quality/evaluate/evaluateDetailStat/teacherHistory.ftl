<#include "/templates/head.ftl"/>
<body onload="parent.clearBarInfo()">
	<table id="bar" width="100%"></table>
	<table width="100%" class="infoTable" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
		<tr>
			<td style="font-size: 10pt; font-weight: bold" colspan="6">历史评价对比分析</td>
		</tr>
		<#assign widthTdTitle = 100.0 / 6.0/>
		<tr>
			<td width="${widthTdTitle}%" class="title">教师姓名：</td>
			<td width="${widthTdTitle}%"><@i18nName evaluateTeacher.teacher/></td>
			<td width="${widthTdTitle}%" class="title">所属院系：</td>
			<td width="${widthTdTitle}%"><@i18nName evaluateTeacher.teacher.department/></td>
			<td width="${widthTdTitle}%" class="title">职称：</td>
			<td width="${widthTdTitle}%"><@i18nName (evaluateTeacher.teacher.title)?if_exists/></td>
		</tr>
	</table>
	<table class="listTable" width="100%" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
		<tr>
			<td width="12%" rowspan="2">评价时间</td>
			<td width="12%" rowspan="2">课程名称</td>
			<td rowspan="2">课程性质</td>
			<td width="8%">课程评价</td>
			<td width="8%">学院课程</td>
			<td width="8%">学校课程</td>
			<td width="10%" rowspan="2">学院排名<br>/学院总数</td>
			<td width="10%" rowspan="2">全校排名<br>/全校总数</td>
		</tr>
		<tr>
			<td>得分</td>
			<td>评价得分</td>
			<td>评价得分</td>
		</tr>
		<#list evaluateTeachers?if_exists as evaluateTeacher>
		<tr>
			<td>${evaluateTeacher.calendar.year}（${evaluateTeacher.calendar.term}）</td>
			<td><@i18nName evaluateTeacher.course/></td>
			<td><!--@i18nName evaluateTeacher.task.courseType/--></td>
			<td>${evaluateTeacher.sumScore?string("0.00")}</td>
			<td>${evaluateDepartMap[evaluateTeacher.calendar.id?string].sumScore?string("0.00")}</td>
			<td>${evaluateCollegeMap[evaluateTeacher.calendar.id?string].sumScore?string("0.00")}</td>
			<td>${evaluateTeacher.departRank}/${evaluateDepartMap[evaluateTeacher.calendar.id?string].count}</td>
			<td>${evaluateTeacher.rank}/${evaluateCollegeMap[evaluateTeacher.calendar.id?string].count}</td>
		</tr>
		</#list>
	</table>
	<script>
		var bar = new ToolBar("bar", "个人统计结果－历史评价", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>