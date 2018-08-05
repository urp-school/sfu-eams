<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="classroomId"/>
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
       <@table.selectTd id="classroomId" value=classroom.id/>
       <td>${classroom.code}</td>
       <td><a href="classRoomSearch.do?method=info&classroomId=${classroom.id}">${classroom.name}</a></td>
       <td><@i18nName classroom.building?if_exists/></td>
       <td><@i18nName classroom.configType/></td>
       <td>${classroom.capacityOfExam?if_exists}</td>
       <td>${classroom.capacityOfCourse?if_exists}</td>
       <td>${(classroom.capacity)?default(0)}</td>
       <td>${(classroom.isCheckActivity)?default(true)?string("是", "否")}</td>
    </@>
  </@>
  	<@htm.actionForm name="actionForm" action="classRoomSearch.do" entity="classroom">
  		<input type="hidden" name="keys" value="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,capacityOfCourse,capacityOfExam,floor,configType.name,building.name,schoolDistrict.name"/>
  		<input type="hidden" name="titles" value="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.capacityOfCourse"/>,<@msg.message key="attr.capacityOfExam"/>,<@msg.message key="attr.floor"/>,<@msg.message key="entity.classroomConfigType"/>,<@msg.message key="entity.building"/>,<@msg.message key="entity.schoolDistrict"/>"/>
  	</@>
	<script>
		var bar = new ToolBar("bar", "教室信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
	</script>
</body>
<#include "/templates/foot.ftl"/>