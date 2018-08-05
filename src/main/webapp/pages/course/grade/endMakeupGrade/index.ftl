<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>	
     <table class="frameTable_title">
      <tr>
       <td style="width:50px"/>
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="taskForm" target="listFrame" method="post" action="makeupGrade.do?method=index" onsubmit="return false;">
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
     <iframe src="#" id="listFrame" name="listFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
	var form = document.taskForm;
	search();   
	function search(pageNo,pageSize,orderBy){   
		form.target="listFrame";        
		taskForm.action="makeupGrade.do?method=courseList";
		goToPage(form,pageNo,pageSize,orderBy);
	}
	var bar=new ToolBar("taskBar","缓补考成绩管理",null,true,true);
	bar.addHelp("<@msg.message key="action.help"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  