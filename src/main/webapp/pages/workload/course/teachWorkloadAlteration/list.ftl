<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="teachWorkloadAlteration" width="100%">
		<@table.thead>
			<@table.selectAllTd id="alterationId"/>
			<@table.sortTd name="attr.taskNo" id="alteration.task.seqNo"/>
			<@table.sortTd name="attr.courseNo" id="alteration.task.course.code"/>
			<@table.sortTd name="attr.courseName" id="alteration.task.course.name"/>
			<@table.sortTd text="教师姓名" id="alteration.teacher.name"/>
			<@table.sortTd name="entity.courseCategory" id="alteration.courseCategory.name"/>
			<@table.sortTd text="修改人" id="alteration.workloadBy.name"/>
			<@table.sortTd text="修改时间" id="alteration.workloadAt"/>
		</@>
		<@table.tbody datas=alterations;alteration>
			<@table.selectTd id="alterationId" value=alteration.id/>
			<td>${alteration.task.seqNo}</td>
			<td>${alteration.task.course.code}</td>
			<td><@i18nName alteration.task.course/></td>
			<td><@i18nName alteration.teacher/></td>
			<td><@i18nName alteration.courseCategory/></td>
			<td>${alteration.workloadBy.name}</td>
			<td>${(alteration.workloadAt)?string("yyyy-MM-dd")}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="teachWorkloadAlteration.do" entity="alteration"></@>
	<script>
		var bar = new ToolBar("bar", "教学工作量日志记录", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
	</script>
</body>
<#include "/templates/foot.ftl"/>