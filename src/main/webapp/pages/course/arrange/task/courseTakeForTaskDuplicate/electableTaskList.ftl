<@table.table id="electtable" width="100%" sortable="true" headIndex="1">
    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, pageGoWithSize)">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="pageGoWithSize()" alt="<@bean.message key="info.filterInResult" />"/>
      </td>
      <form name="taskListForm" action="" method="post" onsubmit="return false;">
        <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
        <input type="hidden" name="type" value="course"/>
        <input type="hidden" name="id" value=""/>

        <input type="hidden" name="task.course.extInfo.courseType.isPractice" value="1"/>
        <input type="hidden" name="practiceCourse" value="1"/>
      <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="10" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>
      <#assign electCountCompare=''/>
      <#if RequestParameters['electInfo.electCountCompare']?exists>
      	<#assign electCountCompare=RequestParameters['electInfo.electCountCompare']/>
      </#if>
      <td>
         <select name="electInfo.electCountCompare" style="width:100%">
            <option value="" <#if electCountCompare='0'> selected</#if>>全部</option>
            <option value="1" <#if electCountCompare='1'> selected</#if>>${"实选>上限"?html}</option>
            <option value="0" <#if electCountCompare='0'> selected</#if>>实选=上限</option>
            <option value="-1" <#if electCountCompare='-1'> selected</#if>>${"实选<上限"?html}</option>
         </select>
      </td>
      <td><input style="width:100%" type="text" name="task.taskGroup.name" maxlength="20" value="${RequestParameters['task.taskGroup.name']?if_exists}"/></td>
      <td></td>
      </form>
    </tr>
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd name="attr.index" width="6%" id="task.seqNo"/>
      <@table.sortTd width="6%" name="attr.id" id="task.course.code"/>
      <@table.sortTd width="18%" name="attr.courseName" id="task.course.name"/>
      <@table.td width="6%" name="entity.teacher"/>
      <@table.sortTd width="16%" name="entity.teachClass" id="task.teachClass.name"/>
      <@table.sortTd width="12%" name="entity.courseType" id="task.courseType.name"/>
      <@table.sortTd width="8%" text="计划" id="task.teachClass.planStdCount"/>
      <@table.sortTd width="8%" text="实际/上限" id="task.teachClass.stdCount"/>
      <@table.sortTd name="attr.groupName" width="10%" id="task.taskGroup.name"/>
      <@table.td width="8%" text="调整次数"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" value=task.id/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</a></td>
      <td><A href="#" onclick="javascript:quickSearch(document.taskListForm, 'courseTakeForTaskDuplicate.do?method=taskList', 'task.course.code', '${(task.course.code)?default("")}', null, 'pageGoWithSize')">${task.course.code}</A></td>
      <td><A href="teachTaskCollege.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course?if_exists/></a></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
      <td><@i18nName task.courseType/></td>
      <td>${task.teachClass.planStdCount}</td>
      <td><A href="teachTaskCollege.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}/${task.electInfo.maxStdCount}</a></td>
      <td title="${(task.taskGroup.name)?if_exists?html}" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis;"><A href="#" onclick="javascript:quickSearch(document.taskListForm, 'courseTakeForTaskDuplicate.do?method=taskList', 'task.taskGroup.name', '${(task.taskGroup.name)?default("")}', null, 'pageGoWithSize')">${(task.taskGroup.name)?if_exists?html}</A></span></td>
      <td>${alterationCountMap[task.id?string]?default(0)}</td>
     </@>
    </@>