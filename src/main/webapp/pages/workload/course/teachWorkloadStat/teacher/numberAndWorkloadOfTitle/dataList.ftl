<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教师职称工作量合计表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>

  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
   	<form name="listForm" method="post" action="" onsubmit="return false;">
  	<tr>
  		<td align="left"><@bean.message key="workload.statisticTimes"/>:${result.teachCalendar.year}  ${result.teachCalendar.term}</td>
  		<td align="right"><@msg.message key="entity.studentType"/>:当前学生类别及其子类</td>
  	</tr>
  	<tr>
  		<td colspan="2">
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td><@bean.message key="common.teacherTitle"/></td>
  					<td><@bean.message key="workload.teachTeacherNumber"/></td>
  					<td><@bean.message key="workload.workloadUnits"/></td>
  					<td><@bean.message key="workload.workloadAverageOfPeople"/></td>
  					<td>上课教师人数所占比重</td>
  					<td>工作量所占比重</td>
  				</tr>
  				<#assign pN =0>
  				<#assign tW =0>
  				<#list result.teacherTitleAndWorkloadList as titleAndWorkload>
  					<#assign pN=pN+titleAndWorkload[0]>
  					<#assign tW=tW+titleAndWorkload[1]>
  				</#list>
  				<#list result.teacherTitleAndWorkloadList as titleWorkload>
  					<#if titleWorkload_index%2==0><#assign class="grayStyle"><#else><#assign class="brightStyle"></#if>
  					<tr class="${class}" align="center">
  						<td>${titleWorkload[2]?if_exists.name?if_exists}</td>
  						<td>${titleWorkload[0]?string}</td>
  						<td>${titleWorkload[1]?string("##0.0")}</td>
  						<td><#if titleWorkload[0]=0>--<#else>${(titleWorkload[1]/titleWorkload[0])?string("##0.0")}</#if></td>
  						<td><#if pN=0>--<#else>${(titleWorkload[0]/pN)?string.percent}</#if></td>
  						<td><#if tW=0>--<#else>${(titleWorkload[1]/tW)?string.percent}</#if></td>
  					</tr>
  				</#list>
  				<tr <#if result.teacherTitleAndWorkloadList?size%2==0>class="grayStyle"<#else>class="brightStyle"</#if> align="center">
  					<td><@bean.message key="workload.totleCalc"/></td>
  					<td>${pN}</td>
  					<td>${tW?string("##0.0")}</td>
  					<td><#if pN=0>0<#else>${(tW/pN)?string("###0.0")}</#if></td>
  					<td>100%</td>
  					<td>100%</td>
  				</tr>
  				<tr>
  					<td colspan="6" height="25px;" class="darkColumn"></td>
  				</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td align="left" colspan="2">
  			备注:
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>