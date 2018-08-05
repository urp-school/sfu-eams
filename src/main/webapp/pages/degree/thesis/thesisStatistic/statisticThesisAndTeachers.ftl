<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文指导教师统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<div align="center"><h2>${stdType.name}毕业论文(设计)指导教师情况</h2></div>
<table width="100%" align="center" class="listTable">
	<tr class="darkColumn">
		<td rowspan="2">入学年份</td>
		<td rowspan="2">毕业论文数(篇)</td>
		<td colspan="${valueMap['titles']?size*2+1}" align="center">指导教师(人)</td>
		<td rowspan="2">人均指导论文数(篇)</td>
	</tr>
	<tr class="darkColumn">
		<#list valueMap['titles'] as title>
			<td><#if title?exists>${title.name}<#else>(空)</#if></td>
			<td>比例(%)</td>
		</#list>
		<td>合计</td>
	</tr>
	<#assign class="grayStyle">
	<#list needYears as year>
		<tr class="${class}">
		<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${year}</td>
			<td>${valueMap[year+'-0']?default(0)}</td>
			<#list valueMap['titles'] as title>
				<#if title?exists><#assign keyValue=year+'-teacher-'+title.id><#else><#assign keyValue=year+'-teacher-null'></#if>
				<td>${valueMap[keyValue]?default(0)}</td>
				<td><#if valueMap[year+'-teacher-0']?exists>${((valueMap[keyValue]?default(0)/valueMap[year+'-teacher-0'])*100)?string("##0.0#")}<#else>0.0</#if>
			</#list>
			<td>${valueMap[year+'-teacher-0']?default(0)}</td>
			<td><#if valueMap[year+'-0']?exists>${(valueMap[year+'-0']/valueMap[year+'-teacher-0'])?string("##0.0#")}<#else>0.0</#if></td>
		</tr>
	</#list>
	<tr class="${class}">
		<td>合计</td>
		<td>${valueMap['0-0']?default(0)}</td>
		<#list valueMap['titles'] as title>
			<#if title?exists><#assign keyValue='0-teacher-'+title.id><#else><#assign keyValue='0-teacher-null'></#if>
			<td>${valueMap[keyValue]?default(0)}</td>
			<td><#if valueMap['0-teacher-0']?exists>${((valueMap[keyValue]?default(0)/valueMap['0-teacher-0'])*100)?string("##0.0#")}<#else>0.0</#if>
		</#list>
		<td>${valueMap['0-teacher-0']?default(0)}</td>
		<td><#if valueMap['0-0']?exists>${(valueMap['0-0']/valueMap['0-teacher-0'])?string("##0.0#")}<#else>0.0</#if></td>
	</tr>
	<tr class="darkColumn">
		<td colspan="${valueMap['titles']?size*2+4}" height="20px;"></td>
	</tr>
</table>
</body>
<#include "/templates/foot.ftl"/>