 <@table.table  id="listTable" sortable="true" width="100%">
    <@table.thead>
     <@table.selectAllTd id="taskId"/>
      <td width="8%" id="task.seqNo" class="tableHeaderSort"><@bean.message key="attr.taskNo"/></td>
      <td width="8%" id="task.course.code" class="tableHeaderSort"><@bean.message key="attr.courseNo"/></td>
      <td width="20%" id="task.course.name" class="tableHeaderSort"><@bean.message key="attr.courseName"/></td>
      <td width="20%" id="task.teachClass.name" class="tableHeaderSort"><@bean.message key="entity.teachClass"/></td>
      <td width="10%" ><@bean.message key="entity.teacher"/></td>
      <td width="15%" id="task.teachClass.stdCount" class="tableHeaderSort">人数(考试/班级)</td>
      <td><@bean.message key="attr.credit"/></td>
      <td>周时</td>
      <td>周数</td>
      <td><@bean.message key="attr.creditHour"/></td>
    </@table.thead>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" type="checkbox" value="${task.id}"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td>${task.course.code}</td>
      <td title="<@i18nName task.course/>" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis"><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></span></td>
      <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis">${task.teachClass.name?html}</span></td>
      <td title="<@getTeacherNames task.arrangeInfo.teachers/>" nowrap><span style="display:block;width:75px;overflow:hidden;text-overflow:ellipsis"><@getTeacherNames task.arrangeInfo.teachers/></span></td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${(examTakeCountOfTasks[task.id?string])?default("0")}/${task.teachClass.stdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td><input type="hidden" name="${task.id}" id="${task.id}" value="${task.isConfirm?string(1, 0)}"/>
       ${task.arrangeInfo.overallUnits}
      </td>
    </@>
  </@>
