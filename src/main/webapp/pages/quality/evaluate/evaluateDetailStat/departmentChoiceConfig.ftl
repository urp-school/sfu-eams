<#include "/templates/head.ftl"/>
<body onload="parent.clearBarInfo()">
	<table id="bar" width="100%"></table>
	<@table.table id="listTable" width="100%">
		<@table.thead>
			<@table.selectAllTd id="evaluationCriteriaId"/>
		   <@table.td width="10%" id="evaluationCriteria.name" text="名称" />
		   <@table.td width="80%" text="对应内容"/>
		</@>
		<@table.tbody datas=evaluationCriterias;evaluationCriteria>
		    <@table.selectTd id="evaluationCriteriaId" value=evaluationCriteria.id/>
		    <td>${evaluationCriteria.name}</td>
		    <td><#list evaluationCriteria.criteriaItems?sort_by("min") as item>${item.name}[${item.min}~${item.max})&nbsp;&nbsp;</#list></td>
		 </@>
	</@>
	<table width="100%">
		<tr>
			<td align="center"><button onclick="depertmentCourseStat()">按选择分值统计</button></td>
		</tr>
	</table>
	<@htm.actionForm name="actionForm" action="evaluateDetailStat.do" entity="evaluationCriteria" onsubmit="return false;">
		<input type="hidden" name="calendar.id" value="${RequestParameters["calendar.id"]}"/>
	</@>
	<script>
		var bar = new ToolBar("bar", "设置院系课程评教统计分值标准", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("统计", "depertmentCourseStat()");
		bar.addBack("<@msg.message key="action.back"/>");
		
		function depertmentCourseStat() {
			submitId(form, "evaluationCriteriaId", false, "evaluateDetailStat.do?method=${RequestParameters["forwardPage"]?default("depertmentCourseStat")}", "要按照所选择参照的分值统计吗？");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>