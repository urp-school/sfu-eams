<@table.table width="100%" id="listTable" sortable="true">
  <@table.thead>
    <@table.selectAllTd id="teachAccidentId"/>
    <@table.td name="field.teachAccident.teacherName"/>
    <@table.td name="field.teachAccident.courseId"/>
    <@table.td name="field.teachAccident.courseName"/>
    <@table.td name="field.teachAccident.college"/>
    <@table.td text="事故内容"/>
  </@>
  <@table.tbody datas=teachAccidents;teachAccident>
    <@table.selectTd id="teachAccidentId" value=teachAccident.id/>
    <td>${teachAccident.teacher.name}</td>
    <td>${teachAccident.task.course.code}</td>
    <td><@i18nName teachAccident.task.course/></td>
    <td><@i18nName teachAccident.task.arrangeInfo.teachDepart/></td>
    <td><A href='${requestAction}?method=info&teachAccidentId=${teachAccident.id}'><#if (teachAccident.description?length>10)>${teachAccident.description[1..9]}...<#else>${teachAccident.description}</#if></A></td>
   </@>
  </@>