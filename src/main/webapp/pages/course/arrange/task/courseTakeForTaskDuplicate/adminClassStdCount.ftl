<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<#list tasks as task>
		<#if adminClassStdMap[task.id?string]?exists>
	<table class="infoTable" style="width:80%" align="center">
		<tr>
			<td class="title">课程序号</td>
			<td class="content">${task.seqNo}</td>
			<td class="title">课程名称</td>
			<td class="content"><@i18nName task.course/></td>
			<td class="title">总人数</td>
			<td class="content">${task.teachClass.stdCount}</td>
		</tr>
	</table>
	<@table.table id="adminClassStdCount" + task.id width="80%" align="center">
		<@table.thead>
			<@table.td text="班级名称"/>
			<@table.td text="专业"/>
			<@table.td text="就读人数"/>
		</@>
		<@table.tbody datas=adminClassStdMap[task.id?string];stdCountObj>
			<td><@i18nName stdCountObj[0]/></td>
			<td><@i18nName stdCountObj[0].speciality/></td>
			<td>${stdCountObj[1]}</td>
		</@>
	</@>
			<#if task_has_next>
	<br>
			</#if>
		</#if>
	</#list>
	<script>
		var bar = new ToolBar("bar", "教学任务班级人数分布情况（实践）", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>