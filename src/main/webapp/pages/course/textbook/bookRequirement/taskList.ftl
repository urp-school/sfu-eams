<#include "/templates/head.ftl"/>
<body>
<table id="bookRequirementBar"></table>
 <@table.table width="100%" id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd width="8%" id="task.seqNo" name="attr.taskNo"/>
      <@table.sortTd width="8%" id="task.course.code" text="课程代码"/>
      <@table.sortTd width="20%" id="task.course.name" name="attr.courseName"/>
      <@table.sortTd width="20%" id="task.teachClass.name" name="entity.teachClass"/>
      <@table.td width="10%" name="entity.teacher"/>
      <@table.sortTd width="4%" name="attr.stdNum" id="task.teachClass.stdCount"/>
      <@table.sortTd width="4%" name="attr.credit" id="task.course.credits"/>
      <@table.td width="20%" text="教师推荐教材"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" value="${task.id}"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td>${task.course.code}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td><#if task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${task.teachClass.name?html}</#if></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.planStdCount}</A></td>
      <td>${task.course.credits}</td>
      <td><@getBeanListNames task.requirement.textbooks?if_exists/></td>
    </@>
   </@>
   	<@htm.actionForm name="taskForm" action="bookRequirement.do" entity="task">
		<input name="task.calendar.id" value="${RequestParameters['task.calendar.id']}" type="hidden"/>
		<input name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}" type="hidden"/>
		<input name="task.id" value="" type="hidden"/>
		<input name="searchWhat" value="taskList" type="hidden"/>
	</@>
	<script>
	 var bar=new ToolBar('bookRequirementBar','<@msg.message key="entity.teachTask"/>',null,true,true);
	 bar.setMessage('<@getMessage/>');
	 bar.addItem("指定教材","addRequirement()");
	 bar.addItem("<@msg.message key="action.batchSet"/>","batchSet()");
	 bar.addItem("<@msg.message key="action.export"/>","exportStdList()");
     var form = document.taskForm;
     action="bookRequirement.do";
     function exportStdList(){
	        addInput(form,"keys",'seqNo,course.code,course.name,courseType.name,teachClass.name,arrangeInfo.teachers,requirement.roomConfigType.name,requirement.teachLangType.name,requirement.isGuaPai,teachClass.planStdCount,teachClass.stdCount,arrangeInfo.weeks,arrangeInfo.weekUnits,,arrangeInfo.courseUnits,arrangeInfo.overallUnits,credit');
	        addInput(form,'titles','课程序号,课程代码,课程名称,课程类别,面向班级,授课教师,教室设备配置,<@msg.message key="attr.teachLangType"/>,是否挂牌,计划人数,实际人数,周数,周课时,节次,总课时,学分');
         exportList();
     }
	 function batchSet(){
	    form.action=action+"?method=chooseTextbook";
	    addParamsInput(form,queryStr);
	    submitId(form,"taskId",true);
	 }
	 function addRequirement(){
	    var taskId=getSelectIds("taskId");
	    if (taskId == "" || isMultiId(taskId)) {
	    	alert("请选择一项教学任务！");
	    	return;
	    }
	    form['task.id'].value=taskId;
	    addParamsInput(form,queryStr);
	    form.action=action+"?method=edit&forward=taskList";
	    form.submit();
     }
    </script>
<#include "/templates/foot.ftl"/>