<#include "/templates/head.ftl"/>
<body>
<div align="center"><h1>学生实习单位统计表</h1></div>
<table>
<tr>
	<td align="left">时间:<#if teachCalendar?exists>学年度${teachCalendar.year} 学期${teachCalendar.term}<#else>所有学期</#if></td>
	<td align="right">学生类别:<#if stdType?exists>${stdType.name}<#else>所有学生类别</#if></td>
</tr>
</table>
<@table.table width="100%">
	<@table.thead>
		<@table.td text="院系"></@>
		<@table.td text="学生总数"></@>
		<#list sources as scource>
			<@table.td text="${scource.name}"></@>
		</#list>
		<@table.td text="实习基地人数"></@>
	</@>
	<#assign class="grayStyle">
	<#list departments as department>
		<@table.tr class="${class}">
			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<@table.td text="${department.name}"/>
			<@table.td text="${datasMap[department.id+'-0']?default(0)}"/>
			<#list sources as scource>
			    <@table.td text="${datasMap[department.id+'-'+scource.id]?default(0)}"/>
			</#list>
			<@table.td text="${datasMap[department.id+'-base']?default(0)}"></@>
		</@>
	</#list>
	<@table.tr class="${class}">
		<@table.td text="合计"></@>
		<@table.td text="${datasMap['0-0']?default(0)}"/>
		<#list sources as scource>
			 <@table.td text="${datasMap['0-'+scource.id]?default(0)}"/>
		</#list>
		<@table.td text="${datasMap['0-base']?default(0)}"></@>
	</@>
	<@table.tr class="darkColumn">
		<@table.td text="" height="25px;" colspan="${sources?size+3}"/>
	</@>
</@>
</body>
<#include "/templates/foot.ftl"/>