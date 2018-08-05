<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<@table.table id="evaluateTeacherStat" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="evaluateTeacherId"/>
			<@table.sortTd text="学年度" id="evaluateTeacher.calendar.year,evaluateTeacher.calendar.term"/>
			<@table.sortTd name="attr.courseNo" id="evaluateTeacher.course.code"/>
			<@table.sortTd name="course.titleName" id="evaluateTeacher.course.name"/>
			<@table.sortTd text="个人得分" id="evaluateTeacher.sumScore"/>
			<@table.sortTd text="全校排名" id="evaluateTeacher.rank"/>
			<@table.sortTd text="院系排名" id="evaluateTeacher.departRank"/>
		</@>
		<@table.tbody datas=evaluateTeachers?if_exists;evaluateTeacher>
			<@table.selectTd id="evaluateTeacherId" value=evaluateTeacher.id/>
			<td>${evaluateTeacher.calendar.year} ${evaluateTeacher.calendar.term}</td>
			<td>${evaluateTeacher.course.code}</td>
			<td><@i18nName evaluateTeacher.course/></td>
			<td>${(evaluateTeacher.sumScore?string("0.00"))?if_exists}</td>
			<td>${(evaluateTeacher.rank)?if_exists}</td>
			<td>${(evaluateTeacher.departRank)?if_exists}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="evaluateDetailSearch.do" entity="evaluateTeacher" onsubmit="return false;">
	</@>
	<script>
		var bar = new ToolBar("bar", "教师个人评教记录", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看详情", "info()", "detail.gif");
	</script>
</body>
<#include "/templates/foot.ftl"/>