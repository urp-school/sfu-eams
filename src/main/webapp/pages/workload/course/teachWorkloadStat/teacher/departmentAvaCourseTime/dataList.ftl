<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','教师人均授课工作量表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
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
  					<td>在编授课教师人数</td>
  					<td>在编授课教师总周课时</td>
  					<td>在编授课教师人均周课时</td>
  					<td>授课教师人数</td>
  					<td>授课教师总周课时</td>
  					<td>授课教师人均周课时</td>
  				</tr>
  				<#assign t1=0>
  				<#assign t2=0>
  				<#assign t3=0>
  				<#assign t4=0>
  				<#assign class="grayStyle">
  				<#list (collegeList?sort_by("code"))?if_exists as college>
  				 <#if registerNoMap[college.id?string]?default(0)!=0>
  					<tr class="${class}">
  					<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
  						<td>${college.name?if_exists}</td>
  						<td>${registerNoMap[college.id?string]?default(0)}</td>
  						<#assign t1=t1+registerNoMap[college.id?string]?default(0)>
  						<td>${registerCourseNoMap[college.id?string]?default(0)?string("##0.0")}</td>
  						<#assign t2=t2+registerCourseNoMap[college.id?string]?default(0)>
  						<td><#if registerNoMap[college.id?string]?default(0)!=0>${(registerCourseNoMap[college.id?string]?default(0)/registerNoMap[college.id?string]?default(0))?string("##0.0")}<#else>0.0</#if></td>
  						<td>${teacherNoMap[college.id?string]?default(0)}</td>
  						<#assign t3=t3+teacherNoMap[college.id?string]?default(0)>
  						<td>${teacherCourseNoMap[college.id?string]?default(0)?string("##0.0")}</td>
  						<#assign t4=t4+teacherCourseNoMap[college.id?string]?default(0)>
  						<td><#if teacherCourseNoMap[college.id?string]?default(0)!=0>${(teacherCourseNoMap[college.id?string]?default(0)/teacherNoMap[college.id?string]?default(0))?string("##0.0")}<#else>0.0</#if></td>
  					</tr>
  				  </#if>
  				</#list>
  				<tr>
  					<td>合计</td>
  					<td>${t1?default(0)}</td>
  					<td>${t2?default(0)?string("##0.0")}</td>
  					<td><#if t1!=0>${(t2/t1)?string("##0.0")}</#if></td>
  					<td>${t3?default(0)?string("##0.0")}</td>
  					<td>${t4?default(0)?string("##0.0")}</td>
  					<td><#if t3!=0>${(t4/t3)?string("##0.0")}</#if></td>
  				</tr>
  				<tr class="darkColumn">
  					<td colspan="10" height="20px;">
  					</td>
  				</tr>
  			</table>
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>