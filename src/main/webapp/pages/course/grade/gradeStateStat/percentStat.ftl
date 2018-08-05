<#include "/templates/head.ftl"/>
<body onload="parent.$('error').innerHTML = '';parent.$('error').style.display = 'none'">
	<table id="bar"></table>
	<@table.table id="gradePercentResultId" width="100%">
		<@table.thead>
			<@table.td text="录入百分比设定情况"/>
			<@table.td text="对应记录数"/>
		</@>
		<@table.tbody datas=results; result>
			<td width="50%"><#list 0..(result[0]?size - 1) as i><#if result[0][i]?exists><#if result[0][0] == "IsEmpty"><font color="red">未设定百分比</font><#break><#else><#if result[0][i]?is_number>&nbsp;${(((result[0][i]?float)?default(0))?string("#.##%"))?default('0.00%')}<#else><#if (i > 0 && i + 1 < result[0]?size)>，</#if><@i18nName result[0][i]/></#if></#if></#if></#list></td>
			<td>${result[1]?default(0)}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "百分比状态统计结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint();
	</script>
</body>
<#include "/templates/foot.ftl"/>