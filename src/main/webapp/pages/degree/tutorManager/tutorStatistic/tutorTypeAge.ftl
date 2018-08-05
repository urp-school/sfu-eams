<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','导师职称年龄分布',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack();
</script>
<div align="center">导师职称年龄分布表</div>
<table width="100%" align="center" class="listTable">
	<tr class="darkColumn" align="center">
		<td >描述</td>
		<#list scoreList?if_exists as score>
			<td>
			<#if score.min==0>
			${score.max}以下
			<#elseif score.max==100>
			${score.min}以上
			<#else>
			${score.min}-${score.max}
			</#if>
			</td>
		</#list>
	</tr>
	<tr class="grayStyle" align="center">
		<td>博导男</td>
		<#list scoreList?if_exists as score>
			<td>${tutorTypeGendarMap["1-3-"+score.min]?default(0)?string}</td>
		</#list>
	</tr>
	<tr class="grayStyle" align="center">
		<td>博导女</td>
		<#list scoreList?if_exists as score>
			<td>${tutorTypeGendarMap["2-3-"+score.min]?default(0)?string}</td>
		</#list>
	</tr>
	<tr class="grayStyle" align="center">
		<td>硕导男</td>
		<#list scoreList?if_exists as score>
			<td>${tutorTypeGendarMap["1-4-"+score.min]?default(0)?string}</td>
		</#list>
	</tr>
	<tr class="grayStyle" align="center">
		<td>硕导女</td>
		<#list scoreList?if_exists as score>
			<td>${tutorTypeGendarMap["2-4-"+score.min]?default(0)?string}</td>
		</#list>
	</tr>
	<tr class="darkColumn">
		<td height="25px;" colspan="${scoreList?if_exists?size+1}"></td>
	</tr>
</table>
<body>
<#include "/templates/foot.ftl"/>