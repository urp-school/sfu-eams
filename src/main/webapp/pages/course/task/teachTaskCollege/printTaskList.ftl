<#include "/templates/head.ftl"/>
<body  >
 <table id="taskListBar"></table>
 <script>
    var bar= new ToolBar("taskListBar","教学任务列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.print"/>","print()");
    bar.addClose();
 </script>
 <@table.table width="100%" sortable="true" id="task">
 	<@table.thead>
 		<@table.td text="序号"/>
 		<@table.sortTd name="attr.taskNo" id="task.seqNo"/>
 		<@table.sortTd name="attr.courseNo" id="task.course.code"/>
 		<@table.sortTd name="attr.courseName" id="task.course.name"/>
 		<@table.sortTd name="entity.teachClass" id="task.teachClass.name"/>
 		<@table.td name="entity.teacher"/>
 		<@table.sortTd text="教室配置" id="task.requirement.roomConfigType.name"/>
 		<@table.sortTd name="attr.teachLangType" id="task.requirement.teachLangType.name"/>
 		<@table.sortTd text="挂牌" id="task.requirement.isGuaPai"/>
 		<@table.sortTd text="人数" id="task.teachClass.planStdCount"/>
 		<@table.sortTd text="周课时" id="task.arrangeInfo.weekUnits"/>
 		<@table.sortTd text="周数" id="task.arrangeInfo.weeks"/>
 		<@table.sortTd name="attr.credit" id="task.course.credits"/>
 		<@table.sortTd text="学时" id="task.arrangeInfo.overallUnits"/>
 	</@>
 	<@table.tbody datas=tasks;task,task_index>
      	<td align="center">${task_index+1}</td>
      	<td align="center">${task.seqNo}</td>
      	<td align="center">${task.course.code}</td>
      	<td><@i18nName task.course/></td>
      	<td>${task.teachClass.name}</td>
      	<td><@getTeacherNames task.arrangeInfo.teachers/></td>
      	<td><@i18nName task.requirement.roomConfigType/></td>
      	<td><@i18nName (task.requirement.teachLangType)?if_exists/></td>
      	<td>${task.requirement.isGuaPai?string("是","否")}</td>
      	<td>${task.teachClass.planStdCount}</td>
      	<td>${task.arrangeInfo.weekUnits}</td>
      	<td>${task.arrangeInfo.weeks}</td>
      	<td>${task.course.credits}</td>      
      	<td>${task.arrangeInfo.overallUnits}</td>
 	</@>
 </@>
</body>
<#include "/templates/foot.ftl"/>