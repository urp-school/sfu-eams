    <@table.table width="100%" sortable="true" id="listTable" headIndex="1">
        <tr bgcolor="#ffffff" onKeyDown="enterQuery(event)">
        <form name="taskListForm" action="" method="post" onsubmit="return false;">
            <input type="hidden" name="primaryKey" value="${(RequestParameters["primaryKey"])?if_exists}"/>
            <input type="hidden" name="departmentId" value="${(RequestParameters["departmentId"])?if_exists}"/>
            <input type="hidden" name="calendarId" value="${RequestParameters['calendarId']?if_exists}"/>
            <input type="hidden" name="taskInId" value="${(RequestParameters["taskInId"])?if_exists}"/>
            <td align="center"><img src="${static_base}/images/action/search.png" align="top" onClick="taskListForm.submit()" alt="<@bean.message key="info.filterInResult"/>"/></td>
            <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
        <#if localName?index_of("en")==-1>
            <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
        <#else>
            <td><input style="width:100%" type="text" name="task.course.engName" maxlength="100" value="${RequestParameters['task.course.engName']?if_exists}"/></td>
        </#if>
            <td><input style="width:100%" type="text" name="task.courseType.name" maxlength="32" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="5" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.course.credits" maxlength="3" value="${RequestParameters['task.course.credits']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.teachClass.depart.name" maxlength="3" value="${RequestParameters["task.teachClass.depart.name"]?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.arrangeInfo.teachDepart.name" maxlength="3" value="${RequestParameters["task.arrangeInfo.teachDepart.name"]?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.taskGroup.name" maxlength="20" value="${RequestParameters['task.taskGroup.name']?if_exists}"/></td>
            <td><select name="task.arrangeInfo.isArrangeComplete" style="width:100%" value="${RequestParameters["task.arrangeInfo.isArrangeComplete"]?default("")}"><option value="">全部</option><option value="0">未排</option><option value="1">已排</option></select></td>
        </form>
        </tr>
        <@table.thead>
            <@table.selectAllTd id="taskId"/>
            <@table.sortTd id="task.seqNo" name="attr.taskNo"/>
            <@table.sortTd id="task.course.code" name="attr.courseNo"/>
            <@table.sortTd width="12%" id="task.course.name" name="attr.courseName"/>
            <@table.sortTd width="12%" id="task.courseType.name" name="entity.courseType"/>
            <@table.sortTd width="8%" id="task.teachClass.name" name="entity.teachClass"/>
            <@table.td width="5%" name="entity.teacher"/>
            <@table.sortTd width="5%" id="task.teachClass.planStdCount" name="teachTask.planStudents"/>
            <@table.sortTd width="5%" id="task.course.credits" name="attr.credit"/>
            <@table.sortTd width="8%" id="task.arrangeInfo.teachDepart.name" text="开课院系"/>
            <@table.sortTd width="8%" id="task.teachClass.depart.name" text="上课院系"/>
            <@table.sortTd width="8%" name="attr.groupName" id="task.taskGroup.name"/>
            <@table.sortTd width="6%" text="排否" id="task.arrangeInfo.isArrangeComplete"/>
        </@>
        <@table.tbody datas=allocations;task>
            <@table.selectTd id="taskId" value=task.id/>
            <td>${task.seqNo?if_exists}</td>
            <td>${task.course.code}</td>
            <td><A href="teachTaskSearch.do?method=info&task.id=${task.id}" title="<@i18nName task.course/>"><@i18nName task.course/></A></td>
            <td><@i18nName task.courseType/></td>
            <td><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</td>
            <td title="<@getTeacherNames task.arrangeInfo.teachers/>" nowrap><span style="display:block;width:45px;overflow:hidden;text-overflow:ellipsis"><@getTeacherNames task.arrangeInfo.teachers/></span></td>
            <td>${task.teachClass.planStdCount}</td>
            <td>${task.course.credits}</td>
            <td title="${task.arrangeInfo.teachDepart.name}" nowrap><span style="display:block;width:45;overflow:hidden;text-overflow:ellipsis">${task.arrangeInfo.teachDepart.name}</span></td>
            <td title="${task.teachClass.depart.name}" nowrap><span style="display:block;width:45px;overflow:hidden;text-overflow:ellipsis">${task.teachClass.depart.name}</span></td>
            <td><#if (task.taskGroup.name)?exists>${(task.taskGroup.name?replace(",", " ")?replace("（", "(")?replace("）", ")"))?html}<br><span style="color:blue">（${(task.taskGroup.stdCountInClass)?default(0)}）</span></#if></td>
            <td<#if (task.arrangeInfo.isArrangeComplete)?exists && task.arrangeInfo.isArrangeComplete> style="color:blue"<#else> style="color:red"</#if>>${task.arrangeInfo.isArrangeComplete?string("已排", "未排")}</td>
        </@>
    </@>
