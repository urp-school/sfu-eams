
 <table width="100%" border="0" class="listTable">
    <tr align="center" class="darkColumn">
      <td class="select">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);"/>
      </td>
      <td width="7%"><@bean.message key="attr.taskNo"/></td>
      <td width="7%"><@bean.message key="attr.courseNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="15%"><@bean.message key="entity.courseType"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="10%"><@bean.message key="attr.roomConfigOfTask"/></td>
      <td width="7%"><@bean.message key="adminClass.planStdCount"/></td>
      <td width="7%">实际人数</td>
      <td width="7%"><@bean.message key="attr.weekHour"/></td>
    </tr>
    <#list taskGroup.taskList?sort_by(["course","id"]) as task>
   	  <#if task_index%2==1><#assign class="grayStyle"></#if>
	  <#if task_index%2==0><#assign class="brightStyle"></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"
      onmouseout="swapOutTR(this)" onclick="onRowChange(event)">
      <td  class="select">
        <input type="checkBox" name="taskId" value="${task.id}"/>
      </td>
      <td><#if task.arrangeInfo.isArrangeComplete ==false>${task.seqNo?if_exists}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}">${task.seqNo?if_exists}</a></#if></td>
      <td>${task.course.code}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>
      <td><@i18nName task.courseType/></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><@i18nName task.requirement.roomConfigType/></td>
      <td>${task.teachClass.planStdCount}</td>
      <td>${task.teachClass.stdCount}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
    </tr>
	</#list>
	</table>