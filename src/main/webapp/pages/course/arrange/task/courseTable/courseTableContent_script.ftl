<script>   
    var table${tableIndex} = new CourseTable(${calendar.startYear},${unitList?size*setting.weekInfos?size});
	var unitCount = ${unitList?size};
	var index=0;
	var activity=null;
	<#list activityList?if_exists as activity>
	   <#if RequestParameters['setting.kind']?if_exists=="room">
	   activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}(${activity.task.seqNo})","${activity.task.course.name}(${activity.task.seqNo})","","","${activity.time.validWeeks}");
	   <#elseif RequestParameters['setting.kind']?if_exists=="teacher">
	   activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","","${activity.task.course.id}(${activity.task.seqNo})","${activity.task.course.name}(${activity.task.seqNo})","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
	   <#elseif activity.task.requirement.isGuaPai=true && RequestParameters['setting.kind']?if_exists=="class">
		   <#assign displayEntity=activity.task.course>
		   <#if activity.task.taskGroup?exists>
		     <#if activity.task.taskGroup.course?exists>
		      <#assign displayEntity=activity.task.taskGroup.course>
		     <#elseif activity.task.taskGroup.courseType?exists>
		      <#assign displayEntity=activity.task.taskGroup.courseType>
		     </#if>
		   </#if>
       activity = new TaskActivity("","","${displayEntity.id}","${displayEntity.name}","","","${activity.time.validWeeks}");
       <#else>
       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}(${activity.task.seqNo})","<@i18nName activity.task.course/>(${activity.task.seqNo})","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
       </#if>
       <#list 1..activity.time.unitCount as unit>
       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
       table${tableIndex}.activities[index][table${tableIndex}.activities[index].length]=activity;
       </#list>
	</#list>	
	table${tableIndex}.marshalTable(${calendar.weekStart},${startWeek},${endWeek});
	fillTable(table${tableIndex},${setting.weekInfos?size},${unitList?size},${tableIndex});
</script>