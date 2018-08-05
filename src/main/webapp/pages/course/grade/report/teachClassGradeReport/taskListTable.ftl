 <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.sortTd id="task.course.code" width="8%" name="attr.courseNo"/>
      <@table.sortTd id="task.course.name" width="20%" name="attr.courseName"/>
      <@table.td width="30%" name="entity.teachClass"/>
      <@table.td width="10%" name="entity.teacher"/>
      <@table.td width="5%" name="attr.stdNum"/>
      <@table.td width="5%" name="attr.credit"/>
      <@table.td width="5%" text="周时"/>
      <@table.td width="5%" text="发布"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd type="checkbox" value=task.id id="taskId"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td>${task.course.code}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td><#if task.requirement.isGuaPai>挂牌<#else>${task.teachClass.name?html}</#if></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="teachClassGradeReport.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.gradeState.isPublished(FINAL)?string("是","否")}</td>
    </@>
  </@>