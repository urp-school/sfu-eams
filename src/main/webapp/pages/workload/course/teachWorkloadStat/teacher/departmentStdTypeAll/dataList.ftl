<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','学院授课工作量汇总表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
</script>
 <#assign stdTypeList=stdTypeList?sort_by("code")>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  <form name="listForm" method="post" action="" onsubmit="return false;">
  <tr>
  	<td align="left" colspan="2">
  		学年度:${calendar?if_exists.year?if_exists} 学期:${calendar?if_exists.term?if_exists}
  	</td>
  </tr>
  <tr>
  	<td colspan="2">
  		<table width="100%" align="center" class="listTable">
  			<tr class="darkColumn" align="center">
  				<td ><b><@bean.message key="workload.college"/></b></td>
				<#list stdTypeList?if_exists as stdType>
				<td><b><#if stdType?exists>${stdType.name?if_exists}<#else>空</#if></b></td>
				</#list>
				<td><b>合计</b></td>
  			</tr>
  			<#assign class="grayStyle">
  				<#list departmentList?sort_by("code")?if_exists as department>
  				  <#if deaprtAndStdTypeWorkloadMap[department.id?string+"-0"]?default(0)!=0>
	   					<tr class="${class}">
	   					<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
	   						<td align="center" width="5%">${department.name?if_exists}</td>
	   						<#list stdTypeList?if_exists as stdType>
	   							<td align="center" width="5%">${deaprtAndStdTypeWorkloadMap[department.id?string+"-"+stdType.id?string]?default(0)?string("##0.0")}</td>
	   						</#list>
	   						<td align="center" width="10%">${deaprtAndStdTypeWorkloadMap[department.id?string+"-0"]?default(0)?string("##0.0")}</td>
	   					</tr>
	   			 </#if>
  				</#list>
  				<tr class="brightStyle">
  					<td align="center">
  						<@bean.message key="workload.totleCalc"/></td>
  					<#list stdTypeList?if_exists as stdType>
	   					<td align="center">${deaprtAndStdTypeWorkloadMap["0-"+stdType.id?string]?default(0)?string("##0.0")}</td>
	   				</#list>
	   				<td align="center">${deaprtAndStdTypeWorkloadMap["0-0"]?default(0)?string("##0.0")}</td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="${stdTypeList?size+2}" height="25px;">
  					</td>
  				</tr>
  		</table>
  	</td>
  </tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>