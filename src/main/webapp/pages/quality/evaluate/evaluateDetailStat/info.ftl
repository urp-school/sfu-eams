<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<table width="100%" class="infoTable">
		<tr>
			<td style="font-size: 10pt; font-weight: bold" colspan="4">课程评价各项指标状态显示</td>
		</tr>
		<tr>
			<td width="25%" class="title">被评教师：</td>
			<td width="25%"><@i18nName evaluateTeacher.teacher/></td>
			<td width="25%"  class="title">所属院系：</td>
			<td><@i18nName evaluateTeacher.teacher.department/></td>
		</tr>
		<tr>
			<td  class="title">被评课程：</td>
			<td><@i18nName evaluateTeacher.course/></td>
			<td  class="title">评价学期：</td>
			<td>${evaluateTeacher.calendar.year}(${evaluateTeacher.calendar.term})</td>
		</tr>
	</table>
	<table class="listTable" width="100%" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
		<tr>
			<td rowspan="2" colspan="2"></td>
			<td width="10%">个人各项</td>
			<td width="10%">院系各项</td>
			<td width="10%">全校各项</td>
		</tr>
		<tr>
			<td>得分均值</td>
			<td>得分均值</td>
			<td>得分均值</td>
		</tr>
		<#list evaluateTeacher.questionsStat?sort_by("question", "id") as questionStat>
		<tr>
			<td align="center">${questionStat_index + 1}</td>
			<td width="65%">${questionStat.question.content?html}</td>
			<td>${questionStat.evgPoints?string("0.00")}</td>
			<td>${departQuestionResults[evaluateTeacher.teacher.department.id + "_" + questionStat.question.id].evgPoints?string("0.00")}</td>
			<td>${collegeQuestionResults["college_" + questionStat.question.id].evgPoints?string("0.00")}</td>
		</tr>
		</#list>
	</table>
	<table width="100%" style="line-height:5mm">
		<tr>
			<td width="30%" align="left">该课程个人得分：${evaluateTeacher.sumScore}</td>
			<td align="center">院系平均分：${evaluateDepartment.sumScore}</td>
			<td width="30%" align="right">全校平均分：${evaluateCollege.sumScore}</td>
		</tr>
	</table>
	<br>
	<table width="100%" style="line-height:5mm">
		<tr>
			<td width="50%">该课程全校得分排名/全校参评课程总数：${evaluateTeacher.rank}/${evaluateCollege.count}</td>
			<td>院系排名/院系参评课程总数：${evaluateTeacher.departRank}/${evaluateDepartment.count}</td>
		</tr>
	</table>
	<#--
	-->
	<script>
		var bar = new ToolBar("bar", "个人统计结果－课程评价", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>