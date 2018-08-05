<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="alertStatResult" width="80%" align="center">
		<@table.thead>
			<@table.td text="学籍状态"/>
			<@table.td text="学生人数"/>
		</@>
		<@table.tbody datas=results;result>
			<td><@i18nName result[0]/></td>
			<td>${result[1]}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "按学籍状态统计结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>