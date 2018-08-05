<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<object id="factory2" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<style>
.task{
	font-size: 18pt; 
	font-family: 隶书; 
	PAGE-BREAK-before: always;
}
.firstTask{
	font-size: 18pt; 
	font-family: 隶书; 	
}
table.task td{
  height:40px;
}
table.firstTask td{
  height:40px;
}
.task_title {
  	text-align:center;
  	font-style: normal; 
	font-size: 25pt;
}
.task_signature {
  	text-align:right;
  	font-style: normal;
	font-size: 18pt;
}
.arrangeInfo{
  font-size: 13pt;
}
</style>
<body  onload="SetPrintSettings()">
   <#list tasks as task>
	<table width="95%" <#if task_index==0 >class="firstTask"<#else>class="task"</#if> align="center" >
	<tr><td class="task_title"><@i18nName systemConfig.school/>&nbsp;<@i18nName task.teachClass.stdType/></td></tr>
	<tr><td class="task_title">任 课 通 知 书	</td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td><@getTeacherNames task.arrangeInfo.teachers/>老师：</td></tr>
    <#if RequestParameters['teachClassName']="自动生成">
    <#assign teachClassName><#if task.requirement.isGuaPai>全校挂牌课<#else><#if (task.teachClass.adminClasses?size>=1)>${task.teachClass.name}的<#else><#if task.teachClass.enrollTurn?exists>${task.teachClass.enrollTurn[0..3]}级</#if><#if task.teachClass.speciality?exists><@i18nName task.teachClass.speciality/><@msg.message key="entity.speciality"/></#if><#if task.teachClass.aspect?exists><@i18nName task.teachClass.aspect/>方向</#if></#if></#if></#assign>
    <#else>
    <#assign teachClassName=RequestParameters['teachClassName']>
    </#if>
    <#if teachClassName?length=0>
    <#assign teachClassName=task.teachClass.name>
    </#if>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;${task.calendar.year}学年<#if task.calendar.term='1'>第一学期<#elseif task.calendar.term='2'>第二学期<#else>${task.calendar.term}</#if>
	  请您担任<U><#if teachClassName?length!=0>${teachClassName}<#else>${RequestParameters['teachClassName']?if_exists} </#if></U>《<@i18nName task.course/>》课程。</td></tr>
	<tr><td>
		<table width="100%" style="font-size:18pt" border="0">
		<tr><td width="20%"><@msg.message key="entity.courseType"/>：</td><td width="20%"><@i18nName task.courseType/></td><td width="20%"><@msg.message key="attr.courseNo"/>：</td><td>${task.course.code}</td></tr>
		<tr><td><@msg.message key="attr.taskNo"/>：</td><td>${task.seqNo}</td><td>学&nbsp;&nbsp;&nbsp;&nbsp;分：</td><td>${task.course.credits}</td></tr>
		<tr><td>人&nbsp;&nbsp;&nbsp;&nbsp;数：</td><td><#if task.teachClass.stdCount!=0>${task.teachClass.stdCount}&nbsp;人</#if></td></tr>
		<tr ><td rowspan="2" valign="top">时间地点：</td>
		     <td class="arrangeInfo" colspan="4">${arrangeInfos[task.id?string]}</td></tr>
		 <tr><td class="arrangeInfo" colspan="4">（每周${task.arrangeInfo.weekUnits}课时，上课${task.arrangeInfo.weeks}周，合计${task.arrangeInfo.overallUnits}课时）</td></tr>
	   </table>
	</td></tr>
	<tr><td>首次上课时间：<#if task.arrangeInfo.isArrangeComplete>${task.firstCourseTime?string("yyyy-MM-dd")}（年-月-日）</#if></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;${RequestParameters['remark']?if_exists}</td></tr>
	<#if (RequestParameters['telephone']?length!=0)>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;联系电话：${RequestParameters['telephone']?if_exists}</td></tr>
	</#if>
	<#if (RequestParameters['email']?length!=0)>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;EMAIL：${RequestParameters['email']?if_exists}</td></tr>
	</#if>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td class="task_signature">${RequestParameters['department']?if_exists}</td></tr>
	<tr><td class="task_signature">${RequestParameters['date']?if_exists}</td></tr>
	</table>
	<#if task_has_next><div style='PAGE-BREAK-BEFORE: always'></div></#if>
	</#list>
	<table class="ToolBar" border="1">
	 <tr>
	<td align="center">
	<button   onclick="print()" style="width:300px;" accessKey="P"><@msg.message key="action.print"/><U>(P)</U></button>
	</td></tr>
	</table>
 </body>
<#include "/templates/foot.ftl"/>
