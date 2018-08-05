<#include "/templates/head.ftl"/>
  <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教师机关兼职分布表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");;
</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
  	<tr>
  		<td align="left"><font color="red"><@bean.message key="workload.statisticTimes"/>:${result.teachCalendar.year},${result.teachCalendar.term}</font></td>
  		<td align="right"><font color="red"><@bean.message key="workload.unit"/>:<#if result.statisticContextId=="1"><@bean.message key="workload.person"/><#else><@bean.message key="workload.courseTime"/></#if></font>学生类别：当前类别及其子类</td>
  	</tr>
  	<tr>
		<td colspan="2">
   			<form name="teacherQueryForm" method="post" action="" onsubmit="return false;">
				<table width="100%" align="center" class="listTable">
					<tr class="darkColumn" align="center">
						<td width="10%">开课院系</td>
							<#list result.teacherTitleList?if_exists as teacherTitle>
								<td><#if teacherTitle?exists>${teacherTitle.name}<#else>空</#if></td>
							</#list>
					</tr>
					<#assign class="grayStyle">
					<#list result.collegeList?if_exists as college>
						<tr class="${class}">
							<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
							<td>${college.name?if_exists}</td>
								<#list result.teacherTitleList?if_exists as teachTitle>
									<#if result.statisticContextId=="1">
										<td><#if teachTitle?exists>${dataMap[college.id?string+'-'+teachTitle.id?string]?default(0)?string}<#else>${dataMap[college.id?string+'-null']?default(0)?string}</#if></td>
									<#else>
										<td><#if teachTitle?exists>${dataMap[college.id?string+'-'+teachTitle.id?string]?default(0)?string("####0.0")}<#else>${dataMap[college.id?string+'-null']?default(0)?string("##0.0")}</#if></td>
									</#if>
								</#list>
						</tr>
					</#list>
					<tr class="${class}">
						<td>合计</td>
								<#list result.teacherTitleList?if_exists as teachTitle>
									<#if result.statisticContextId=="1">
										<td><#if teachTitle?exists>${dataMap["0-"+teachTitle.id?string]?default(0)?string}<#else>${dataMap["0-null"]?default(0)?string}</#if></td>
									<#else>
										<td><#if teachTitle?exists>${dataMap["0-"+teachTitle.id?string]?default(0)?string("###0.0")}<#else>${dataMap["0-null"]?default(0)?string("###0.0")}</#if></td>
									</#if>
								</#list>
					</tr>
					<tr class="darkColumn">
						<td height="25px;" colspan="${result.teacherTitleList?size+1}"></td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
  </table>
  </body>
  <#include "/templates/foot.ftl"/>