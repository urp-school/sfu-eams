  	<@table.table id="listTable" sortable="true" width="100%">
    	<@table.thead>
      		<@table.selectAllTd id="taskId"/>
      		<@table.sortTd width="8%" id="task.seqNo" name="attr.taskNo"/>
      		<@table.sortTd width="12%" id="task.course.name" name="attr.courseName"/>
      		<@table.sortTd width="20%" id="task.teachClass.name" name="entity.teachClass"/>
      		<@table.sortTd width="10%" id="task.teachClass.stdCount" text="人数(考试/班级)"/>
      		<@table.td width="20%" text="考试时间"/>
      		<@table.td width="10%" text="考试地点"/>
      		<@table.td width="10%" text="主考老师"/>
    	</@>
    	<@table.tbody datas=tasks;task>
      		<@table.selectTd  id="taskId" value=task.id/>
      		<td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      		<td title="<@i18nName task.course/>" nowrap><span style="display:block;width:80px;overflow:hidden;text-overflow:ellipsis"><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></span></td>
      		<td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis">${task.teachClass.name?html}</span></td>
      		<td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${(examTakeCountOfTasks["${task.id}"])?default("0")}/${task.teachClass.stdCount}</A></td>
      		<#assign digestExamValue>${task.arrangeInfo.digestExam(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],"第:weeks周 :day :time")}</#assign>
      		<td title="${digestExamValue}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis">${digestExamValue}</span></td>
      		<td title="<@getBeanListNames task.arrangeInfo.getExamRooms(examType)?sort_by("name")/>" nowrap><span style="display:block;width:75px;overflow:hidden;text-overflow:ellipsis"><@getBeanListNames task.arrangeInfo.getExamRooms(examType)?sort_by("name")/></span></td>
      		<td title="<@getBeanListNames task.arrangeInfo.getExamTeachers(examType)?sort_by("name")/>" nowrap><span style="display:block;width:75px;overflow:hidden;text-overflow:ellipsis"><@getBeanListNames task.arrangeInfo.getExamTeachers(examType)?sort_by("name")/></span></td>
    	</@>
  	</@>
