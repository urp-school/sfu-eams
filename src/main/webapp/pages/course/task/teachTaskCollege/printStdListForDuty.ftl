<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<body onload="SetPrintSettings()">
<table id="dutyStdListBar" width="100%"> </table>
<script>
   var bar = new ToolBar("dutyStdListBar","<@msg.message key="task.attendanceSheet" />",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addClose("<@msg.message key="action.close"/>");
</script>
<#assign stdCountPerPage=40>
<#list tasks as task>
<#--
<#assign units = task.arrangeInfo.weeks>
-->
<#assign units = 12>
<#assign stdIndex=1>
 <table width="100%">
	 <tr>
		 <td align="center" colspan="3">
		 <label class="contentTableTitleTextStyle"><B><@i18nName systemConfig.school/>学生学习情况卡</B></label>
		 </td>
	 </tr> 
	 <tr>
		 <td align="center" colspan="3">
		 <label class="contentTableTitleTextStyle"><B> <#if task.calendar.term='1'><@bean.message key="course.yearTerm1" arg0=task.calendar.year/><#elseif task.calendar.term='2'><@bean.message key="course.yearTerm2" arg0=task.calendar.year/><#else><@bean.message key="course.yearTermOther" arg0=task.calendar.year arg1=task.calendar.term/></#if></B></label>
		 </td>
	 </tr>
	 <tr class="infoTitle">
	     <td><@msg.message key="attr.taskNo"/>：${task.seqNo}</td>
	     <td><@msg.message key="attr.courseName"/>：<@i18nName task.course/></td>
	     <td><@msg.message key="entity.studentType"/>：<@i18nName task.teachClass.stdType/></td>
	 </tr>
	 <tr class="infoTitle">
	     <td>授课教师：<@getTeacherNames task.arrangeInfo.teachers/></td>
	     <td colspan="2"><@msg.message key="task.arrangeInfo"/>:${arrangeInfos[task.id?string]}</td>	     
	 </tr>
    <tr class="infoTitle">
      <td>说明</td>
      <td>1.出席&nbsp;&radic;&nbsp;&nbsp;旷课&nbsp;&chi;&nbsp;&nbsp;病假&nbsp;&Omicron;&nbsp;&nbsp;事假&nbsp;&Delta;&nbsp;&nbsp;迟到&nbsp;&Phi;</td>
      <td>任课老师签名：<#list 1..10 as j>&nbsp;</#list>年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</td>
    </tr>
</table>
<#list courseTakes[task.id?string]?sort_by(["student","code"])?chunk(stdCountPerPage) as subCourseTakes>
  <table width="100%" border="0" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="3%">序号</td>
      <td width="8%"><@msg.message key="attr.stdNo"/></td>
      <td width="8%"><@msg.message key="attr.personName"/></td>  
      <td width="5%"><@msg.message key="entity.gender"/></td>
      <td width="10%">修读类别</td>
      <td width="22%">班级</td>
      <#list 1..units as i>
      <td></td>
      </#list>
      <td width="5%">平时</td>
      <td width="5%">期末</td>
    </tr>
    <#list subCourseTakes as take>
     <tr class="brightStyle" align="center" >
      <td>${stdIndex}</td>
      <td>${take.student.code}</td>
      <td>${take.student.name}</td>
      <td><@i18nName take.student.basicInfo.gender?if_exists/></td>
      <td><@i18nName take.courseTakeType/></td>
      <td align="center" <#if (((take.student.firstMajorClass.name)?default('')?length)>11)>style="font-size:9px"</#if>>${(take.student.firstMajorClass.name)?default('')}</td>
      <#list 1..units as i><td>&nbsp;</td></#list>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <#assign stdIndex=stdIndex+1>
	</#list>
	</table>
	<#if subCourseTakes_has_next><div style='PAGE-BREAK-AFTER:always'></div><#else><p>一共${task.teachClass.courseTakes?size}名学生</p></#if>
  </#list>
	<#if task_has_next><div style='PAGE-BREAK-AFTER:always'></div></#if>	
</#list>
</body> 
<#include "/templates/foot.ftl"/> 