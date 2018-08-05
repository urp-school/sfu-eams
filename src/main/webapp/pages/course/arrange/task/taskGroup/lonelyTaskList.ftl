<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
    <table id="taskListForAddBar"></table>

    <table width="100%" border="0" class="listTable">
    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
    <form name="taskListForm" action="" method="post" onsubmit="return false;">
    <#if RequestParameters['taskGroup.id']?default("")!="">
    <input type="hidden" name="taskGroup.id" value="${RequestParameters['taskGroup.id']}"/>
    </#if>
    <input type="hidden" name="taskGroup.name" value="${RequestParameters['taskGroup.name']?if_exists}"/>
    <input type="hidden" name="taskGroup.priority" value="${RequestParameters['taskGroup.priority']?if_exists}"/>
    <input type="hidden" name="taskGroup.remark" value="${RequestParameters['taskGroup.remark']?if_exists}"/>
    <input type="hidden" name="taskGroup.isSameTime" value="${RequestParameters['taskGroup.isSameTime']?if_exists}"/>
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
    <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
      <td align="center">
        <img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>               
      <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.arrangeInfo.teachDepart.name" maxlength="20" value="${RequestParameters['task.arrangeInfo.teachDepart.name']?if_exists}"/></td>
      <td></td>
    </tr>
    <tr align="center" class="darkColumn">
      <td align="center" class="select">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      </td>
      <td width="7%"><@bean.message key="attr.taskNo"/></td>
      <td width="7%"><@bean.message key="attr.courseNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="15%">教学班</td>
      <td width="10%"><@bean.message key="entity.courseType"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="10%">开课院系</td>
      <td width="10%">建议时间</td>
    </tr>
    <#list taskList?if_exists as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"
      onmouseout="swapOutTR(this)" onclick="onRowChange(event)">
      <td class="select">
        <input type="checkBox" name="taskId" value="${task.id}"/>
      </td>
      <td><#if task.arrangeInfo.isArrangeComplete ==false>${task.seqNo?if_exists}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}">${task.seqNo?if_exists}</a></#if></td>
      <td>${task.course.code}</td>
      <td><@i18nName task.course/></td>
      <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
      <td><@i18nName task.courseType/></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><@i18nName task.arrangeInfo.teachDepart/></td>
      <td><#if (task.arrangeInfo.suggest.time)?exists>${task.arrangeInfo.suggest.time.abbreviate(Session["org.apache.struts.action.LOCALE"],Request["org.apache.struts.action.MESSAGE"])}</#if></td>
    </tr>
	</#list>
	</form>
	<#include "/templates/newPageBar.ftl"/>
	</table>
	<script>
	function query(pageNo,pageSize){
	    var form = document.taskListForm;
	    form.action="taskGroup.do?method=lonelyTaskList";
	    goToPage(form,pageNo,pageSize);
	}
    function add(quickAdd){
       	var taskIds = getSelectIds("taskId");
       	if (taskIds == null || taskIds == "") {
       		alert("<@bean.message key="common.selectPlease"/>");
       		return;
       	}
       	var form = document.taskListForm;
       	if (quickAdd == 1) {
          	form.action = "taskGroup.do?method=addTasks&quickAdd=1&taskIds=" + taskIds;
       	} else {
          	form.action = "taskGroup.do?method=addTaskOptions&taskIds=" + taskIds;  
       	}
       	form.submit();
    }
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("taskId")));
    }
    function pageGoWithSize(pageNo,pageSize){
        query(pageNo,pageSize);
    }
   var bar = new ToolBar('taskListForAddBar','<@bean.message key="info.group.addTask" arg0="${taskGroup.name}"/> <#if taskGroup.id?exists&&taskGroup.id!=0><#else>(2)</#if>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("快速添加","add(1)",'new.gif',"合并每个任务的建议.同时要求同一时间的排课组,将共享班级和合并教师");
   bar.addItem("<@bean.message key="action.add"/>","add(0)",'new.gif');
   bar.addBack("<@bean.message key="action.back"/>");
  </script>
</body> 
<#include "/templates/foot.ftl"/> 