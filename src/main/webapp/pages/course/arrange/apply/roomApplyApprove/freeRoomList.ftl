<#include "/templates/head.ftl"/>
<body>
<table id="bar2"></table>
<@table.table id="listTable" style="width:100%">
	<form method="post" action="" name="applyRoomSettingForm">
	<@searchParams/>
		<tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
			<td>
				<img src="${static_base}/images/action/search.png" align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
			</td>
			<td><input type="text" name="room.name" value="${(RequestParameters["room.name"])?if_exists}" maxlength="20" style="width:100%"/></td>
			<td><@htm.i18nSelect datas=schoolDistricts selected=(RequestParameters['room.schoolDistrict.id']?string)?default("") name="room.schoolDistrict.id" style="width:100%"><option value="">全部校区</option></@></td>
			<td><@htm.i18nSelect datas=buildings selected=(RequestParameters['room.building.id']?string)?default("") name="room.building.id" style="width:100%"><option value="">全部教学楼</option></@></td>
			<td><@htm.i18nSelect datas=types selected=(RequestParameters['room.configType.id']?string)?default("") name="room.configType.id" style="width:100%"><option value="">全部教室设备配置</option></@></td>
			<td><input type="text" name="room.capacityOfExam" value="${(RequestParameters["room.capacityOfExam"])?if_exists}" maxlength="5" style="width:100%"/></td>
			<td><input type="text" name="room.capacityOfCourse" value="${(RequestParameters["room.capacityOfCourse"])?if_exists}" maxlength="5" style="width:100%"/></td>
		</tr>
	</form>
   	<@table.thead>
	    <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('classroomId'),event);"></td>
	    <@table.td name="attr.infoname" width="20%"/>
	    <@table.td name="common.schoolDistrict" width="20%"/>
	    <@table.td name="common.building" width="20%"/>
	    <@table.td name="common.classroomConfigType" width="20%"/>
	    <@table.td name="attr.capacityOfExam" width="15%"/>
	    <@table.td name="attr.capacityOfCourse" width="15%"/>
   	</@>
   	<@table.tbody datas=rooms?if_exists;classroom>
      	<td align="center"><input type="checkBox" name="classroomId" value="${classroom.id}"></td>
      	<td><@i18nName classroom/></td>
      	<td>&nbsp;<@i18nName classroom.schoolDistrict?if_exists/></td>
      	<td>&nbsp;<@i18nName classroom.building?if_exists/></td>
      	<td>&nbsp;<@i18nName classroom.configType/></td>
      	<td>&nbsp;${classroom.capacityOfExam?if_exists}</td>
      	<td>&nbsp;${classroom.capacityOfCourse?if_exists}</td>
   	</@table.tbody>
</@table.table>
  <script>
  	var bar2=new ToolBar("bar2","空闲教室列表",null,true,true);
    bar2.addItem("选中添加","addSelected()");
     
    var detailArray = {};  
    <#list rooms as classroom>
	detailArray['${classroom.id}'] = {'name':'<@i18nName classroom/>'};
	</#list>
    function addSelected(){
       var idSeq= getSelectIds("classroomId");
       if(idSeq==""){alert("请选择");return;}
       var ids = idSeq.split(",");
       for(var i=0;i<ids.length;i++){
			if(ids[i]!=""){
             	parent.addValue(ids[i],getName(ids[i]));
          	}
       }
    } 
    function getName(id){
       if (id != ""){
          return detailArray[id]['name'];
       }else{
          return "";
       }
    }      
	function query(){
		var form = document.applyRoomSettingForm;
		form.action="roomApplyApprove.do?method=getFreeRooms";
		addInput(form, "roomApplyId", parent.document.actionForm["roomApply.id"].value, "hidden");
		form.submit();
	}
		
	function enterQuery(event) {
        if (portableEvent(event).keyCode == 13) {
        	query();
        }
    } 
   </script>
</body>
<#include "/templates/foot.ftl"/>
