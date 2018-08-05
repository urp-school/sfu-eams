<#include "/templates/head.ftl"/>
<#include "../resourceListHead.ftl"/>
 <@table.table id="listTable" style="width:100%" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="adminClassId"/>
    <@table.sortTd name="attr.code" id="adminClass.code" width="10%"/>
    <@table.sortTd name="attr.infoname" id="adminClass.name" width="20%"/>
    <@table.sortTd name="attr.enrollTurn" id="adminClass.enrollYear" width="10%" />
    <@table.sortTd name="common.college" id="adminClass.department" width="15%"/>
    <@table.sortTd name="entity.speciality" id="adminClass.speciality" width="20%"/>
   </@>
   <@table.tbody datas=adminClasses;adminClass>
     <@table.selectTd type="checkbox" id="adminClassId" value="${adminClass.id}"/>
      <td>${adminClass.code}</td>
      <td>
       <a href="#" onclick="getOccupyInfo('${adminClass.id}')" target="_self" title="点击查看详细占用情况">
         ${adminClass.name?if_exists}
       </a></td>
      <td>${adminClass.enrollYear}</td>	
      <td>${adminClass.department?if_exists.name?if_exists}</td>	 
      <td>${adminClass.speciality?if_exists.name?if_exists}</td>
   </@table.tbody>
 </@table.table>
<#include "../resourceListFoot.ftl"/>