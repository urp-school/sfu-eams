 <@table.table  width="100%" border="0">
   <@table.thead >
      <td width="1%"></td>
      <td ><@bean.message key="course.no" /></td>
      <td><@bean.message key="course.titleName"/></td>
      <td><@msg.message key="entity.courseType"/></td>
      <td><@bean.message key="attr.credit" /></td>
      <td><@bean.message key="attr.weeks" /></td>
      <td><@bean.message key="attr.weekHour"/></td>
      <td><@msg.message key="task.firstCourseTime"/></td>
      <td><@msg.message key="task.arrangeInfo"/></td>
      <td><@msg.message key="task.studentNum"/></td>
      <td><@bean.message key="attr.GP"/></td>
      <td><@msg.message key="attr.teachLangType"/></td>
      <td><@msg.message key="attr.startWeek"/></td>
    </@>
    <@table.tbody datas=taskList?sort_by("courseType","name");task>
      <@table.selectTd  type="radio" id="taskId" value="${task.id}"/>
      <td><A href="courseTableForTeacher.do?method=taskTable&task.id=${task.id}"  title="<@bean.message key="info.courseTable.lookFormTaskTip"/>"><U>${task.seqNo?if_exists}</U></a></td>
      <td><A href="teachTaskSearch.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><U><@i18nName task.course/></U></a></td>
      <td><@i18nName task.courseType/></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <#if arrangeSwitch?string("true","false")="true">
      	<#assign date =task.firstCourseTime?if_exists>
	 	<#if date?string!="">
	 	<td>${date?string("yyyy-MM-dd")}<br>${date?string("HH:mm")}</td>
	 	<#else><td></td>
	 	</#if>
       <td>
        ${task.arrangeInfo.digest(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2:day:units节 :weeks周 :room")}
       </td>
       <#else>
       <td></td>
       <td></td>
      </#if>
      <td><A href="teacherTask.do?method=printDutyStdList&teachTaskIds=${task.id}" target="_blank"><U>${task.teachClass.stdCount}</U></A></td>
      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes" /> <#else> <@bean.message key="common.no" /> </#if></td>       
      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>       
      <td>${task.arrangeInfo.weekStart}</td>
    </@>
 </@>
    <table id="courseTableBar" width="100%"></table>
    <script>
       var bar = new ToolBar("courseTableBar","<@msg.message key="entity.courseTable"/>",null,true,true);
       bar.addItem("<@msg.message key="action.print"/>","courseTableForTeacher.do?method=courseTable&setting.kind=teacher&ids=${teacher.id}&setting.forCalendar=0&calendar.id=${calendar.id}","print.gif");
    </script>
    </script>
	<#include "../../arrange/task/courseTable/courseTableContent.ftl"/>
	<#include "../../arrange/task/courseTable/courseTableRemark.ftl"/>