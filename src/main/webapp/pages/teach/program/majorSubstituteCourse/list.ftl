<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="teachPlanBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="majorCourseId"/>
      <@table.sortTd width="12%" id="majorCourse.enrollTurn" name="attr.enrollTurn"/>
      <@table.sortTd width="10%" id="majorCourse.stdType.id" name="entity.studentType"/>
      <@table.sortTd width="16%" id="majorCourse.department.id" name="entity.college"/>
      <@table.sortTd width="20%" text="专业" id="majorCourse.major.id"/>
      <@table.sortTd width="10%" text="专业方向" id="majorCourse.majorField.id"/>
      <@table.td text="要替代课程"/>
      <@table.td text="替代课程"/>
    </@>
    <@table.tbody datas=majorCourses;majorCourse>
     <@table.selectTd id="majorCourseId" value=majorCourse.id/>
     <td>${majorCourse.enrollTurn}</td>
     <td title="<@i18nName majorCourse.stdType/>" nowrap><span style="display:block; width:50px; overflow:hidden;text-overflow:ellipsis"><@i18nName majorCourse.stdType/></span></td>
     <td><@i18nName majorCourse.department?if_exists/></td>
     <td><@i18nName majorCourse.major?if_exists/></td>
     <td title="<@i18nName majorCourse.majorField?if_exists/>" nowrap><span style="display:block; width:150px; overflow:hidden;text-overflow:ellipsis"><@i18nName majorCourse.majorField?if_exists/></span></td>
     <#assign originCourse><#list majorCourse.origins as origin>${origin.name}（${origin.code}）<#if origin_has_next>, </#if></#list></#assign>
     <td title="${originCourse?html}" nowrap><span style="display:block; width:200px; overflow:hidden;text-overflow:ellipsis">${originCourse}</span></td>
     <#assign substituteCourse><#list majorCourse.substitutes as substitute>${substitute.name}（${substitute.code}）<#if substitute_has_next>, </#if></#list></#assign>
     <td title="${substituteCourse?html}" nowrap><span style="display:block; width:200px; overflow:hidden;text-overflow:ellipsis">${substituteCourse}</span></td>
    </@>
  </@>
  <@htm.actionForm name="actionForm" action="majorSubstituteCourse.do" entity="majorCourse"></@>
  <script>
    var bar=new ToolBar("teachPlanBar","替代结果",null,true,true);
    bar.setMessage('<@getMessage />');
    bar.addItem("查看", "info()", "detail.gif");
    bar.addItem("制定", "add()");
    bar.addItem("修改", "edit()");
    bar.addItem("删除", "remove()");
  </script>
</body>
<#include "/templates/foot.ftl"/>