<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table width="100%" border="0" class="listTable">
	    <tr align="center" class="darkColumn">
	      <td width="1%"></td>
	      <td ><@bean.message key="attr.taskNo" /></td>
	      <td><@bean.message key="attr.courseName"/></td>
	      <td><@msg.message key="entity.courseType"/></td>
	      <td>排课建议</td>
	      <td><@bean.message key="attr.credit" /></td>
	      <td><@bean.message key="attr.weeks" /></td>
	      <td><@bean.message key="attr.weekHour"/></td>
	      <td>人数</td>
	      <td><@bean.message key="attr.GP"/></td>
	      <td><@msg.message key="attr.teachLangType"/></td>
	      <td><@msg.message key="attr.startWeek"/></td>
	    </tr>
	    <#list taskList?sort_by("courseType","name") as task>
	   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
		  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
	     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"
	        onmouseout="swapOutTR(this)" 
	        onclick="onRowChange(event)">
	      <td><input type="radio" name="taskId" value="${task.id}"></td>
	      <td><A href="courseTableForTeacher.do?method=taskTable&task.id=${task.id}"  title="<@bean.message key="info.courseTable.lookFormTaskTip"/>"><U>${task.seqNo?if_exists}</U></a></td>
	      <td><A href="teachTaskSearch.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><U><@i18nName task.course/></U></a></td>
	      <td><@i18nName task.courseType/></td>
	      <td><A href="teacherTime.do?method=suggest&task.id=${task.id}&task.calendar.id=${RequestParameters['task.calendar.id']}&inIframe=1">查看排课建议</A></td>
	      <td>${task.course.credits}</td>
	      <td>${task.arrangeInfo.weeks}</td>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td><A href="teacherTask.do?method=printDutyStdList&teachTaskIds=${task.id}" target="_blank"><U>${task.teachClass.stdCount}</U></A></td>
	      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes" /> <#else> <@bean.message key="common.no" /> </#if></td>       
	      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>       
	      <td>${task.arrangeInfo.weekStart}</td>
	    </tr>
		</#list>
    </table>
<#include "/templates/foot.ftl"/>