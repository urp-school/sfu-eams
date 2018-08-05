<#include "/templates/head.ftl"/>
<body onload="parent.clearBarInfo()">
	<table id="bar" width="100%"></table>
	<table>
		<tr>
			<td style="font-size: 10pt;font-weight:bold">课程质量评价全校汇总历史对比</td>
		</tr>
	</table>
	<#assign criterias = evaluationCriteria.criteriaItems?sort_by("min")?reverse/>
	<#assign criteriaSize = criterias?size/>
	<table class="listTable" width="100%" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
		<tr>
			<td colspan="${2 + criteriaSize * 2}"><#list criterias as item>${item.name}：<#if item_index == 0>${item.min}分以上<#elseif item_index == criteriaSize - 1>${item.max}分以下<#else>${item.min}－${item.max - 0.1}</#if><#if item_has_next>；</#if></#list></td>
		</tr>
		<tr>
			<td width="9%"></td>
			<td colspan="${1 + criteriaSize * 2}" align="center">评价得分情况</td>
		</tr>
		<tr>
			<td>评价时间</td>
			<td width="6%">平均分</td>
			<#list criterias as item>
			<td width="${85.0 / criteriaSize?float * 0.45}%">${item.name}(人数)</td>
			<td width="${85.0 / criteriaSize?float * 0.55}%">所占比例(%)</td>
			</#list>
		</tr>
		<#list evaluateCollegeCalendars as evaluateCollegeCalendar>
		<tr>
			<td>${evaluateCollegeCalendar[0].calendar.year}(${evaluateCollegeCalendar[0].calendar.term})</td>
			<td>${(evaluateCollegeCalendar[0].sumScore)?string("0.00")}</td>
			<#assign teacherCount = 0/>
			<#list 1..criteriaSize as i>
				<#assign teacherCount = teacherCount + (evaluateCollegeCalendar[i]?number)?default(0)/>
			</#list>
			<#list criterias as item>
			<td>${evaluateCollegeCalendar[item_index + 1]?default(0)}</td>
			<td>${((evaluateCollegeCalendar[item_index + 1]?number)?default(0)?float / teacherCount?float * 100)?string("0.00")}</td>
			</#list>
		</tr>
		</#list>
	</table>
	<script>
		var bar = new ToolBar("bar", "历史课程评教汇总", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>