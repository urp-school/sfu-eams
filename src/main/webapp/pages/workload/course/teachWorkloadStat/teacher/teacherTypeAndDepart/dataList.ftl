<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教学单位群体考核数据表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	<form name="listForm" method="post" action="" onsubmit="return false;">
  	<tr>
  		<td align="left"><@msg.message key="workload.statisticTimes"/>:${teachCalendar.year}  ${teachCalendar.term} 
  		</td><td align="right"><@msg.message key="entity.studentType"/>:当前学生类别及其子类</td>
  	</tr>
  	<tr>
  		<td colspan="2">
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td>开课院系</td>
  					<#list teacherTypeList?if_exists as teacherType>
  						<td><#if teacherType?exists>${teacherType.name?if_exists}<#else>空</#if></td>
  					</#list>
  					<td><@msg.message key="workload.totleCalc"/></td>
  				</tr>
  				<#assign class="grayStyle">
  				<#list departmentList?sort_by("code") as department>
  				   <#if department.isTeaching?default(false)>
  					<tr class="${class}" align="center">
  						<td>${department.name?if_exists}</td>
						<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
						<#list teacherTypeList?if_exists as teacherType>
							<td><#if teacherType?exists>${teacherTypeAndDeparMap[department.id?string+'-'+teacherType.id?string]?default(0)?string("##0.0")}<#else>${teacherTypeAndDeparMap[department.id?string+'-null']?default(0)?string("##0.0")}</#if></td>
						</#list>
						<td>${teacherTypeAndDeparMap[department.id?string+'-0']?default(0)?string("##0.0")}</td>
  					</tr>
  				 </#if>
  				</#list>
  				<tr align="center">
  					<td>合计</td>
  					<#list teacherTypeList?if_exists as teacherType>
  						<td><#if teacherType?exists>${teacherTypeAndDeparMap['0-'+teacherType.id?string]?default(0)?string("##0.0")}<#else>${teacherTypeAndDeparMap["0-null"]?default(0)?string("##0.0")}</#if></td>
  					</#list>
  					<td>${teacherTypeAndDeparMap["0-0"]?default(0)?string("##0.0")}</td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="${teacherTypeList?size+2}" height="20px;">
  				</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td align="left" colspan="2">
  			<@msg.message key="workload.titleBasicWorkloadRemark"/>
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>