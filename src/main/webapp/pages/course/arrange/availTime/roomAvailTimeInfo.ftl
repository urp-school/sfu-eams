<#include "/templates/head.ftl"/>
<table id="roomAvailTimeBar"></table>
<#include "/pages/course/arrange/availTime/availTimeInfo.ftl" />
<form name="roomForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="classroom.id" value="${classroom.id}"/>
</form>
<script>
    var bar = new ToolBar('roomAvailTimeBar','<@bean.message key="info.availTimeOfRoom" arg0="${classroom.name}"/>',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@bean.message key="action.modify"/>",edit,'update.gif');
    function edit(){
         document.roomForm.action="roomAvailTime.do?method=editRoomAvailTime";
         document.roomForm.submit();
     }
</script> 
<#include "/templates/foot.ftl"/>