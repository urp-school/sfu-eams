<#include "/templates/head.ftl"/>
<BODY>
   <table id="taskBar"></table>	
   <table style="width:100%" class="frameTable">
    <tr>
     <td valign="top" style="width:16%" class="frameTable_view">
     <form name="taskForm" target="teachTaskListFrame" method="post" action="?method=index" onsubmit="return false;">
     <#include "../attendWarn/searchForm.ftl"/>
     </form>
     </td>     
     <td valign="top">
     <iframe src="#" id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
  var bar=new ToolBar("taskBar","全校学生维度考勤报表",null,true,true);
  search();
	function search(){
	    var form = document.taskForm;
	    taskForm.action = "?method=search";
	    form.target="teachTaskListFrame";
	    form.submit();
	}
<#--
  bar.addItem("导入","importData()");
  bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
  bar.addItem("不及格成绩"," noPassCourseGrades()");
  bar.addItem("无成绩学生名单","noGradeTakes()");
  bar.addHelp("<@msg.message key="action.help"/>");
   var form = document.taskForm;
   var action ="courseGrade.do";
   searchTask();
   function searchTask(pageNo,pageSize,orderBy){
        form.target="teachTaskListFrame";
	    taskForm.action="courseGrade.do?method=search";
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
   function downloadTemplate(){
	   self.location="dataTemplate.do?method=download&document.id=15";
   }
   function importData(){
	   form.action=action+"?method=importForm&templateDocumentId=15";
	   addInput(form,"importTitle","学生成绩上传")
	   form.submit();
   }
-->
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  