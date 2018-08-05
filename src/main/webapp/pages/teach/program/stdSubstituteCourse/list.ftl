<#include "/templates/head.ftl"/>
<body>
<table id="stdSubstituteCourseBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="substitutionCourseId"/>   
      <@table.sortTd  width="10%" id="substitutionCourse.std.code" name="std.code"/>
      <@table.sortTd  width="10%" id="substitutionCourse.std.name" name="attr.personName"/>
      <@table.sortTd  width="15%" id="substitutionCourse.std.department.id" text="院系"/>
      <@table.sortTd  width="15%" id="substitutionCourse.std.firstMajor.id" text="专业"/>
      <@table.td width="25%" text="要替代课程"/>
      <@table.td width="25%" text="替代课程"/>
    </@>
    <@table.tbody datas=substitutionCourses;substitutionCourse>
     <@table.selectTd id="substitutionCourseId" value=substitutionCourse.id/>
         <input type="hidden" name="${substitutionCourse.id}" id="${substitutionCourse.id}" />
     <td>${(substitutionCourse.std.code)?if_exists}</td>
     <td title="<@i18nName (substitutionCourse.std)?if_exists/>" nowrap><span style="display:block; width:120px; overflow:hidden;text-overflow:ellipsis"><@i18nName (substitutionCourse.std)?if_exists/></span></td>
     <td>${(substitutionCourse.std.department.name)?if_exists}</td>
     <td>${(substitutionCourse.std.firstMajor.name)?if_exists}</td>
     <#assign originCourse><#list substitutionCourse.origins as origin>${origin.name}（${origin.code}）<#if origin_has_next>, </#if></#list></#assign>
     <td title="${originCourse?html}" nowrap><span style="display:block; width:250px; overflow:hidden;text-overflow:ellipsis">${originCourse}</span></td>
     <#assign substituteCourse><#list substitutionCourse.substitutes as substitute>${substitute.name}（${substitute.code}）<#if substitute_has_next>, </#if></#list></#assign>
     <td title="${substituteCourse?html}" nowrap><span style="display:block; width:250px; overflow:hidden;text-overflow:ellipsis">${substituteCourse}</span></td>
    </@>
  </@>
  </body>
  <@htm.actionForm name="actionForm" entity="substitutionCourse" action="stdSubstituteCourse.do"/>
<script>
    var bar=new ToolBar("stdSubstituteCourseBar","替代课程列表",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addItem("查看","info()", "detail.gif");
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
</script>
<#include "/templates/foot.ftl"/>