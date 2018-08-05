<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo><@bean.message key="field.questionnarieStatistic.collegeOfYourOperator"/></#assign>
  	<#include "/templates/back.ftl"/>
  	<table width="100%" align="center" class="listTable">
  		<tr class="darkColumn">
  			<td rowspan="2" align="center"><@bean.message key="field.cultivateCourse.department"/></td>
  			<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.A"/></td>
  			<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.B"/></td>
  			<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.C"/></td>
  			<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.D"/></td>
  			<td align="center" rowspan="2">合计人次</td>
  		</tr>
  		<tr class="darkColumn">
  			<#list 1..4 as x>
  			<td align="center">
  				<@bean.message key="field.questionnaireStatistic.Amount"/>
  			</td>
  			<td align="center">
  				<@bean.message key="field.questionnaireStatistic.proportion"/>
  			</td>
  			</#list>
  		</tr>
  		<#assign class="grayStyle">
  			<#list departmentList as department>
  			<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
  				<#if class="grayStyle"><#assign class="brightStyle" ><#else><#assign class="grayStyle" ></#if>
  				<td align="center"><a href="questionnaireStat.do?method=pieOfEvaluate&departmentId=${department.id}&teachCalendarId=${teachCalendar.id}">${department.name}</a></td>
  				<td align="center">${departRelatedMap[department.id+"-A"]?default(0)}</td>
  				<td align="center"><#if departRelatedMap[department.id+"-all"]?default(0)?string=="0">0.0<#else>${((departRelatedMap[department.id+"-A"]?default(0)/departRelatedMap[department.id+"-all"])*100)?string("##0.0")}</#if></td>
  				<td align="center">${departRelatedMap[department.id+"-B"]?default(0)}</td>
  				<td align="center"><#if departRelatedMap[department.id+"-all"]?default(0)?string=="0">0.0<#else>${((departRelatedMap[department.id+"-B"]?default(0)/departRelatedMap[department.id+"-all"])*100)?string("##0.0")}</#if></td>
  				<td align="center">${departRelatedMap[department.id+"-C"]?default(0)}</td>
  				<td align="center"><#if departRelatedMap[department.id+"-all"]?default(0)?string=="0">0.0<#else>${((departRelatedMap[department.id+"-C"]?default(0)/departRelatedMap[department.id+"-all"])*100)?string("##0.0")}</#if></td>
  				<td align="center">${departRelatedMap[department.id+"-D"]?default(0)}</td>
  				<td align="center"><#if departRelatedMap[department.id+"-all"]?default(0)?string=="0">0.0<#else>${((departRelatedMap[department.id+"-D"]?default(0)/departRelatedMap[department.id+"-all"])*100)?string("##0.0")}</#if></td>
  				<td align="center">${departRelatedMap[department.id+"-all"]?default(0)}</td>
  			</tr>
  		</#list>
  		<tr class="${class}" align="center">
  			<td><@bean.message key="workload.totleCalc"/></td>
  			<td>${departRelatedMap["0-A"]?default(0)}</td>
  			<td><#if departRelatedMap["0-0"]?default(0)?string=="0">0.0<#else>${((departRelatedMap["0-A"]?default(0)/departRelatedMap["0-0"])*100)?string("##0.0")}</#if></td>
  			<td>${departRelatedMap["0-B"]?default(0)}</td>
  			<td><#if departRelatedMap["0-0"]?default(0)?string=="0">0.0<#else>${((departRelatedMap["0-B"]?default(0)/departRelatedMap["0-0"])*100)?string("##0.0")}</#if></td>
  			<td>${departRelatedMap["0-C"]?default(0)}</td>
  			<td><#if departRelatedMap["0-0"]?default(0)?string=="0">0.0<#else>${((departRelatedMap["0-C"]?default(0)/departRelatedMap["0-0"])*100)?string("##0.0")}</#if></td>
  			<td>${departRelatedMap["0-D"]?default(0)}</td>
  			<td><#if departRelatedMap["0-0"]?default(0)?string=="0">0.0<#else>${((departRelatedMap["0-D"]?default(0)/departRelatedMap["0-0"])*100)?string("##0.0")}</#if></td>
  			<td>${departRelatedMap["0-0"]?default(0)}</td>
  		</tr>
  		<tr class="darkColumn" align="center">
  			<td colspan="10" height="25px;">
  			</td>
  		</tr>
  	</table>
</body>
<#include "/templates/foot.ftl"/>