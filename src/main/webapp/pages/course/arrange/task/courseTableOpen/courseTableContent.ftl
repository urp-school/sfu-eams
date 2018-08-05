  <#assign unitList=calendar.timeSetting.courseUnits>
  <table width="100%" align="center" class="listTable" id="courseAgentTable">
   <#include "/pages/system/calendar/timeSetting/timeSettingBar.ftl"/>
    <tr class="darkColumn">
        <td width="6%"></td>
     	<#list unitList as unit>
		<td align="center" width="7%">${unit_index+1}</td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 1..unitList?size as unit>
   		<td id="TD${week_index*unitList?size+unit_index}" style="backGround-Color:#ffffff"></td>
		</#list>
	</tr>
    </#list>
</table>

<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<script>   
    var table = new CourseTable(${calendar.startYear},${unitList?size*weekList?size});
	var unitCount = ${unitList?size};
	var index=0;
	var activity=null;
	<#list activityList as activity>
	   <#if activity.task.requirement.isGuaPai=true && RequestParameters['courseTableType']?if_exists=="class">
       activity = new TaskActivity("","","${activity.task.course.id}","${activity.task.course.name}","","","${activity.time.validWeeks}");
       <#else>
       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}(${activity.task.seqNo})","<@i18nName activity.task.course/>(${activity.task.seqNo})","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
       </#if>
       <#list 1..activity.time.unitCount as unit>
       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
       table.activities[index][table.activities[index].length]=activity;
       </#list>
	</#list>
	table.marshalTable(${calendar.weekStart},${startWeek},${endWeek});
	table.fill("courseAgentTable",${weekList?size},${unitList?size});
    frameResize('contentListFrame');
</script>