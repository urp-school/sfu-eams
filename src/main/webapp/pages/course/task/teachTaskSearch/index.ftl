<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>
	<script>
	  var bar=new ToolBar("taskBar","全校任务汇总查询",null,true,true);
	  bar.addItem("<@msg.message key="action.export"/>", "exportData()");
	  bar.addHelp("<@msg.message key="action.help"/>");	 
	</script> 
     <table  class="frameTable_title">
      <tr>       
       <td id="viewTD1"  style="width:50px">
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|&nbsp;</td>
      <form name="taskForm" target="teachTaskListFrame" method="post" action="teachTaskSearch.do?method=index" onsubmit="return false;">
      <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
      </tr>
     </table>
  <table width="100%"  class="frameTable" height="89%">
    <tr>
     <td valign="top"  style="width:160px" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </td>
     </form>
     <td valign="top">
     <iframe  src="#"
     id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no"
     marginwidth="0" marginheight="0"      frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
 <script>
    var form = document.taskForm;
   	function searchTask(pageNo,pageSize){
	    form.action="teachTaskSearch.do?method=arrangeInfoList";
        goToPage(form,pageNo,pageSize);
   	}
   	searchTask();
   
    function exportData(){
        if(confirm("是否导出查询出的所有任务?")){
	        form.action="teachTaskSearch.do?method=export";
	        addInput(form,"keys",'seqNo,course.code,course.name,teachClass.name,arrangeInfo.teachers,arrangeInfo.activities,requirement.roomConfigType.name,requirement.teachLangType.name,requirement.isGuaPai,teachClass.planStdCount,teachClass.stdCount,arrangeInfo.weeks,arrangeInfo.weekUnits,,arrangeInfo.courseUnits,arrangeInfo.overallUnits,credit');
	        addInput(form,'titles','课程序号,课程代码,课程名称,面向班级,授课教师,上课时间地点,教室设备配置,<@msg.message key="attr.teachLangType"/>,是否挂牌,计划人数,实际人数,周数,周课时,节次,总课时,学分');
	        form.submit();
        }
     }
   
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  