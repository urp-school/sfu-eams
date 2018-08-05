<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
  <@table.table width="100%" id="listTable" sortable="1">
    <@table.thead>
     <@table.selectAllTd id="teacherId"/>
     <@table.sortTd name="teacher.code" id="teacher.code" width="5%"/>
     <@table.sortTd name="attr.personName" id="teacher.name" width="10%"/>
     <@table.sortTd name="common.gender" id="teacher.gender.name" width="5%"/>
     <@table.sortTd name="entity.department" id="teacher.department.name" width="15%"/>
     <@table.sortTd name="common.phoneOfCorporation" id="teacher.addressInfo.phoneOfCorporation" width="15%"/>
    </@table.thead>
    <@table.tbody datas=teachers;teacher>
      <@table.selectTd type="checkbox" id="teacherId" value="${teacher.id}"/>
      <td>${teacher.code?if_exists}</td>
      <td><a href="?method=courseTable&setting.kind=teacher&ids=${teacher.id}&calendar.id=${calendar.id}&setting.forCalendar=0" target="_blank">
          <@i18nName teacher/>
          </a>
      </td>
      <td><@i18nName teacher.gender?if_exists/></td>
      <td><@i18nName teacher.department?if_exists/> </td>
      <td>${teacher.addressInfo?if_exists.phoneOfCorporation?if_exists}</td>
   </@table.tbody>
   </@table.table>
 <#assign courseTableType="teacher">
 <#include "courseTableSetting.ftl"/>
  <script language="javascript">
   	var bar = new ToolBar('backBar','<@bean.message key="entity.teacher"/><@bean.message key="common.list"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addItem('<@msg.message key="action.preview"/>','displaySetting()','setting.png');
   	bar.addItem('<@msg.message key="action.selectPreview"/>','printCourseTable()','print.gif');
  </script>
  </body>
<#include "/templates/foot.ftl"/>