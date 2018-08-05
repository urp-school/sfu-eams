<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/course/task/task.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<script language="JavaScript" type="text/JavaScript" src="pages/course/arrange/arrange.js"></script> 
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.1,transition=1) blendtrans(duration=.2);
    }
-->
</style>
   <#assign taskList =tasks/>
   <#if RequestParameters['task.arrangeInfo.isArrangeComplete']?default("1")=="0">
   <#include "/pages/course/arrange/taskArrangeSuggestPrompt.ftl"/> 
   <#else>
   <#include "/pages/course/arrange/taskArrangeResult.ftl"/> 
   </#if>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 <script>initToolTips()</script>
<table id="taskListBar"></table>
 <div id="processDIV" style="display:block">页面加载中...</div>
 <div id="contentDIV" style="display:none">
	<@table.table id="teachTask" width="100%" sortable="true" headIndex="1">
	  	<form name="taskListForm" action="" method="post" onsubmit="return false;">
	  	<#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
	  	<input type="hidden" name="type" value="course"/>
	  	<input type="hidden" name="id" value=""/>
	  	
	  	<input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']?if_exists}"/>
	  	<input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="${RequestParameters['task.arrangeInfo.isArrangeComplete']?if_exists}"/>
	    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
	      	<td align="center" >
	        	<img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult" />"/>
	      	</td>
	      	<td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.arrangeInfo.teachDepart.name" maxlength="20" value="${RequestParameters['task.arrangeInfo.teachDepart.name']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="7" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="task.taskGroup.name" maxlength="20" value="${RequestParameters['task.taskGroup.name']?if_exists}"/></td>
	    </tr>
	    </form>
		<@table.thead>
			<@table.selectAllTd id="taskId"/>
			<@table.sortTd name="attr.taskNo" width="8%" id="task.seqNo"/>
			<@table.sortTd name="attr.courseNo" width="8%" id="task.course.code"/>
			<@table.sortTd name="attr.courseName" width="10%" id="task.course.name"/>
            <@table.sortTd name="entity.teachClass" width="20%" id="task.teachClass.name"/>
            <@table.sortTd text="开课系统" width="10%" id="task.arrangeInfo.teachDepart.name"/>
			<@table.td name="entity.teacher" width="10%"/>
			<@table.sortTd name="entity.courseType" width="10%" id="task.courseType.name"/>
			<@table.sortTd text="计划人数" width="5%" id="task.teachClass.planStdCount"/>
			<@table.sortTd name="attr.groupName" width="10%" id="task.taskGroup.name"/>
		</@>
		<#list tasks as task>
		  	<#if task_index%2==1><#assign class="grayStyle"/></#if>
		  	<#if task_index%2==0><#assign class="brightStyle"/></#if>
			<tr class="${class}" align="center" onmouseover="displayPrompt('${task.id}');" onmouseout="toolTip();">
				<@table.selectTd id="taskId" value=task.id/>
		      	<td><#if task.arrangeInfo.isArrangeComplete == false>${task.seqNo?if_exists}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}">${task.seqNo?if_exists}</a></#if></td>
		      	<td><A href="javascript:courseInfo('id', ${task.course.id})">${task.course.code}</A></td>
		      	<td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></a></td>
		      	<td title="${task.teachClass.name?html}">${task.teachClass.name?html}</td>
                <td title="${task.arrangeInfo.teachDepart.name?html}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;">${task.arrangeInfo.teachDepart.name?html}</span></td>
		      	<td id="teachers_${task.id}"><@getTeacherNames task.arrangeInfo.teachers/></td>
		      	<td><@i18nName task.courseType/></td>
		      	<td>${task.teachClass.planStdCount}</td>
		      	<td title="${(task.taskGroup.name)?if_exists?html}" nowrap><span style="display:block;width:80px;overflow:hidden;text-overflow:ellipsis;">${(task.taskGroup.name)?if_exists?html}</span></td>
	      	</tr>
	    </#list>
	    <#if thisPageSize?exists>
	    	<#include "/templates/newPageBar.ftl"/>
	    </#if>
	</@>
</div>
<#list 1..3 as i><br></#list>
<script>
	var bar = new ToolBar('taskListBar','<#if !RequestParameters['task.arrangeInfo.isArrangeComplete']?exists || RequestParameters['task.arrangeInfo.isArrangeComplete']=="0"><@bean.message key="common.notArranged" /><#else><@bean.message key="common.alreadyArranged" /></#if>课程列表',null,true,true);
	bar.setMessage('<@getMessage/>');
	<#if !RequestParameters['task.arrangeInfo.isArrangeComplete']?exists || RequestParameters['task.arrangeInfo.isArrangeComplete']=="0">
	bar.addItem("排课建议","editSuggestTime()");
	bar.addItem("<@bean.message key="action.manualArrange"/>",adjust);
	bar.addItem("修改任务","editTeachTask()",'update.gif');
	<#else>
	bar.addItem("<@bean.message key="action.export"/>","exportData()",'excel.png');
	bar.addItem("更换老师","changeTeacher()");
	bar.addItem("<@bean.message key="action.adjust"/>","adjust()");
	bar.addItem("平移教学周",shift);
	bar.addItem("<@bean.message key="action.delete"/>","removeArrangeResult()","delete.gif");
	</#if>
    document.getElementById('processDIV').style.display="none";
    document.getElementById('contentDIV').style.display="block";
    function displayPrompt(taskId){
        <#if !RequestParameters['task.arrangeInfo.isArrangeComplete']?exists || RequestParameters['task.arrangeInfo.isArrangeComplete']=="0">
        displaySuggestOfArrange(taskId);
        <#else>
        displayArrangeResult(taskId);
        </#if>
    }
    function editSuggestTime(){
      suggestTime(document.taskListForm);
    }
    var form = document.taskListForm;
	function query(){
	    transferParams(parent.document.taskForm, form, null, false);
	    form.action="manualArrange.do?method=taskList";
	    form.submit();
	}
	
	function courseInfo(selectId, courseId) {
	   if (null == courseId || "" == courseId || isMultiId(selectId) == true) {
	       alert("请选择一条要操作的记录。");
	       return;
	   }
	   form.action = "courseSearch.do?method=info";
	   form[selectId].value = courseId;
	   form.submit();
	}
	
	function changeTeacher(){
	   setSearchParams();
       form.action="manualArrange.do?method=displayTeachers";
       submitId(form,"taskId",false);
	}
	function setSearchParams(){
	    var params = getInputParams(form,null,false);
        params += getInputParams(parent.document.taskForm,null,false);
        addInput(form,"params",params);
    }
    function adjust(){
        var taskId = getCheckBoxValue(document.getElementsByName("taskId"));
        if(taskId.indexOf(",")!=-1){alert("请选择一个进行调整");return;}
        if(taskId=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
        setSearchParams();
        form.action="manualArrange.do?method=manualArrange&task.id="+taskId;
        form.submit();
    }
    
    function shift(){
        setSearchParams();
        form.action="manualArrange.do?method=shift";
        var offset = prompt("请输入平移量(正数为向后偏移，负数向前移动)","0");
        if(null!=offset){
        	if (confirm("如果平移教学周超出被设置的教学周范围，将删除被移出的教学周。\n"
        			  + "比如：假设当前被设置的教学周为1-17周时：\n"
        			  + "　　　此时若向前移5周，结果为1-12周；\n"
        			  + "　　　若向后移5周，结果为6-17周。\n\n"
        			  + "是否要继续？") == false) {
        		return;
        	}
          	addInput(form,"offset",offset);
          	submitId(form,"taskId",true);
        }
    }
    function exportData(){
       setSearchParams();
       form.action="teachTask.do?method=exportSetting";
       form.submit();
    }
   function editTeachTask(){
       var id = getCheckBoxValue(document.getElementsByName("taskId"));
       if(id=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       if(id.indexOf(",")!=-1) {alert("<@bean.message key="common.singleSelectPlease" />。");return;}
       window.open("teachTask.do?method=edit&forward=actionResult&task.id=" + id);
   }
  </script>
</body>
<#include "/templates/foot.ftl"/>