<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="electRecordBar"></table>
 <table class="frameTable_title" width="100%">
  <tr>
   <td id="viewTD1" class="transfer"  style="width:50px">
      <font color="blue">详细查询</font>
   </td>
   <td>|</td>
  <form name="taskForm" target="teachTaskListFrame" method="post" action="courseTakeForTaskDuplicate.do?method=index" onsubmit="return false;">
  <#include "/pages/course/calendar.ftl"/>
  </tr>
 </table>
  <table width="100%" colspacing="0" class="frameTable" height="85%">
    <tr>
     <td valign="top" style="width:18%" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </form>
     </td>
     <td valign="top">
     <iframe src="#"
     id="teachTaskListFrame" name="teachTaskListFrame"
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0" height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
 <script>
  var bar =new ToolBar("electRecordBar", "任务管理（实践）", null, true, true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("重新统计教学班人数","statStdCount()");
  bar.addHelp("<@msg.message key="action.help"/>");
  
  var taskForm =document.taskForm;
  
  function statStdCount(){
     taskForm.action = "courseTakeForTaskDuplicate.do?method=statStdCount&calendar.id=${calendar.id}";
     taskForm.target = "";
     taskForm.submit();
  }
  function searchTask(pageNo, pageSize){
  		taskForm.action = "courseTakeForTaskDuplicate.do?method=taskList";
   		taskForm.target = "teachTaskListFrame";
   		goToPage(taskForm,pageNo,pageSize);
  }
  
  searchTask();
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  