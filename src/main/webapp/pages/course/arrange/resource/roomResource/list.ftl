<#include "/templates/head.ftl"/>
<#include "../resourceListHead.ftl"/>
 <@table.table id="listTable" style="width:100%" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="classroomId"/>
    <@table.sortTd name="attr.infoname" id="classroom.name" width="20%"/>
    <@table.sortTd name="common.building" id="classroom.building.name" width="20%"/>
    <@table.sortTd name="common.classroomConfigType" id="classroom.configType" width="20%"/>
    <@table.sortTd name="attr.capacityOfExam" id="classroom.capacityOfExam" width="15%"/>
    <@table.sortTd name="attr.capacityOfCourse" id="classroom.capacityOfCourse" width="15%"/>
   </@>
   <@table.tbody datas=rooms;classroom>
     <@table.selectTd type="checkbox" id="classroomId" value="${classroom.id}"/>
      <td>
       <a href="#" onclick="getOccupyInfo('${classroom.id}')" target="_self" title="点击查看详细占用情况">
          ${classroom.name}</a></td>
      <td><@i18nName classroom.building?if_exists/></td>
      <td><@i18nName classroom.configType/></td>
      <td>${classroom.capacityOfExam?if_exists}</td>
      <td>${classroom.capacityOfCourse?if_exists}</td>
   </@table.tbody>
 </@table.table>
<#include "../resourceListFoot.ftl"/>