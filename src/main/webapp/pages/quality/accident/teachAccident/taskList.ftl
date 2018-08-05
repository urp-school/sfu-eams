<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="myBar" width="100%"></table> 
  <@table.table width="100%" sortable="true" id="listTable" headIndex="1">
   <form name="taskListForm"  method="post" action="teachAccident.do?method=taskList" onsubmit="return false;">
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
    <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
    <@searchParams/>
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
      <td align="center" width="3%" >
        <img src="${static_base}/images/action/search.png"  align="top" onClick="search()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      <#if localName?index_of("en")==-1>
      <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
      <#else>
      <td><input style="width:100%" type="text" name="task.course.engName" maxlength="100" value="${RequestParameters['task.course.engName']?if_exists}"/></td>
      </#if>
      <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="7" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>      
      <td><input style="width:100%" type="text" name="task.course.credits" maxlength="3" value="${RequestParameters['task.course.credits']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.arrangeInfo.weekUnits" maxlength="3" value="${RequestParameters['task.arrangeInfo.weekUnits']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.arrangeInfo.weeks" maxlength="3" value="${RequestParameters['task.arrangeInfo.weeks']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.arrangeInfo.overallUnits" maxlength="3" value="${RequestParameters['task.arrangeInfo.overallUnits']?if_exists}"/></td>    
    </tr>
  	</form>
  	<@table.thead>
  	  <@table.td text=""/>
      <@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.sortTd id="task.course.name" width="20%" name="attr.courseName"/>
      <@table.sortTd id="task.teachClass.name" width="20%" name="entity.teachClass"/>
      <@table.td width="10%" name="entity.teacher"/>
      <@table.sortTd width="4%" text="计划人数" id="task.teachClass.planStdCount"/>
      <@table.sortTd width="4%" id="task.course.credits" name="attr.credit"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.weekUnits" text="周时"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.weeks" text="周数"/>
      <@table.sortTd width="4%" id="task.arrangeInfo.overallUnits" name="attr.creditHour"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" type="radio" value=task.id/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td>${task.teachClass.name?html}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.planStdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td>${task.arrangeInfo.overallUnits}</td>
    </@>
  </@>
  <script>function enterQuery(event) {if (portableEvent(event).keyCode == 13)search();}</script>
  <script>
    var bar = new ToolBar("myBar","教学任务列表",null,true,true);
    bar.addItem("选择任务,添加事故",'addTeachAccident()');
    bar.addBack("<@msg.message key="action.back"/>");
    function addTeachAccident(){
       var form =document.taskListForm;
       var taskId =getSelectId("taskId");
       if(taskId ==""){alert("请选择教学任务");return;}
       form.action='teachAccident.do?method=edit&teachAccident.task.id='+taskId;
       form.submit();
    }
    function search(){
      document.taskListForm.submit();
    }
  </script>
</body>
 <#include "/templates/foot.ftl"/>