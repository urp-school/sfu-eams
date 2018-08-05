 <@table.table width="100%" sortable="true" id="listTable" headIndex="1">
   <form name="taskListForm" action="" method="post" onsubmit="return false;">
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']?default(RequestParameters['calendar.id'])}"/>
    <input type="hidden" name="method" value="search">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
    </tr>
  	</form>
  	<@table.thead>
  	  <@table.selectAllTd id="taskId"/>
      <@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.sortTd id="task.course.code" name="attr.courseNo"/>
      <@table.sortTd id="task.course.name" width="20%" name="attr.courseName"/>
      <@table.sortTd id="task.courseType.name" width="10%" name="entity.courseType"/>
      <td>考试类型</td>
      <td>考试日期-时间-地点-监考人员（2名及以上）</td>
      <@table.sortTd width="4%" id="task.teachClass.planStdCount" name="teachTask.planStudents"/>
      <@table.sortTd width="4%" id="task.course.credits" name="attr.credit"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.weekUnits" name="teachTask.weeksPerHour"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.weeks" name="attr.weeks"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.overallUnits" name="attr.creditHour"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" value=task.id/>
      <td><#if task.arrangeInfo.isArrangeComplete ==false>${task.seqNo?if_exists}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</a></#if></td>
      <td id="course_${task.id}">${task.course.code}</td>
      <td><A href="?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>
      <td><@i18nName task.courseType/></td>
      <td><#if task.examMode?exists><#if task.examMode.id?exists && 1 == task.examMode.id>卷面考试<#else>非卷面考试</#if><#else></#if></td>
      <td>${task.remark?if_exists}</td>
      <td><A href="?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.planStdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td><input type="hidden" name="${task.id}" id="${task.id}" value="<#if task.isConfirm == true>1<#else>0</#if>"/>
       ${task.arrangeInfo.overallUnits}
      </td>
    </@>
  </@>
  <script>function enterQuery(event) {if (portableEvent(event).keyCode == 13)query();}</script>
