<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>  
<script src='dwr/interface/teacherDAO.js'></script>
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.2,transition=1) blendtrans(duration=.2);
    }
-->
</style> 
<body  onload="DWRUtil.useLoadingMessage();">
 <table id="batchEditBar"></table>
 <script>
    var bar= new ToolBar("batchEditBar","指定教师、教室设备配置类型、双语挂牌",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.print"/>","print()");
    bar.addItem("<@bean.message key="action.save"/>","batchUpdate(document.taskListForm)");
    bar.addClose();
 </script>
 <div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 <script>initToolTips()</script>
	<@table.table width="100%" id="taskEdit" sortable="true">
		<form name="taskListForm" method="post" action="" onsubmit="return false;">
			<input type="hidden" name="params" value="${RequestParameters['params']?if_exists}"/>
		<@table.thead>
			<@table.selectAllTd id="taskId"/>
			<@table.td text="序号" width="30px"/>
			<@table.td name="attr.taskNo" width="5%"/>
			<@table.td name="attr.courseNo" width="5%"/>
			<@table.td name="attr.courseName" width="15%"/>
			<@table.td name="entity.teachClass" width="15%"/>
			<@table.td name="entity.teacher" width="25%"/>
			<@table.td text="教室配置" width="10%"/>
			<@table.td name="attr.teachLangType"/>
			<@table.td text="挂牌"/>
			<@table.td text="人数"/>
			<@table.td text="周课时"/>
			<@table.td text="周数"/>
			<@table.td name="attr.credit"/>
			<@table.td text="学时"/>
		</@>
		<@table.tbody datas=taskList;task,task_index>
			<@table.selectTd id="taskId" value=task.id title="只有选中的才保存被设置的信息"/>
			<td align="center">${task_index + 1}</td>
			<td align="center" onmouseover="displaySuggestOfArrange('${task.id}')" onmouseout=" toolTip();" onClick="editTaskSuggestTime('${task.id}')"><font color="blue">${task.seqNo}</font></td>
			<td align="center">${task.course.code}</td>
			<td><@i18nName task.course/></td>
			<td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
			<td>
				<#assign teacherName="">
				<#if task.arrangeInfo.teachers?size==1>
					<#assign teacherName><@getTeacherNames task.arrangeInfo.teachers/></#assign>
				</#if>
				<#if (task.arrangeInfo.teachers?size>1)>
					<@getTeacherNames task.arrangeInfo.teachers/>
				<#else>
					<select name="department${task.id}" id="department${task.id}" onmouseover="displayDepartList(event);" onChange="displayTeacherList('teacher${task.id}',this.value,true)" style="width:60%">
						<option value="${departMap[task.id?string].id}"><@i18nName departMap[task.id?string]/></option>
					</select><select name="teacherId${task.id}" id="teacher${task.id}" onmouseover="displayTeacherList('teacher${task.id}',department${task.id}.value,false)" style="width:40%">
					<#if teacherName!="">
						<option value="${task.arrangeInfo.teachers?first.id}">${teacherName}(${task.arrangeInfo.teachers?first.code})</option>
					<#else>
						<option value=""></option>
					</#if>
					</select>
				</#if>
			</td>
			<td>
				<select name="roomConfigTypeId${task.id}" id="roomConfigType${task.id}" onmouseover="displayRoomConfigList(event);" style="width:100%">
					<option value="${ task.requirement.roomConfigType.id}"><@i18nName task.requirement.roomConfigType/></option>
				</select>
			</td>
			<td><@htm.i18nSelect datas=teachLangTypes  name="teachLangTypeId${task.id}" selected=(task.requirement.teachLangType.id)?string/></td>
			<td><input type="checkbox" name="isGuaPai${task.id}"<#if task.requirement.isGuaPai> checked</#if>/></td>
			<td>${task.teachClass.planStdCount}</td>
			<td>${task.arrangeInfo.weekUnits}</td>
			<td>${task.arrangeInfo.weeks}</td>
			<td>${task.course.credits}</td>      
			<td>${task.arrangeInfo.overallUnits}</td>
		</@>
		</form>
		<tr align="center" class="darkColumn">
			<td colspan="15">本次修改任务数量：${taskList?size}。可以选择左侧的复选框，进行选择性的保存</td>
		</tr>
	</@>
	<script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js?v=20151209"></script>
    <script>
    var roomConfigTypeList = new Array();
    <#list roomConfigTypeList as config>
       roomConfigTypeList[${config_index}]={'id':'${config.id}','name':'<@i18nName config/>'};
    </#list>
    function displayRoomConfigList(e){
        var roomSelect =getEventTarget(e);
        if(roomSelect.tagName=="SELECT"){
	        if(roomSelect.options.length==1){
	            for(var i=0;i<roomConfigTypeList.length;i++){
	                if(roomConfigTypeList[i].id!=roomSelect.options[0].value)
	                roomSelect.options[roomSelect.options.length]=new Option(roomConfigTypeList[i].name,roomConfigTypeList[i].id);
	            }
	        }
        }
    }
     var departmentList= new Array();
	 var teacherMap= new Object();
	 <#list departmentList?sort_by("code") as department>
	   departmentList[${department_index}]={'id':'${department.id}','name':'<@i18nName department/>'};
	 </#list>
    function batchUpdate(form){
        if(getCheckBoxValue(form.taskId)=="") {alert("请选择一个或多个进行保存设置");return;}
        form.action="?method=batchUpdate&hold=true";
        form.submit();
    }
</script>
<#include "/pages/course/arrange/taskArrangeSuggestPrompt.ftl"/> 
</body>
<#include "/templates/foot.ftl"/>