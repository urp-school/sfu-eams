 <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.sortTd id="task.course.code" width="8%" name="attr.courseNo" />
      <@table.sortTd id="task.course.name" width="20%" name="attr.courseName"/>
      <td width="30%"><@bean.message key="entity.teachClass"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="5%"><@bean.message key="attr.stdNum"/></td>
      <td width="5%"><@bean.message key="attr.credit"/></td>
      <td width="5%">周时</td>
      <td width="5%">发布</td>
      <td width="5%" id="task.gradeState.moralGradePercent" class="tableHeaderSort">四六体系</td>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd type="checkbox" value=task.id id="taskId"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td>${task.course.code}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td><#if task.requirement.isGuaPai>挂牌<#else>${task.teachClass.name?html}</#if></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.gradeState.isPublished(FINAL)?string("是","否")}</td>
      <td>${(task.gradeState.moralGradePercent)?if_exists}</td>
    </@>
  </@>