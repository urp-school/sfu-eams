<#include "/templates/head.ftl"/>
<body onload="parent.$('error').innerHTML = '';parent.$('error').style.display = 'none'">
	<table id="bar"></table>
	<@table.table width="100%" id="stdCourseGrade">
		<@table.thead>
			<@table.td text="成绩类型"/>
			<@table.td text="尚未录入"/>
			<@table.td text="录入一次"/>
			<@table.td text="录入两次"/>
		</@>
		<@table.tbody datas=results;result>
			<td><@i18nName result[0]?if_exists/></td>
			<td>${result[1]}</td>
			<td>${result[2]}</td>
			<td>${result[3]}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "成绩状态统计结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint();
	</script>
</body>
<#include "/templates/foot.ftl"/>