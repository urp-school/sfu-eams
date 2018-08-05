<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','硕士生导师分布',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<div align="center"><h2><@i18nName systemConfig.school/>硕士生导师名单<br>(按专业分布)</h2></div>
<table width="100%" align="center" class="listTable">
	<tr align="center" class="darkColumn">
		<td><@msg.message key="entity.department"/></td>
		<td>硕士专业名称</td>
		<td>在编硕士生导师名称(含博导)</td>
		<td>在编硕士生导师人数</td>
		<td>退休返聘硕士生导师名称(含博导)</td>
		<td>退休返聘硕士生导师人数</td>
		<td>兼职硕士生导师名称(含博导)</td>
		<td>兼职硕士生导师人数</td>
	</tr>
	<#assign class="grayStyle"/>
	<#assign sum0 = 0/>
	<#assign sum1 = 0/>
	<#assign sum2 = 0/>
	<#list specialitys?if_exists as speciality>
		<tr class="${class}" align="center">
			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${speciality.department.name?if_exists}</td>
			<td>${speciality.name?if_exists}</td>
			<td><#list specialityTutorMap[speciality.id+"ZS"]?if_exists as tutor><#if tutor_has_next>${tutor.name},<#else>${tutor.name}</#if></#list></td>
			<td><#assign zs = (specialityTutorMap[speciality.id+"ZS"]?size)?default(0)/><#assign sum0 = sum0 + zs/>${zs}</td>
			<td><#list specialityTutorMap[speciality.id+"FP"]?if_exists as tutor><#if tutor_has_next>${tutor.name},<#else>${tutor.name}</#if></#list></td>
			<td><#assign fp = (specialityTutorMap[speciality.id+"FP"]?size)?default(0)/><#assign sum1 = sum1 + fp/>${fp}</td>
			<td><#list specialityTutorMap[speciality.id+"JZ"]?if_exists as tutor><#if tutor_has_next>${tutor.name},<#else>${tutor.name}</#if></#list></td>
			<td><#assign jz = (specialityTutorMap[speciality.id+"JZ"]?size)?default(0)/><#assign sum2 = sum2 + jz/>${jz}</td>
		</tr>
	</#list>
	<tr class="darkColumn" style="text-align:center; font-weight: bold">
		<td>合计</td>
		<td></td>
		<td></td>
		<td>${sum0?default(0)}</td>
		<td></td>
		<td>${sum1?default(0)}</td>
		<td></td>
		<td>${sum2?default(0)}</td>
	</tr>
</table>
<body>
<#include "/templates/foot.ftl"/>