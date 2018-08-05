<#include "/templates/head.ftl"/>
<body>
	<#assign fromDate>${RequestParameters["alterFromDate"]?if_exists}</#assign>
	<#assign toDate>${RequestParameters["alterToDate"]?if_exists}</#assign>
	<table id="bar"></table>
	<@table.table id="alertStatResult" width="80%" align="center">
		<@table.thead>
			<@table.td text="变动类型"/>
			<@table.td text="变动次数"/>
		</@>
		<@table.tbody datas=results;result>
			<td><@i18nName result[0]/></td>
			<td>${result[1]}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "<#if fromDate == "" && toDate == "">所有变动时间的<#elseif fromDate != "" && toDate == "">从${fromDate}变动时间开始的<#elseif fromDate == "" && toDate != "">到${toDate}为止变动时间的<#else>从${fromDate}到${toDate}之间变动时间的</#if>统计结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>