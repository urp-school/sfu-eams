<#include "/templates/head.ftl"/>
<body >
  	<table id="taskBar"></table>
  	<@table.table id="listTable" sortable="true" width="100%">
    	<@table.thead>
      		<@table.selectAllTd id="taskId"/>
      		<@table.sortTd width="8%" id="task.seqNo" name="attr.taskNo"/>
      		<@table.sortTd width="20%" id="task.course.name" name="attr.courseName"/>
      		<@table.sortTd width="20%" id="task.teachClass.name" name="entity.teachClass"/>
      		<@table.sortTd width="5%" id="task.teachClass.stdCount" text="班级人数"/>
      		<@table.td width="20%" text="考试时间"/>
      		<@table.td width="10%" text="考试地点"/>
      		<@table.td width="10%" text="主考老师"/>
    	</@>
    	<@table.tbody datas=tasks;task>
      		<@table.selectTd  id="taskId" value=task.id/>
      		<td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      		<td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      		<td>${task.teachClass.name?html}</td>
      		<td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}</A></td>
      		<td>${task.arrangeInfo.digestExam(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],"第:weeks周 :day :time")}</td>
      		<td><@getBeanListNames task.arrangeInfo.getExamRooms(examType)?sort_by("name")/></td>
      		<td><@getBeanListNames task.arrangeInfo.getExamTeachers(examType)?sort_by("name")/></td>
    	</@>
  	</@>
  	<form name="actionForm" method="post" onsubmit="return false;" action="">
   		<input type="hidden" name="examType.id" value="${RequestParameters['examType.id']}">
  	</form>
  	<script>
     	var bar = new ToolBar('taskBar', '排考结果列表', null, true, true);
    	bar.setMessage('<@getMessage/>');
     	printMenu=bar.addMenu("<@msg.message key="action.print"/>", "seatReport()", "print.gif");
     	
     	function seatReport() {
        	var taskIds = getSelectIds("taskId");
        	if (taskIds == null || taskIds == "") {
        		alert("请选择一个或多个教学任务");
        		return;
        	}
        	window.open("examArrange.do?&method=seatReport&examType.id=${RequestParameters['examType.id']}&taskIds=" + taskIds);
     	}
  	</script>
</body> 
<#include "/templates/foot.ftl"/> 