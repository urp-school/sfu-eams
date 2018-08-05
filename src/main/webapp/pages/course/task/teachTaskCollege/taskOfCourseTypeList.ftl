<#include "/templates/head.ftl"/>
 <body>
 <table id="gpBar"></table>
 <script>
 </script>
 <@table.table width="85%" align="center" id="sortTable" sortable="true">
    <@table.thead align="center" class="darkColumn">
      <@table.td width="5%" text="序号"/>
      <@table.sortTd  width="40%" id="courseType.name" name="entity.courseType"/>
      <@table.sortTd  width="40%" id="adminClass.name" text="班级名称"/>
      <@table.sortTd  width="10%" id="adminClass.actualStdCount" text="人数"/>
      <@table.sortTd  width="10%" id="credits" name="attr.credit"/>
    </@>
    <@table.tbody datas=taskOfCourseTypes;task,task_index>
      <td width="5%">${task_index+1}</td>
      <td width="40%"><@i18nName task.courseType/></td>
      <td width="40%"><@i18nName task.adminClass/></td>
      <td width="10%">${task.adminClass.actualStdCount}</td>
      <td width="10%">${task.credits}</td>
    </@>
  </@>
  <script>
  	var bar = new ToolBar("gpBar","任选模块汇总(${taskOfCourseTypes?size})个",null,true,true);
  	bar.setMessage('<@getMessage/>');
  	bar.addItem("<@msg.message key="action.print"/>","print()");

    function orderBy(what){
        self.location="?method=taskOfCourseTypeList&calendar.studentType.id=${RequestParameters['calendar.studentType.id']}&calendar.id=${RequestParameters['calendar.id']}&orderBy="+what;
    }
  </script>
</body>
<#include "/templates/foot.ftl"/> 