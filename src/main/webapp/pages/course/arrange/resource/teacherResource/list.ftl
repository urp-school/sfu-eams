<#include "/templates/head.ftl"/>
<#include "../resourceListHead.ftl"/>
 <@table.table id="listTable" style="width:100%" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="teacherId"/>
    <@table.sortTd name="teacher.code" id="teacher.code" width="15%"/>
    <@table.sortTd name="attr.personName" id="teacher.name"/>
    <@table.sortTd name="common.gender" id="teacher.gender.name"/>
    <@table.sortTd name="entity.department" id="teacher.department.name"/>
    <@table.sortTd name="entity.teacherType" id="teacher.teacherType.name"/>
    <@table.sortTd name="common.teacherTitle" id="teacher.title.name" width="15%"/>
   </@>
   <@table.tbody datas=teachers;teacher>
     <@table.selectTd id="teacherId" value=teacher.id/>
      <td>&nbsp;${teacher.code?if_exists}</td>
      <td><A href="#" onclick="getOccupyInfo('${teacher.id}')" title="点击查看详细占用情况"><@i18nName teacher/></A></td>
      <td>&nbsp;<@i18nName (teacher.gender)?if_exists/></td>
      <td>&nbsp;<@i18nName (teacher.department)?if_exists/></td>
      <td>&nbsp;<@i18nName teacher.teacherType?if_exists/> </td> 
      <td>&nbsp;<@i18nName (teacher.title)?if_exists/></td> 
   </@table.tbody>
 </@table.table>
<#include "../resourceListFoot.ftl"/>