<#include "/templates/head.ftl"/>
<body onload="parent.clearBarInfo()">
	<table id="bar" width="100%"></table>
	<center>
	<div style="width:80%">
		<table>
			<tr>
				<td style="font-size: 10pt;font-weight:bold">本次课程质量评价学院统计汇总表</td>
			</tr>
		</table>
		<table class="listTable" width="100%" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
			<tr>
				<#assign criterias = evaluationCriteria.criteriaItems?sort_by("min")?reverse/>
				<#assign criteriaSize = criterias?size/>
				<td colspan="7"><#list criterias as item>${item.name}：<#if item_index == 0>${item.min}分以上<#elseif item_index == criteriaSize - 1>${item.max}分以下<#else>${item.min}－${item.max - 0.1}</#if><#if item_has_next>；</#if></#list></td>
			</tr>
			<tr>
				<#assign tdWidth = 40.0 / criteriaSize?float/>
				<td width="40%">学院（平均分）</td>
				<td width="20%"></td>
				<#list criterias as item>
				<td width="${tdWidth}%">${item.name}</td>
				</#list>
			</tr>
			<#assign teacherSum = 0/>
			<#list departmentResults?if_exists as departmentResult>
			<tr>
				<td rowspan="2"><@i18nName departmentResult[0].department/>（${departmentResult[0].sumScore?string("0.00")}）</td>
				<td>人数</td>
				<#assign teacherCount = 0/>
				<#list 1..criteriaSize as i>
				<#assign teacherCount = teacherCount + (departmentResult[i]?number)?default(0)/>
				<td>${departmentResult[i]?default(0)}</td>
				</#list>
			</tr>
			<tr>
				<td>学院比例（%）</td>
				<#list 1..criteriaSize as i>
				<td>${((departmentResult[i]?number)?default(0) / teacherCount * 100)?string("0.00")}</td>
				</#list>
			</tr>
			</#list>
			<tr>
				<td rowspan="2">学校（${((collegeResult[0].sumScore)?default(0))?string("0.00")}）</td>
				<td>人数</td>
				<#assign teacherCount = 0/>
				<#list 1..criteriaSize as i>
				<#assign teacherCount = teacherCount + (collegeResult[i]?number)?default(0)/>
				<td>${(collegeResult[i])?default(0)}</td>
				</#list>
			</tr>
			<tr>
				<td>学院比例（%）</td>
				<#list 1..criteriaSize as i>
				<td><#if teacherCount == 0>0.00<#else>${((collegeResult[i]?number)?default(0) / teacherCount * 100)?string("0.00")}</#if></td>
				</#list>
			</tr>
			<#--
			-->
		</table>
	</div>
	</center>
	<script>
		var bar = new ToolBar("bar", "院系课程评教详情", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>