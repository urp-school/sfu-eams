<#include "/templates/head.ftl"/>
<body>
<table width="100%" class="frameTable">
<tr>
 <td colspan="2">
    <table id="myBar" width="100%"></table>
 </td>
</tr>
<tr>
  <td valign="top" class="frameTable_view" width="160px">
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	    <form name="taskSearchForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}" />
        <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
        <input type="hidden" name="examGroup.id" value="${examGroup.id?if_exists}"/>
	    <input type="hidden" name="examGroup.examType.id" value="${examGroup.examType.id}"/>
	    <input type="hidden" name="examType.id" value="${examGroup.examType.id}"/>
	    <input type="hidden" name="examGroup.name" value="<@i18nName examGroup?if_exists/>"/>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="${RequestParameters['task.seqNo']?if_exists}" style="width:60px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="${RequestParameters['task.course.code']?if_exists}" maxlength="32" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="${RequestParameters['task.course.name']?if_exists}" maxlength="50" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType"/>:</td>
	     <td><input type="text" name="task.courseType.name" value="${RequestParameters['task.courseType.name']?if_exists}" maxlength="50" style="width:100px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="${RequestParameters['task.teachClass.enrollTurn']?if_exists}" maxlength="7" style="width:60px"></td>
	    </tr>
	    <tr>
	    	<td class="infoTitle"><@msg.message key="entity.studentType"/>:</td>
	    	<td><@htm.i18nSelect datas=studentTypes?if_exists selected=RequestParameters["task.teachClass.stdType.id"]?default("") name="task.teachClass.stdType.id" style="width:100px"><option value="">...</option></@></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">结束周≤:</td>
	     <td><input type="text" name="arrangeInfo.endWeek" value="${RequestParameters['arrangeInfo.endWeek']?if_exists}" maxlength="3" style="width:60px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">星期:</td>
	     <td>
		     <select name="time.week" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list weeks as week>
		     	<option value="${week.id}" <#if week.id?string=RequestParameters['time.week']?default("")>selected</#if>><@i18nName week/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle">小节:</td>
	     <td>
		     <select name="time.startUnit" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list 1..14 as unit>
		     	<option value=${unit} <#if unit?string=RequestParameters['time.startUnit']?default("")>selected</#if>>${unit}</option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
	 </td>
	 <td valign="top">	 
    <@table.table width="100%" id="listTable" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="taskId"/>
            <@table.sortTd width="8%" id="task.seqNo" name="attr.taskNo"/>
            <@table.sortTd width="10%" id="task.course.code" name="attr.courseNo"/>
            <@table.sortTd width="20%" id="task.course.name" name="attr.courseName"/>
            <@table.sortTd width="20%" id="task.teachClass.name" name="entity.teachClass"/>
            <@table.td width="10%" name="entity.teacher"/>
            <@table.sortTd id="task.teachClass.stdCount" name="attr.stdNum"/>
            <@table.sortTd name="attr.credit" id="task.course.credits"/>
            <@table.sortTd text="周时" id="task.arrangeInfo.weekUnits"/>
            <@table.sortTd text="周数" id="task.arrangeInfo.weeks"/>
            <@table.sortTd name="attr.creditHour" id="task.arrangeInfo.overallUnits"/>
        </@>
        <@table.tbody datas=tasks;task>
            <@table.selectTd id="taskId" value=task.id/>
            <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
            <td>${task.course.code}</td>
            <td title="<@i18nName task.course/>" nowrap><span style="display:block;width:130px;overflow:hidden;text-overflow:ellipsis"><A href="teachTaskCollege.do?method=info&task.id=${task.id}" title="<@i18nName task.course/>"><@i18nName task.course/></A></span></td>
            <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;">${task.teachClass.name?html}</span></td>
            <td title="<@getTeacherNames task.arrangeInfo.teachers/>" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;"><@getTeacherNames task.arrangeInfo.teachers/></span></td>
            <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}</A></td>
            <td>${task.course.credits}</td>
            <td>${task.arrangeInfo.weekUnits}</td>
            <td>${task.arrangeInfo.weeks}</td>
            <td>${task.arrangeInfo.overallUnits}</td>
            <input type="hidden" name="${task.id}" id="${task.id}" value="${task.isConfirm?string(1, 0)}"/>
        </@>
	</@>
  </td>
 </tr>
</table>
  <script>
     var bar1 = new ToolBar("myBar","请选择教学任务",null,true,true);
     bar1.setMessage('<@getMessage/>');
     bar1.addItem("选中添加任务","addTask()");
     bar1.addBack();
     
     var params = "<#list RequestParameters?keys as key><#if key != "params">&${key}=${RequestParameters[key]}</#if></#list>";
     
	var form =document.taskSearchForm;
	function addTask(){
	   var taskIds = getSelectIds("taskId");
	   if(""==taskIds){alert("请选择教学任务进行排考");return;}
	   addInput(form,"taskIds",taskIds);
	   form.action="examGroup.do?method=addTask";
	   addInput(form, "params", params, "hidden");
	   form.submit();
	}
	function search(pageNo,pageSize,orderBy){
	   form.action="examGroup.do?method=taskList";
	   goToPage(form,pageNo,pageSize,orderBy);
	}
	function pageGoWithSize(pageNo,pageSize){
	   search(pageNo,pageSize,"${RequestParameters['orderBy']?default('null')}");
	}
    <#assign sortTableId="listTable"/>
    <#include "/templates/initSortTable.ftl"/>
    orderBy= function (byWhat){
       search(null,null,byWhat);
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 