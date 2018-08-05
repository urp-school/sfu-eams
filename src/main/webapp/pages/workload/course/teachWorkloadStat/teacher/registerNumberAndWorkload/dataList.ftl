<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','在编教师人数和工作量的比例表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	 	<form name="listForm" method="post" action="" onsubmit="return false;">
  	<tr>
  		<td align="left"><@bean.message key="workload.statisticTimes"/>:${result.teachCalendar.year}  ${result.teachCalendar.term} 
  		</td><td align="right">学生类别:默认为当前学生类别及其子类</td>
  	</tr>
  	<tr>
  		<td colspan="2">
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td>教师所在部门</td>
  					<td>在编授课教师</td>
  					<td>在编教师</td>
  					<td>在编授课比重</td>
  					<td>授课教师</td>
  					<td>在编授课人数占实际授课比重人数</td>
  					<td>在编教师工作量</td>
  					<td>授课教师工作量</td>
  					<td>在编工作量与实际工作量比重</td>
  				</tr>
  				<#assign class="grayStyle"/>
  				<#assign t0=0>
  				<#assign t1=0>
  				<#assign t2=0>
  				<#assign t3=0>
  				<#assign t4=0>
  				<#list departmentList?if_exists?sort_by("code") as department>
  				  <#if registerTeacherWorkloadMap[department.id?string]?default(0)==0&&registerTeacherMap[department.id?string]?default(0)==0>
  				  <#else>
  					<tr class="${class}">
  						<#if class="grayStyle"><#assign class="brightStyle"/><#else><#assign class="grayStyle"/></#if>
						<td>${department.name?if_exists}</td>
						<td>${registerTeacherWorkloadMap[department.id?string]?default(0)}</td>
						<#assign t0=t0+registerTeacherWorkloadMap[department.id?string]?default(0)>
						<td>${registerTeacherMap[department.id?string]?default(0)}</td>
						<#assign t1=t1+registerTeacherMap[department.id?string]?default(0)>
						<td><#if registerTeacherMap[department.id?string]?default(0)==0>0.0%<#else>${(registerTeacherWorkloadMap[department.id?string]?default(0)/registerTeacherMap[department.id?string])?string.percent}</#if></td>
						<td>${workloadTeacherMap[department.id?string]?default(0)}</td>
						<#assign t2=t2+workloadTeacherMap[department.id?string]?default(0)>
						<td><#if workloadTeacherMap[department.id?string]?default(0)==0>0.0%<#else>${(registerTeacherWorkloadMap[department.id?string]?default(0)/workloadTeacherMap[department.id?string])?string.percent}</#if></td>
						<td>${registerWorkloadMap[department.id?string]?default(0)?string("##0.0")}</td>
						<#assign t3=t3+registerWorkloadMap[department.id?string]?default(0)>
						<td>${teacherWorkloadMap[department.id?string]?default(0)?string("##0.0")}</td>
						<#assign t4=t4+teacherWorkloadMap[department.id?string]?default(0)>
						<td><#if teacherWorkloadMap[department.id?string]?default(0)==0>0.0%<#else>${(registerWorkloadMap[department.id?string]?default(0)/teacherWorkloadMap[department.id?string])?string.percent}</#if></td>
  					</tr>
  				  </#if>
  				</#list>
  				<tr class="${class}">
  					<td>合计</td>
  					<td>${t0}</td>
  					<td>${t1}</td>
  					<td><#if t1==0>0.0%<#else>${(t0/t1)?string.percent}</#if></td>
  					<td>${t2}</td>
  					<td><#if t2==0>0.0%<#else>${(t0/t2)?string.percent}</#if></td>
  					<td>${t3?string("##0.0")}</td>
  					<td>${t4?string("##0.0")}</td>
  					<td><#if t4==0>0.0%<#else>${(t3/t4)?string.percent}</#if></td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="9" height="20px;">
  				</tr>
  			</table>
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>