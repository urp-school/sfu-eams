<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskListBar" width="100%"> </table>
   <table class="frameTable_title" width="100%" border="0">
    <form name="calendarForm" action="teacherTask.do?method=index" method="post" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1" /> 
     <tr  style="font-size: 10pt;" align="left">
     <td>&nbsp;</td>
        <#include "/pages/course/newCalendar.ftl"/>
        </form>
     </tr>
    </table>
    <#include "taskList.ftl"/>
    <form method="post" action="" name="actionForm"></form>
    <script>
	   	var bar = new ToolBar("taskListBar", "<@bean.message key="task.list" />", null, true, true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("<@msg.message key="task.studentList" />", "doAction('stdList')", "list.gif");
	   	bar.addItem("<@msg.message key="task.attendanceSheet" />", "printStdListForDuty()", 'print.gif');
	   	bar.addItem("<@msg.message key="action.info" />", "doAction('taskInfo')");
	   	
      	var form = document.actionForm;
      	function doAction(method) {
         	var id = getRadioValue(document.getElementsByName("taskId"));
         	if ("" == id) {
         		alert("<@msg.message key="action.selectOneObject"/>");
         		return;
         	}
         	form.action = "teacherTask.do?method=" + method;
         	form.target = "_self";
         	addInput(form, "teachTask.id", id, "hidden");
         	form.submit();
      	}
      	
      	function printStdListForDuty() {
         	var id = getRadioValue(document.getElementsByName("taskId"));
         	if ("" == id) {
         		alert("<@msg.message key="action.selectOneObject"/>");
         		return;
         	}
         	form.action = "teacherTask.do?method=printDutyStdList";
         	form.target = "_blank";
         	addInput(form, "teachTaskIds", id, "hidden");
         	form.submit();
     	}
    </script>
</body>
<#include "/templates/foot.ftl"/>