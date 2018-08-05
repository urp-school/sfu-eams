<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="courseArrangeAlteration" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="alterationId"/>
			<@table.sortTd name="attr.taskNo" id="alteration.task.seqNo"/>
			<@table.sortTd name="attr.courseNo" id="alteration.task.course.code"/>
			<@table.sortTd name="attr.courseName" id="alteration.task.course.name"/>
			<@table.td name="entity.teacher" width="10%"/>
			<@table.td text="调课前信息"/>
			<@table.td text="调课后信息"/>
			<@table.sortTd text="调课人" id="alteration.alterBy.name"/>
			<@table.sortTd text="调课时间" id="alteration.alterationAt"/>
		</@>
		<@table.tbody datas=alterations;alteration>
			<@table.selectTd id="alterationId" value=alteration.id/>
			<td width="10%">${alteration.task.seqNo}</td>
			<td width="10%">${alteration.task.course.code}</td>
			<td width="12%"><@i18nName alteration.task.course/></td>
			<!--上课老师 lzs  -->
			<td><@getTeacherNames alteration.task.arrangeInfo.teachers/></td>
			<td width="20%">${alteration.alterationBefore}</td>
			<td width="20%">${alteration.alterationAfter}</td>
			<td width="20%">${alteration.alterBy.name}</td>
			<td width="10%">${alteration.alterationAt?string("yyyy-MM-dd")}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="courseArrangeAlteration.do" entity="alteration"></@>
	<script>
		var bar = new ToolBar("bar", "调课变动情况列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
	</script>
</body>
<#include "/templates/foot.ftl"/>