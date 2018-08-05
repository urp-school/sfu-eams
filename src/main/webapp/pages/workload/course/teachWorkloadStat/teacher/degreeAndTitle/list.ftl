<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教师学历学位统计表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	<form name="listForm" method="post" action="" onsubmit="return false;">
  	<TR>
  		<td colspan="2">学年度：${calendar.year} 学期:${calendar.term}</td>
  	</tr>
  	<tr>
  		<td>
  			<table width="100%" align="center" class="listTable">
  				<tr class="darkColumn" align="center">
  					<td rowspan="2"><b><#if degreeFlag?exists&&"1"==degreeFlag>学位<#elseif degreeFlag?exists&&"2"==degreeFlag>学历</#if></b></td>
  					<td colspan="2"><b><#if result.teacherAge?exists>${result.teacherAge}<@bean.message key="workload.people"/><#else><@bean.message key="workload.allPerson"/></#if></b></td>
  					<td rowspan="2"><b><@bean.message key="workload.rate"/></b></td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td><b><@bean.message key="workload.actual"/></b></td>
  					<td><b><@bean.message key="workload.enrolNumber"/></b></td>
  				</tr>
  				<#assign t1=0>
  				<#assign t2=0>
  				<#assign class="grayStyle"/>
  				 <#list templist?if_exists as temp>
  				  <tr class="${class}">
  				    <#if class=="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
  					<td align="center">
  						<#if temp?exists><#assign key=temp.id?string>${temp.name}<#else><#assign key="null">空</#if>
  					</td>
  					<td>
  						${workloadMap[key?string]?default(0)?string}
  						<#assign t1=t1+workloadMap[key?string]?default(0)?number>
  					</td>
  					<td>
  						${teacherMap[key?string]?default(0)?string}
  						<#assign t2 = t2+teacherMap[key?string]?default(0)?number>
  					</td>
  					<td>
  						<#if teacherMap[key?string]?default(0)?number==0>0%<#else>${(workloadMap[key?string]?default(0)?number/teacherMap[key?string]?number)?string.percent}</#if>
  					</td>
  				</tr>
  				</#list>
  				<tr class="${class}">
  					<td align="center">
  						<@bean.message key="workload.totleCalc"/>
  					</td>
  					<td  align="left">
  						${t1}
  					</td>
  					<td align="left">
  						${t2}
  					</td>
  					<td align="left">
  						<#if t2!=0>
  							${(t1/t2)?string.percent}
  						<#else>
  							0.0
  						</#if>
  					</td>
  				</tr>
  				<tr class="darkColumn" align="center">
  					<td colspan="4" height="25px;">
  						
  					</td>
  				</tr>
  			</table>
  		</td>
  	</tr>
  </table>
  </form>
</body>
 <#include "/templates/foot.ftl"/>