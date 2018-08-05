<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskListBar" width="100%"> </table>
<script>
   var bar = new ToolBar("taskListBar","时间设置",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   function availTime(){
      document.taskListFrame.location="";
   }
</script>
   <table class="frameTable_title" width="100%" border="0">
    <form name="calendarForm" action="teacherTime.do?method=index" method="post" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1" /> 
     <tr  style="font-size: 10pt;" align="left">
     <td id="viewTD0" class="transfer" onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)" >教学任务列表</td>
     <td id="viewTD1" class="padding"  onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">可用时间偏好设置</td>
        <#include "/pages/course/calendar.ftl"/>
      </form>
    </tr>
    </table>
    
    <table width="100%" height="90%" class="frameTable">
       <tr><td valign="top">
        <iframe name="taskListFrame" id="taskListFrame" src="teacherTime.do?method=taskList&task.calendar.id=${calendar.id}"
        width="100%" height="100%"  marginwidth="0" marginheight="0"      frameborder="0"  ></iframe>
        </td></tr>
    </table>

 <script>
   var viewNum=2;
   function changeView1(id,event){
     changeView(getEventTarget(event));
     if(id=="view1"){
        taskListFrame.location="teacherTime.do?method=taskList&task.calendar.id=${calendar.id}";
     }else{
        taskListFrame.location="teacherTime.do?method=availTimeInfo";
     }
   }
    </script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 

</body>
<#include "/templates/foot.ftl"/>