<#include "/templates/head.ftl"/>
<BODY>
	<table id="freeRoomBar"></table>
	<@table.table width="100%" sortable="true" id="croomId" headIndex="1">
  		<form name="roomListForm" method="post" action="" onsubmit="return false;">
    		<input type="hidden" name="examType.id" value="${RequestParameters['examType.id']}"/>
    		<input type="hidden" name="taskIds" value="${RequestParameters['taskIds']}"/>
    		<input type="hidden" name="noRoomTaskIds" value="${RequestParameters['noRoomTaskIds']}"/>
    		<input type="hidden" name="timeUnit.year" value="${RequestParameters['timeUnit.year']}"/>
		    <input type="hidden" name="timeUnit.validWeeksNum" value="${RequestParameters['timeUnit.validWeeksNum']}"/>
		    <input type="hidden" name="timeUnit.startUnit" value="${RequestParameters['timeUnit.startUnit']}"/>
		    <input type="hidden" name="timeUnit.endUnit" value="${RequestParameters['timeUnit.endUnit']}"/>
		    <input type="hidden" name="timeUnit.weekId" value="${RequestParameters['timeUnit.weekId']}"/>
		    <input type="hidden" name="timeUnit.startTime" value="${RequestParameters['timeUnit.startTime']}"/>
		    <input type="hidden" name="timeUnit.endTime" value="${RequestParameters['timeUnit.endTime']}"/>
		    <input type="hidden" name="stdCount" value="${RequestParameters['stdCount']}"/>
	    	<tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
	      		<td align="center">
	        		<img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult"/>"/>
	      		</td>
	      		<td><input type="text" name="classroom.code" value="${RequestParameters['classroom.code']?if_exists}" style="width:100%" maxlength="32"/></td>
	      		<td><input type="text" name="<@localAttrName "classroom"/>" value="" style="width:100%" maxlength="20"/></td>
	      		<td>&nbsp;</td><td>&nbsp;</td>
	      		<td><input type="text" name="classroom.capacityOfExam" value="${RequestParameters['classroom.capacityOfExam']?default('')}" style="width:100%" maxlength="5"/></td>
	      		<td><input type="text" name="classroom.floor" value="${RequestParameters['classroom.floor']?default('')}" style="width:100%" maxlength="4"/></td>
	      		<td><@htm.i18nSelect name="classroom.configType.id" datas=configTypeList selected=RequestParameters['classroom.configType.id']?default("") style="width:100%"><option value="">请选择..</option></@></td>
	    	</tr>
   		</form>
    	<@table.thead>
      		<@table.selectAllTd id="classroomId"/>
     		<@table.td name="attr.code" id="room.code"/>
     		<@table.td name="attr.infoname" id="room.name"/>
     		<@table.td text="校区"/>
     		<@table.td name="entity.building" id="room.building.name"/>
      		<@table.td text="考试人数" id="room.capacityOfExam"/>
      		<@table.td text="楼层" id="room.floor"/>
      		<@table.td text="教室设备配置" id="room.configType"/>
		</@>
    	<@table.tbody datas=rooms;room>
       		<@table.selectTd id="classroomId" value=room.id/>
       		<td>${room.code}</td>
       		<td><@i18nName room/></td>
       		<td><@i18nName room.schoolDistrict?if_exists/></td>
       		<td><@i18nName room.building?if_exists/></td>
       		<td>${room.capacityOfExam}</td>
       		<td>${room.floor?if_exists}</td>
       		<td><@i18nName room.configType?if_exists/></td>
    	</@>
   	</@>
	<script language="javascript">
   		var bar = new ToolBar('freeRoomBar', '<@bean.message key="info.freeRoom.list"/>,需要容量(${RequestParameters['stdCount']})人,请选择一个或多个教室进行分配', null, true, true);
   		bar.setMessage('<@getMessage/>'); 
   		bar.addItem("分配选中的教室", 'allocRoom()', 'new.gif');
	   	bar.addBack("<@msg.message key="action.back"/>");
	   	
    	function enterQuery(event) {
        	if (event.keyCode == 13) {
         		query();
         	}
    	}
    	var form = document.roomListForm;
    	function query() {
       		form.action = "examArrange.do?method=allocateRoomSetting";
       		goToPage(form);
   		}
   		
   		function allocRoom() {
      		if(confirm("分配教室是否区分校区?")){
      			form.action = "examArrange.do?method=allocateRoom&sameDistrictWithCourse=1";
	      		submitId(document.roomListForm, "classroomId", true, null, "确定分配你选择的教室吗？");
	      		return;
      		}else{
      		    form.action = "examArrange.do?method=allocateRoom";
      		    submitId(document.roomListForm, "classroomId", true, null, "确定分配你选择的教室吗？");
      		}
   		}
  	</script>
</body>
<#include "/templates/foot.ftl"/>