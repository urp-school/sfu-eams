<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@bean.message key="field.questionnarieStatistic.collegeOfYourOperator"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  <tr>
  	<td>
  		<table width="100%" align="center" class="listTable">
  			<tr class="darkColumn">
  				<td rowspan="2" align="center"><@bean.message key="field.cultivateCourse.department"/></td>
  				<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.A"/></td>
  				<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.B"/></td>
  				<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.C"/></td>
  				<td colspan="2" align="center"><@bean.message key="field.questionnaireStatistic.D"/></td>
  				<td align="center" rowspan="2"><@bean.message key="field.questionnaireStatistic.personAmount"/></td>
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
  			<#assign t1=0>
  			<#assign t2=0>
  			<#assign t3=0>
  			<#assign t4=0>
  			<#assign t5=0>
  			<#assign class="grayStyle">
  				<#list collegeList?sort_by("code")?if_exists as college>
  				<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
  					<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
  					<td align="center"><a href="questionnaireStatisticAction.do?method=pieOfEvaluate&departmentId=${college.id}&teachCalendarId=${teachCalendar?if_exists.id}">${college.name}</a></td>
  					<td align="center">${departRelatedMap[college.id+"-A"]?default(0)?string}</td>
  					<#assign t1=t1+departRelatedMap[college.id+"-A"]?default(0)>
  					<td align="center"><#if departRelatedMap[college.id+"-all"]?default(0)==0>0.0<#else>${(departRelatedMap[college.id+"-A"]?default(0)/departRelatedMap[college.id+"-all"]?default(0)*100)?string("##0.0")}</#if></td>
  					<td align="center">${departRelatedMap[college.id+"-B"]?default(0)?string}</td>
  					<#assign t2=t2+departRelatedMap[college.id+"-B"]?default(0)>
  					<td align="center"><#if departRelatedMap[college.id+"-all"]?default(0)==0>0.0<#else>${(departRelatedMap[college.id+"-B"]?default(0)/departRelatedMap[college.id+"-all"]?default(0)*100)?string("##0.0")}</#if></td>
  					<td align="center">${departRelatedMap[college.id+"-C"]?default(0)?string}</td>
  					<#assign t3=t3+departRelatedMap[college.id+"-C"]?default(0)>
  					<td align="center"><#if departRelatedMap[college.id+"-all"]?default(0)==0>0.0<#else>${(departRelatedMap[college.id+"-C"]?default(0)/departRelatedMap[college.id+"-all"]?default(0)*100)?string("##0.0")}</#if></td>
  					<td align="center">${departRelatedMap[college.id+"-D"]?default(0)?string}</td>
  					<#assign t4=t4+departRelatedMap[college.id+"-D"]?default(0)>
  					<td align="center"><#if departRelatedMap[college.id+"-all"]?default(0)==0>0.0<#else>${(departRelatedMap[college.id+"-D"]?default(0)/departRelatedMap[college.id+"-all"]?default(0)*100)?string("##0.0")}</#if></td>
  					<td align="center">${departRelatedMap[college.id+"-all"]?default(0)}</td>
  					<#assign t5=t5+departRelatedMap[college.id+"-all"]?default(0)>
  				</tr>
  				</#list>
  			<tr class="${class}" align="center">
  				<td><@bean.message key="workload.totleCalc"/></td>
  				<td>${t1}</td>
  				<td><#if t5==0>0.0%<#else>${(t1/t5*100)?string("##0.0")}</#if></td>
  				<td>${t2}</td>
  				<td><#if t5==0>0.0%<#else>${(t2/t5*100)?string("##0.0")}</#if></td>
  				<td>${t3}</td>
  				<td><#if t5==0>0.0%<#else>${(t3/t5*100)?string("##0.0")}</#if></td>
  				<td>${t4}</td>
  				<td><#if t5==0>0.0%<#else>${(t4/t5*100)?string("##0.0")}</#if></td>
  				<td>${t5}</td>
  			</tr>
  			<tr class="darkColumn" align="center">
  				<td colspan="10" height="25px;">
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
  </table>
</body>
<#include "/templates/foot.ftl"/>