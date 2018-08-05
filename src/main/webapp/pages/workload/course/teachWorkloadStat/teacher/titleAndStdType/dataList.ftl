<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','工作量由各类职称教师完成比例表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	<form name="listForm" method="post" action="" onsubmit="return false;">
  	<tr>
  		<td align="left"><@bean.message key="workload.statisticTimes"/>:${result.teachCalendar.year}  ${result.teachCalendar.term} 
  		</td>
  		<td align="right">学生类别:当前学生类别的子类</td>
  	</tr>
  	<tr>
  		<td colspan="2">
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td><@bean.message key="common.teacherTitle"/></td>
  					<#list teacherTitleList?if_exists as teacherTitle>
  						<td><#if teacherTitle?exists>${teacherTitle?if_exists.name?if_exists}<#else>空</#if></td>
  					</#list>
  					<td><@bean.message key="workload.totleCalc"/></td>
  				</tr>
  				<#assign class="grayStyle">
  				<#list stdTypeList?if_exists as stdType>
  					<tr class="${class}" align="center">
  						<td>${stdType.name?if_exists}</td>
  					<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
  						<#list teacherTitleList?if_exists as teacherTitle>
  							<td><#if teacherTitle?exists>${stdTypeAndTitleMap[stdType.id?string+'-'+teacherTitle.id]?default(0)}<#else>${stdTypeAndTitleMap[stdType.id?string+'-null']?default(0)}</#if></td>
  						</#list>
  						<td>${stdTypeAndTitleMap[stdType.id?string+'-0']?default(0)}</td>
  					</tr>
  				</#list>
  				<tr class="${class}" align="center">
  					<td>合计</td>
  					<#list teacherTitleList?if_exists as teacherTitle>
  							<td><#if teacherTitle?exists>${stdTypeAndTitleMap['0-'+teacherTitle.id]?default(0)}<#else>${stdTypeAndTitleMap['0-null']?default(0)}</#if></td>
  					</#list>
  					<td>${stdTypeAndTitleMap['0-0']?default(0)}</td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="${teacherTitleList?size+2}" height="20px;">
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