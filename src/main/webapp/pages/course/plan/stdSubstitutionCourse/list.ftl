<#include "/templates/head.ftl"/>
<body>
<table id="stdSubstitutionCourseBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="substitutionCourseId"/>   
      <@table.sortTd  width="15%" id="teachPlan.std.code" name="std.code"/>
      <@table.sortTd  width="10%" id="teachPlan.std.name" name="attr.personName"/>
      <td width="15%">被代替课程代码</td>
      <td width="15%">被代替课程名称</td>
      <td width="15%">可代替课程代码</td>
      <td width="15%">可代替课程名称</td>
      <td width="15%">备注</td>
    </@>
    <@table.tbody datas=substitutionCourses;substitutionCourse>
     <@table.selectTd id="substitutionCourseId" value=substitutionCourse.id/>
         <input type="hidden" name="${substitutionCourse.id}" id="${substitutionCourse.id}" />
     </td>
     <td>${(substitutionCourse.std.code)?if_exists}</td>
     <td><@i18nName (substitutionCourse.std)?if_exists/></td>
     <td>${(substitutionCourse.origin.code)?if_exists}</td>
     <td><@i18nName substitutionCourse.origin?if_exists/></td>
     <td>${(substitutionCourse.substitute.code)?if_exists}</td>
     <td><@i18nName substitutionCourse.substitute?if_exists/></td>
     <td>${(substitutionCourse.remark)?if_exists}</td>
    </@>
  </@>
  </body>
  <@htm.actionForm name="actionForm" entity="substitutionCourse" action="stdSubstitutionCourse.do"/>
<script>
    var bar=new ToolBar("stdSubstitutionCourseBar","替代课程列表",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
</script>
<#include "/templates/foot.ftl"/>