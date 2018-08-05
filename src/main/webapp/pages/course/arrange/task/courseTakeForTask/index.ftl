<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="electRecordBar"></table>
 <table class="frameTable_title" width="100%">
  <tr>
   <td id="viewTD1" class="transfer"  style="width:50px">
      <font color="blue"><@bean.message key="action.advancedQuery"/></font>
   </td>
   <td>|</td>
  <form name="taskForm" target="teachTaskListFrame" method="post" action="courseTakeForTask.do?method=index" onsubmit="return false;">
  <td class="infoTitle">
     <select name="task.electInfo.isElectable" onChange="searchTask()" style="width:80px">
         <option value="1"><@bean.message key="attr.electable"/></option>
         <option value="0"><@bean.message key="attr.unelectable"/></option>
     </select>
   </td>
  <#include "/pages/course/calendar.ftl"/>
  </tr>
 </table>
  
  <table width="100%" colspacing="0" class="frameTable" height="85%">
    <tr>
     <td valign="top" style="width:160px" class="frameTable_view">
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
  var bar =new ToolBar("electRecordBar","<@bean.message key="info.elect.resultManagement"/>",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("发送筛选消息","sendFilterMessage()");
  bar.addItem("重新统计教学班人数","statStdCount()");
  //bar.addItem("筛选保留范围","reservedStudent()");
  bar.addHelp("<@msg.message key="action.help"/>");
	var taskForm =document.taskForm;
  function statStdCount(){
     taskForm.action="courseTakeForTask.do?method=statStdCount&calendar.id=${calendar.id}";
     taskForm.target="";
     taskForm.submit();
  }
  function sendFilterMessage(){
     taskForm.action="courseTakeForTask.do?method=sendFilterMessage&calendar.id=${calendar.id}";
     var turn = prompt("请输入筛选的轮次:",2);
     if (null != turn) {
         taskForm.action += "&turn=" + turn;
	     taskForm.target = "";
	     taskForm.submit();
     }
  }
   function searchTask(pageNo,pageSize){
   		taskForm.action="courseTakeForTask.do?method=taskList";
   		taskForm.target="teachTaskListFrame";
   		goToPage(taskForm,pageNo,pageSize);
   }
   /*function reservedStudent(){
       taskForm.action="reservedStudent.do?method=index&calendar.id=${calendar.id}";
       taskForm.target = "_blank";
	   taskForm.submit();
   }*/
   searchTask();
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  