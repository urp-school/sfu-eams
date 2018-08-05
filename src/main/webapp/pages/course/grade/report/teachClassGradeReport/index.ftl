<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>	
     <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="taskForm" target="teachTaskListFrame" method="post" action="teachClassGradeReport.do?method=index" onsubmit="return false;">
      <input type="hidden" name="gradeState.confirmGA" value="1"/>
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
   <table width="100%" class="frameTable" height="89%">
    <tr>
     <td valign="top" style="width:160px" class="frameTable_view">
     <#include "../../taskBasicForm.ftl"/>
     </td>
     </form>
     <td valign="top">
     <iframe  src="#"
     id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no"
     marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
 <script>
   searchTask();
   function searchTask(pageNo,pageSize,orderBy){
        var form = document.taskForm;
        form.target="teachTaskListFrame";
	    taskForm.action="teachClassGradeReport.do?method=search";
        goToPage(form,pageNo,pageSize,orderBy);
   }
	  var bar=new ToolBar("taskBar","教学班成绩打印",null,true,true);
	  bar.addHelp("<@msg.message key="action.help"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/>
