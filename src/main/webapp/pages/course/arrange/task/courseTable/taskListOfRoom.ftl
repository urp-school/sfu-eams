 <table width="100%" border="0" class="listTable">
    <tr align="center" class="darkColumn">
      <td width="1%"></td>
      <td width="7%"><@msg.message key="attr.courseNo"/></td>
      <td width="7%"><@bean.message key="attr.taskNo"/></td>
      <td width="6%"><@bean.message key="entity.teacher"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="10%"><@msg.message key="entity.courseType"/></td>
      <td width="20%"><@bean.message key="entity.teachClass"/></td>
      <td width="5%"><@bean.message key="attr.weekHour"/></td>
      <td width="5%"><@bean.message key="attr.credit"/></td>
      <td width="5%"><@msg.message key="attr.personCount"/></td>
      <td width="5%"><@msg.message key="attr.teachLangType"/></td>
      <td width="5%"><@msg.message key="attr.startWeek"/></td>
    </tr>
    <#list taskList?sort_by("courseType","name") as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle"/></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle"/></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
      <td>${task_index+1}</td> 
      <td>${task.course.code}</td>
      <td><A href="?method=taskTable&task.id=${task.id}" title="<@bean.message key="info.courseTable.lookFormTaskTip"/>">${task.seqNo?if_exists}</a></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>
       <A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>">
       <@i18nName task.course/></a>
      </td>
      <td><@i18nName task.courseType/></td>
      <td>${task.teachClass.name}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.course.credits}</td>
      <td><A href="teachTask.do?method=printStdListForGrade&teachTaskIds=${task.id}"><U>${task.teachClass.stdCount}</U></a></td>
      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>
      <td>${task.arrangeInfo.weekStart}</td>
    </tr>
	</#list>
	</table>