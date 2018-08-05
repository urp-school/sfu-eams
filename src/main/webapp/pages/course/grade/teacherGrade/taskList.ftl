 <@table.table width="100%">
	    <@table.thead>
	      <@table.selectAllTd id="taskId"/>
	      <td ><@bean.message key="attr.taskNo" /></td>
	      <td><@bean.message key="attr.courseName"/></td>
	      <td><@msg.message key="entity.courseType"/></td>
	      <td><@bean.message key="attr.credit" /></td>
	      <td><@bean.message key="attr.weeks" /></td>
	      <td><@bean.message key="attr.weekHour"/></td>
	      <td><@msg.message key="task.studentNum"/></td>
	      <td><@bean.message key="attr.GP"/></td>
	      <td><@msg.message key="info.operation"/></td>
	    </@>
	    <@table.tbody datas=tasks?sort_by("seqNo");task>
	   	  <@table.selectTd id="taskId" value="${task.id}" type="checkbox"/>
	      <td><A href="courseTableForTeacher.do?method=taskTable&task.id=${task.id}"  title="<@bean.message key="info.courseTable.lookFormTaskTip"/>"><U>${task.seqNo?if_exists}</U></a></td>
	      <td><A href="teachTaskSearch.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><U><@i18nName task.course/></U></a></td>
	      <td><@i18nName task.courseType/></td>
	      <td>${task.course.credits}</td>
	      <td>${task.arrangeInfo.weeks}</td>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td><A href="teacherTask.do?method=printDutyStdList&teachTaskIds=${task.id}" target="_blank"><U>${task.teachClass.stdCount}</U></A></td>
	      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes" /> <#else> <@bean.message key="common.no" /> </#if></td>
	      <td>
	          <#if (task.gradeState.isConfirmed(GAGrade))?default(false)>
	             <A href="#" onclick="printTeachClassGrade(${task.id})"><U><@msg.message key="action.print"/></U></A>
	          <#else>
	            <#if canInput>
	              <A href="#" onclick="inputGrade(${task.id})"><U><@msg.message key="grade.input"/></U></A>
	            <#else>
	                未开放录入
	            </#if>
	          </#if>
	      </td>
	    </@>
    </@>