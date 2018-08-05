<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '教学任务列表（院系）', null, true, false);
     bar.addItem('查看出勤情况','showReport()');
</script>
  	 <#include "teachTaskList.ftl"/>
<#include "/templates/foot.ftl"/> 
<script>
		function showReport(){
			var calendarId=document.getElementById("calendarId").value;
			ids = getSelectIds("taskId");
       		if(ids=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       		window.open("attendReportDept.do?method=showReport&taskids="+ids+"&calendarId="+calendarId,"newWindow")
			
		}
</script>