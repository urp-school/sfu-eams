	
 <@table.table id="listTable" style="width:100%" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="classroomId"/>
    <@table.sortTd name="attr.infoname" id="classroom.name"/>
    <@table.sortTd name="common.schoolDistrict" id="classroom.schoolDistrict.name"/>
    <@table.sortTd name="common.building" id="classroom.building.name"/>
    <@table.sortTd name="common.classroomConfigType" id="classroom.configType"/>
    <@table.sortTd name="attr.capacityOfExam" id="classroom.capacityOfExam" width="15%"/>
    <@table.sortTd name="attr.capacityOfCourse" id="classroom.capacityOfCourse" width="15%"/>
   </@>
   <@table.tbody datas=rooms?if_exists;classroom>
      <@table.selectTd id="classroomId" value=classroom.id/>
      <td>${classroom.name}</td>
      <td>&nbsp;<@i18nName classroom.schoolDistrict?if_exists/></td>
      <td>&nbsp;<@i18nName classroom.building?if_exists/></td>
      <td>&nbsp;<@i18nName classroom.configType/></td>
      <td>&nbsp;${classroom.capacityOfExam?if_exists}</td>
      <td>&nbsp;${classroom.capacityOfCourse?if_exists}</td>
   </@table.tbody>
 </@table.table>
