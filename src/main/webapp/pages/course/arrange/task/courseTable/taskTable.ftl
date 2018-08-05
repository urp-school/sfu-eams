<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign courseName><@i18nName task.course/></#assign>
<#assign labInfo><@bean.message key="task.curriculumsArrange" arg0=(task.seqNo)?default("") arg1=courseName/></#assign>
<#include "/templates/back.ftl"/>
<#assign unitList =task.calendar.timeSetting.courseUnits?sort_by("index")>
  <table width="100%" align="center" class="listTable" id="courseAgentTable">
   <#include "/pages/system/calendar/timeSetting/timeSettingBar.ftl"/>
    <tr class="darkColumn">
        <td width="7%"></td>
     	<#list unitList as unit>
		<td width="7%" align="center">${unit_index+1}</td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 1..unitList?size as unit>
   		<td id="TD${week_index*unitList?size+unit_index}"  style="backGround-Color:#ffffff"></td>
		</#list>
	</tr>
    </#list>
</table>
<#include "courseTableRemark.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script>
<script>
    var table = new CourseTable(${calendar.startYear},${unitList?size*weekList?size});
	var unitCount = ${unitList?size};
	var index=0;
	var activity=null;
	<#if arrangeSwitch>
		<#list activityList as activity>
	       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}","","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
	       <#list 1..activity.time.unitCount as unit>
	       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
	       table.activities[index][table.activities[index].length]=activity;
	       </#list>
		</#list>
	<#else>
		<#if isAdmin>
			<#list activityList as activity>
	       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}","","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
	       <#list 1..activity.time.unitCount as unit>
	       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
	       table.activities[index][table.activities[index].length]=activity;
	       </#list>
		</#list>
		</#if>	
	</#if>	
	table.marshalTable(${calendar.weekStart},${startWeek},${endWeek});
	table.fill("courseAgentTable",${weekList?size},${unitList?size});
</script>
<#include "/templates/foot.ftl"/>