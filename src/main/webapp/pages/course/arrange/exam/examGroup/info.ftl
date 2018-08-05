<#include "/templates/head.ftl"/>
<body >
  <table id="taskBar"></table>
  <@table.table  id="listTable" sortable="true" width="100%">
    <@table.thead>
     <@table.selectAllTd id="taskId"/>
      <td width="8%" id="task.seqNo" class="tableHeaderSort"><@bean.message key="attr.taskNo"/></td>
      <td width="10%" id="task.course.code" class="tableHeaderSort"><@bean.message key="attr.courseNo"/></td>
      <td width="18%" id="task.course.name" class="tableHeaderSort"><@bean.message key="attr.courseName"/></td>
      <td width="12%" id="task.arrangeInfo.teachDepart.name" class="tableHeaderSort"><@bean.message key="attr.teachDepart"/></td>
      <td width="20%" id="task.teachClass.name" class="tableHeaderSort"><@bean.message key="entity.teachClass"/></td>
      <td width="10%" ><@bean.message key="entity.teacher"/></td>
      <td width="4%" id="task.teachClass.stdCount" class="tableHeaderSort"><@bean.message key="attr.stdNum"/></td>
      <td width="4%"><@bean.message key="attr.credit"/></td>
      <td width="4%">周时</td>
      <td width="4%">周数</td>
      <td width="4%"><@bean.message key="attr.creditHour"/></td>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" type="checkbox"  value="${task.id}"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td>${task.course.code}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>
      <td>${task.arrangeInfo.teachDepart.name}</td>
      <td>${task.teachClass.name?html}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td><input type="hidden" name="${task.id}" id="${task.id}" value="${task.isConfirm?string(1, 0)}"/>
       ${task.arrangeInfo.overallUnits}
      </td>
    </@>
	</@>
	 <form name="taskSearchForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}" />
        <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
        <input type="hidden" name="examGroup.id" value="${examGroup.id?if_exists}"/>
	    <input type="hidden" name="examGroup.examType.id" value="${examGroup.examType.id}"/>
	    <input type="hidden" name="examType.id" value="${examGroup.examType.id}"/>
	    <input type="hidden" name="examGroup.name" value="<@i18nName examGroup/>"/>
	 </form>
  <script>
     var bar=new ToolBar('taskBar','&nbsp;<@i18nName examGroup/>教学任务列表',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("添加任务","taskList()","new.gif");
     bar.addItem("删除任务","removeTask()");
     bar.addItem("返回列表","backToGroup()","backward.gif");
     
     var params = "<#list RequestParameters?keys as key><#if key != "params">&${key}=${RequestParameters[key]}</#if></#list>";
     
	function backToGroup(){
	   var form=document.taskSearchForm;
	   form.action="examGroup.do?method=groupList";
	   form['examGroup.name'].value="";
	   form['examGroup.id'].value="";
	   form.submit();
	}
	function taskList(){
	   var form=document.taskSearchForm;
	   form.action="examGroup.do?method=taskList";
	   addInput(form, "params", params, "hidden");
	   form.submit();
	}
	function removeTask(){
	  var taskIds = getSelectIds("taskId");
      if(""==taskIds){
        alert("请选择教学任务进行删除");return;
      }
      if(confirm("确认删除?")){
        var form =document.taskSearchForm;
        form.action="examGroup.do?method=removeTask&taskIds="+taskIds;
        addInput(form, "params", params, "hidden");
        form.submit();
      }
	}
  </script>
</body>
<#include "/templates/foot.ftl"/>