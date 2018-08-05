<#include "/templates/head.ftl"/>
<BODY>
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="8%" name="attr.code" id="classroom.code"/>
       <@table.sortTd width="15%" name="attr.infoname" id="classroom.name"/>
       <@table.sortTd name="entity.building" id="classroom.building.name"/>
       <@table.sortTd width="15%" name="entity.classroomConfigType" id="classroom.configType.name"/>
       <@table.sortTd width="15%" name="attr.capacityOfExam" id="classroom.capacityOfExam"/>
       <@table.sortTd width="15%" name="attr.capacityOfCourse" id="classroom.capacityOfCourse"/>
       <@table.sortTd width="10%" text="容量" id="classroom.capacity"/>
       <@table.sortTd text="是否排课检查" id="classroom.isCheckActivity"/>
    </@>
    <@table.tbody datas=classrooms;classroom>
       <@table.selectTd id="classroomId" type="radio" value="${classroom.id}"/>
       <td>${classroom.code}</td>
       <td><a href="classroom.do?method=info&classroom.id=${classroom.id}">${classroom.name}</a></td>
       <td><@i18nName classroom.building?if_exists/></td>
       <td><@i18nName classroom.configType/></td>
       <td>${classroom.capacityOfExam?if_exists}</td>
       <td>${classroom.capacityOfCourse?if_exists}</td>
       <td>${(classroom.capacity)?default(0)}</td>
       <td>${(classroom.isCheckActivity)?default(true)?string("是", "否")}</td>
    </@>
  </@>
 <script language="javascript">
   type="classroom";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
</body>
<#include "/templates/foot.ftl"/>