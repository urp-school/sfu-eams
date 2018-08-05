<div id="weekRoomDiv" style="display:none">
   <table  width="100%">
<form name="weekTeacherRoomForm" action="manualArrange.do?method=freeRoomList" method="post" target="roomListFrame" onsubmit="return false;">
    <tr>
      <input type="hidden" name="task.calendar.id" value="${task.calendar.id}" />
      <input type="hidden" name="selectedWeekUnits" value=""/>
      <input type="hidden" name="selectedWeeks" value=""/>
      <input type="hidden" name="taskWeekStart" value="${task.arrangeInfo.weekStart}"/>
      <input type="hidden" name="calendarStart" value="${task.calendar.weekStart}"/>
      <input type="hidden" name="year" value="${task.calendar.startYear}"/>
      <input type="hidden" name="containPublicRoom" value="0"/>
      <input type="hidden" name="detectCollision" value="1"/>
      <input type="hidden" name="classroom.configType.id" value="${task.requirement.roomConfigType.id}"/>
      <#assign maxStdCount = task.teachClass.stdCount/>
      <#if (maxStdCount < task.teachClass.planStdCount)>
	      <#assign maxStdCount = task.teachClass.planStdCount/>
      </#if>
      <input type="hidden" name="classroom.capacityOfCourse" value="${maxStdCount}"/>
      <input type="hidden" name="classroom.code" value=""/>
      <input type="hidden" name="<@localAttrName "classroom"/>" value=""/>
      <input type="hidden" name="pageNo" value="1"/>
      <input type="hidden" name="pageSize" value="20"/>
      <table id="arrangeRoomBar"></table>
</table>
<script> 
   var bar = new ToolBar('arrangeRoomBar','<#assign courseName><@i18nName task.course/></#assign>\
          <@bean.message key="info.manual.arrangeSetting" arg0="${courseName}"/>',null,true,true);
   bar.setMessage("<div id='selectedWeekUnitsDIV'></div>");
   bar.addItem("<@bean.message key="action.save"/>","checkRoomCount()",'save.gif');
   bar.addItem("<@bean.message key="action.back"/>","returnToCourseTable(false);",'backward.gif');
</script>
  <table width="100%" align="center">
   <tr>
   <td width="300px" valign="top">
    <table  width="100%">
    <tr class="ToolBar">
      <td style="height:22px"class="pading" style="width:10%" onclick="setOccupyWeek(2);" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@bean.message key="attr.oddWeek"/></td>
      <td class="padding" style="width:10%" onclick="setOccupyWeek(3);" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@bean.message key="attr.evenWeek"/></td>
      <td class="padding" style="width:20%" onclick="setOccupyWeek(1);" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@bean.message key="attr.continuedWeek"/></td>
      <#if (task.arrangeInfo.teachers?size>1)>
      <td class="padding" onclick="listTeacher();" style="width:20%" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@bean.message key="action.assignTeacher"/></td>
      </#if>
      <td class="padding" onclick="initFreeRoomForm('${task.requirement.roomConfigType.id}','${task.teachClass.planStdCount}');listFreeRoom(false,true,1,20);" style="width:20%" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@bean.message key="action.assignRoom"/></td> 
    </tr>
    <tr>
      <td colspan="6" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  </table>
  <table width="100%" align="center" class="listTable" id="weekRoomTeacherTable">
    <tr class="darkColumn" align="center">
      <td align="center">&nbsp;<input type="checkbox" onClick="clickWeekToggleBox()"></td>
    	<td width="20%"><@bean.message key="attr.teachWeek"/></td>
    	<td width="35%"><@bean.message key="entity.teacher"/></td>
 	    <td width="45%"><@bean.message key="entity.classroom"/></td>
    </tr>
    <#list 1..task.arrangeInfo.weeks as week>
     <tr bgcolor="#ffffff" style="font-size:12px" align="center">
        <td>
            <input type="checkBox" id="weekId${week_index+task.arrangeInfo.weekStart-1}"  name="weekId${week_index+task.arrangeInfo.weekStart-1}" value="${week}" onClick="clickWeekBox();"/>
            <input type="hidden" id="roomId${week_index+task.arrangeInfo.weekStart-1}" name="roomId${week_index+task.arrangeInfo.weekStart-1}" value=""/>
            <input type="hidden" id="teacherId${week_index+task.arrangeInfo.weekStart-1}" name="teacherId${week_index+task.arrangeInfo.weekStart-1}" value=""/>
        </td>
		<td onclick="changeRoomSelected();"><@bean.message key="attr.nthWeek" arg0=(task.arrangeInfo.weekStart+week - 1)?string/></td>
        <td id="teacherName${week_index+task.arrangeInfo.weekStart-1}" onclick="changeRoomSelected(event);"></td>
 	    <td id="roomName${week_index+task.arrangeInfo.weekStart-1}" onclick="changeRoomSelected(event);"></td>
 	    </tr>
	</#list>
   </form>
   </table>
   </td>
   <td valign="top">
     <iframe src="#" id="roomListFrame" name="roomListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="600px" width="100%"></iframe>
   </td>
   </tr>
 </table>
</div>

<script>
    function checkRoomCount() {
        // 记录人数是否超出(提供保存排课时使用
        <#--isOutCount = false;
        outCountMessage = "";
        <#list rooms as room><#list 1..task.arrangeInfo.weeks as week>if (("," + $("roomId${week_index + task.arrangeInfo.weekStart - 1}").value + ",").indexOf(",${room.id},") >= 0 && ${task.teachClass.stdCount} > ${room.capacity}) {
            outCountMessage = "所选的教室其真实容量为${room.capacity}，而这个教学任务\n的教学班实际人数为${task.teachClass.stdCount}，是否要强制保存。";
            isOutCount = true;
            if (!confirm(outCountMessage)) {
                return;
            }
        }<#if (week_index < task.arrangeInfo.weeks - 1) || room_has_next> else </#if></#list></#list>
        -->
        returnToCourseTable(true);
    }
</script>