<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","查询结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addPrint("<@msg.message key="action.print"/>");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
  <h3 align="center">课程列表</h3>
 <@table.table id="statTable"  sortable="true"  id="listTable" width="100%">
 <@table.thead>
      <@table.td width="4%" text="序号"/>
      <@table.sortTd width="5%" id="fineCourse.passedYear" text="年度"/>
      <@table.sortTd width="30%" id="fineCourse.courseName" name="attr.courseName"/>
      <@table.td width="15%" text="课程负责人"/>
      <@table.sortTd width="30%" id="fineCourse.level.name" text="级别"/>
  </@>
  <@table.tbody datas=fineCourses;fineCourse,fineCourse_index>
      <td>${fineCourse_index+1}</td>
      <td>${fineCourse.passedYear?default("")}</td>
      <td>${(fineCourse.courseName)?default("")}</td>
      <td>${(fineCourse.chargeNames)?default("")}</td>
      <td><@i18nName fineCourse.level/></td>
  </@>
 </@>
</body>
<#include "/templates/foot.ftl"/> 