<#include "/templates/head.ftl"/>
<script src='dwr/interface/teacherDAO.js'></script>
<body  onload="DWRUtil.useLoadingMessage();">
 <table id="batchEditBar"></table>
 <script>
    var bar= new ToolBar("batchEditBar","指定或修改主考老师",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.print"/>","print()");
    bar.addItem("<@bean.message key="action.save"/>","batchUpdate(document.taskListForm)");
    bar.addClose();
 </script>
 <@table.table width="100%" id="listTable">
 <form name="taskListForm" method="post" action="" onsubmit="return false;">
    <@table.thead>
      <@table.selectAllTd id="examActivityId"/>
      <td></td>
      <@table.td width="8%" name="attr.taskNo"/>
      <@table.td width="15%" name="attr.courseName"/>
      <@table.td width="20%" name="entity.teachClass"/>
      <@table.td width="20%" text="考试时间/地点"/>
      <@table.td width="8%" text="授课老师"/>
      <@table.td width="15%" text="开课院系"/>
      <@table.td width="15%" text="主考老师"/>
      <@table.td width="10%" text="自定主考"/>
    </@>
    <@table.tbody datas=examActivities;activity,activity_index>
      <@table.selectTd id="examActivityId" value=activity.id/>
      <td  align="center">${activity_index+1}</td>
      <td  align="center">${activity.task.seqNo}</td>
      <td><@i18nName activity.task.course/></td>
      <td>${activity.task.teachClass.name}</td>
      <td>${activity.date?string("yyyy-MM-dd")} ${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],activity.examType.id,":day :time")} <@i18nName activity.room?if_exists/></td>
      <td><@getTeacherNames activity.task.arrangeInfo.teachers/></td>
      <td><@i18nName activity.task.arrangeInfo.teachDepart/></td>
      <td><select name="teacherId${activity.id}" id="teacher${activity.id}" 
          onmouseover="displayTeacherList('teacher${activity.id}',${activity.task.arrangeInfo.teachDepart.id},false)"
          style="width:100%" >
            <option value="${(activity.examMonitor.examiner.id)?if_exists}"><@i18nName activity.examMonitor.examiner?if_exists/></option>
          </select>
      </td>
      <td><input name="examinerName${activity.id}" style="width:50px" value="${activity.examMonitor.examinerName?if_exists}"></td>
    </@>
    <tr align="center" class="darkColumn">
       <td colspan="15">本次修改安排数量：${examActivities?size}。可以选择左侧的复选框，进行选择性的保存</td>
    </tr>
	</form>
	</@>
	<script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js"></script>
    <script>
    function batchUpdate(form){
        if(getCheckBoxValue(form.examActivityId)=="") {alert("请选择一个或多个进行保存设置");return;}
        form.action="examiner.do?method=saveExaminers";
        form.submit();
    }
    var examBoxes=document.getElementsByName("examActivityId");
    for (var i=0; i < examBoxes.length; i++) {
       examBoxes[i].checked = true;
    }
</script>
</body>
<#include "/templates/foot.ftl"/>