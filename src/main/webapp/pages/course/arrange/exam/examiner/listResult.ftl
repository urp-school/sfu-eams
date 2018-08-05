 <@table.table width="100%" sortable="true" id="listTable">
    <@table.thead>
       <@table.selectAllTd id="examActivityId"/>
      <td width="8%" id="activity.task.seqNo" class="tableHeaderSort"><@msg.message key="attr.taskNo"/></td>
      <td width="15%" id="activity.task.course.name" class="tableHeaderSort"><@msg.message key="attr.courseName"/></td>
      <td width="20%" id="activity.task.teachClass.name" class="tableHeaderSort"><@msg.message key="entity.teachClass"/></td>
      <td width="12%" id="activity.time.year,activity.time.validWeeksNum desc,activity.time.weekId,activity.time.startTime" desc="activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc,activity.time.startTime desc" class="tableHeaderSort">考试安排</td>
      <td width="8%" id="activity.room.name" class="tableHeaderSort">地点</td>
      <td width="10%" id="activity.department" class="tableHeaderSort">主考院系</td>
      <td width="8%">主考</td>
      <td width="10%" id="activity.examMonitor.depart.name" class="tableHeaderSort">监考院系</td>
      <td width="8%">监考</td>
    </@>
    <@table.tbody datas=examActivities;activity>
      <@table.selectTd id="examActivityId"  type="checkBox" value="${activity.id}"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${activity.task.id}" title="查看课程安排">${activity.task.seqNo?if_exists}</A></td>
      <td><A href="teachTask.do?method=info&task.id=${activity.task.id}" title="<@msg.message key="info.task.info"/>"><@i18nName activity.task.course/></A></td>      
      <td>${activity.task.teachClass.name?html}</td>
      <td>${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],":date :time")}</td>
      <td><@i18nName activity.room?if_exists/></td>
      <td><@i18nName activity.department?if_exists/></td>
      <td><@i18nName activity.examMonitor.examiner?if_exists/> ${activity.examMonitor.examinerName?if_exists}</td>
      <td><@i18nName activity.examMonitor.depart?if_exists/></td>
      <td><@i18nName activity.examMonitor.invigilator?if_exists/> ${activity.examMonitor.invigilatorName?if_exists}</td>
   </@>
</@>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="examActivityIds" value=""/>
        <input type="hidden" name="extraCount" value=""/>
        <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('')}"/>
        <input type="hidden" name="calendar.id" value="${RequestParameters["calendar.id"]}"/>
        <input type="hidden" name="examType.id" value="${RequestParameters["examType.id"]}"/>
        <input type="hidden" name="keys" value="task.seqNo,task.course.code,task.course.name,task.arrangeInfo.teacherCodes,task.arrangeInfo.teacherNames,task.teachClass.name,task.teachClass.stdCount,examTakeCount,time,date,room.name,task.arrangeInfo.teachDepart.name,examMonitor.examinerNames,examMonitor.depart.name,examMonitor.invigilatorNames,task.requirement.isGuaPai"/>
        <input type="hidden" name="titles" value="<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,教师工号,教师姓名,教学班,上课人数,考试人数,考试安排,考试时间,考试地点,主考院系,主考老师,监考院系,监考老师,是否挂牌"/>
    </form>