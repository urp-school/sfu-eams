<#include "/templates/head.ftl"/>
<#if "gender"==propertyName>
	<#assign labInfo>导师性别统计</#assign>
</#if>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','${labInfo}',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
</script>
<table width="100%" align="center" class="listTable">
	<tr align="center" class="darkColumn">
		<td>教师所在部门</td>
		<#list propertyList?if_exists as property>
			<td><#if property?exists>${property.name?if_exists}<#else>无</#if></td>
		</#list>
		<td>合计</td>
	</tr>
	<#assign class="grayStyle">
	<#list departmentList?if_exists as department>
		<tr class="${class}" align="center">
		<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${department.name?if_exists}</td>
			<#list propertyList?if_exists as property>
				<td><#if property?exists>${returnMap[department.id+"-"+property.id]?default(0)?string}<#else>${returnMap[department.id+"-null"]?default(0)?string}</#if></td>
			</#list>
			<td>${returnMap[department.id+"-0"]?default(0)?string}</td>
		</tr>
	</#list>
	<tr class="${class}" style="text-align:center; font-weight: bold">
		<td>合计</td>
		<#list propertyList?if_exists as property>
			<td><#if property?exists>${returnMap["0-"+property.id]?default(0)?string}<#else>${returnMap["0-null"]?default(0)?string}</#if></td>
		</#list>
		<td>${returnMap["0-0"]?default(0)?string}</td>
	</tr>
	<tr class="darkColumn">
		<td height="25px;" colspan="${propertyList?size+2}"></td>
	</tr>
</table>
<body>
<#include "/templates/foot.ftl"/>