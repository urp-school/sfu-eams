<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>	
     <table class="frameTable_title">
      <tr>
       <td style="width:50px"/>
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="taskForm" target="teachTaskListFrame" method="post" action="collegeGrade.do?method=index" onsubmit="return false;">
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
   <table width="100%" class="frameTable" height="89%">
    <tr>
     <td valign="top" style="width:160px" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </td>
     </form>
     <td valign="top">
     <iframe src="#" id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
	var bar=new ToolBar("taskBar","录入教学任务成绩",null,true,true);
	bar.addItem("不及格成绩"," noPassCourseGrades()");
	bar.addItem("无成绩学生名单","noGradeTakes()");
	bar.addHelp("<@msg.message key="action.help"/>");
   var form = document.taskForm;
   searchTask();
   function searchTask(pageNo,pageSize,orderBy){
     form.target="teachTaskListFrame";
	    taskForm.action="collegeGrade.do?method=search";
     goToPage(form,pageNo,pageSize,orderBy);
   }
   function noGradeTakes(){
       form.action="collegeGrade.do?method=noGradeTakeList";
       form.submit();
       }
   function noPassCourseGrades(){
       form.action="collegeGrade.do?method=noPassCourseGradeList";
       form.submit();
       }
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  