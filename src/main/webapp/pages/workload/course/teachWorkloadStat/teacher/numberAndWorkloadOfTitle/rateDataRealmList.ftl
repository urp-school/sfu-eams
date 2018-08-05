<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<form name="listForm" method="post" action="" onsubmit="return false;">
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	<tr>
  	<td align="center" colspan="2">
  		<h2>上课教师人数,工作量,所占比重</h2>
  	</td>
  </tr>
  	<tr>
  		<td align="left"><@bean.message key="workload.statisticTimes"/>:${result.teachCalendar.year}  ${result.teachCalendar.term} 
  		</td><td align="right"><@bean.message key="entity.studentType"/>:<#if result.studentType?exists>${result.studentType.name}<#else><@bean.message key="workload.studenttypeByTakeCharge"/></#if></td>
  	</tr>
  	<tr>
  		<td colspan="2">
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td><@bean.message key="common.teacherTitle"/></td>
  					<td><@bean.message key="workload.workloadUnits"/></td>
  					<td><@bean.message key="workload.workloadUnitsIntotle"/></td>
  					<td>上课教师人数</td>
  					<td>上课教师人数所占比重</td>
  				</tr>
  				<#assign t1=0>
  				<#assign t2=0>
  				<#list result.basicWorkloadList as basic>
  					<#assign t1=t1+basic.peopleNumber>
  					<#assign t2=t2+basic.workloadNumber>
  				</#list>
  				<#list result.basicWorkloadList as basicList>
  					<#if basicList_index%2==0><#assign class="grayStyle"><#else><#assign class="brightStyle"></#if>
  					<tr class="${class}" align="center">
  						<td>${basicList?if_exists.teacherTitle?if_exists.name?if_exists}</td>
  						<td>${basicList.workloadNumber?if_exists?string("####0.0")}</td>
  						<td><#if t2==0>0.0<#else>${(basicList.workloadNumber/t2*100)?string("##0.0")}%</#if></td>
  						<td>${basicList.peopleNumber}</td>
  						<td><#if t1==0>0.0<#else>${(basicList.peopleNumber/t1*100)?string("##0.0")}%</#if></td>
  						
  					</tr>
  				</#list>
  				<tr <#if result.basicWorkloadList?size%2==0>class="grayStyle"<#else>class="brightStyle"</#if> align="center">
  					<td><@bean.message key="workload.totleCalc"/></td>
  					<td>${t2}</td>
  					<td>100.0%</td>
  					<td>${t1}</td>
  					<td>100.0%</td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="5" height="20px;">
  				</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td align="left" colspan="2">
  			<@bean.message key="workload.titleBasicWorkloadRemark"/>
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>