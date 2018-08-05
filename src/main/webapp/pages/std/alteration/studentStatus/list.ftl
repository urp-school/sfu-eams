<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <@table.table align="center" width="100%" sortable="true" id="sordId">
      <@table.thead>
         <@table.selectAllTd id="stdId"/>
         <@table.sortTd name="attr.stdNo" id="std.code"/>
         <@table.sortTd name="attr.personName" id="std.name"/>
         <@table.sortTd name="entity.studentType" id="std.type.name"/>
         <@table.sortTd name="entity.college" id="std.department"/>
         <@table.sortTd name="entity.speciality" id="std.firstMajor.name"/>
         <@table.sortTd name="filed.enrollYearAndSequence" id="std.enrollYear"/>
         <@table.sortTd name="entity.studentState" id="std.active"/>
      </@>
      <@table.tbody datas=students;student>
          <@table.selectTd id="stdId" value=student.id/>
          <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">${student.code}</a></td>
          <td><@i18nName student/></td>
          <td><@i18nName student.type?if_exists/></td>
          <td><@i18nName student.department?if_exists/></td>
          <td><@i18nName student.firstMajor?if_exists/></td>
          <td>${(student.enrollYear)?default('')}</td>
          <td><@i18nName (student.state)?if_exists/></td>
      </@>
    </@>
    <@htm.actionForm method="post" action="studentStatus.do" entity="std" name="actionFrom"/>
    <script>
        var bar = new ToolBar("bar", "学生学籍列表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("设置学籍状态", "multiAction('statusSetting')");
        bar.addItem("设置毕业时间", "multiAction('graduateOnSetting')");
    </script>
</BODY>
<#include "/templates/foot.ftl"/>