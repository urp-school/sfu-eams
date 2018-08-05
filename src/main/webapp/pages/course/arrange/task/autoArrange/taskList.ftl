<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/course/task/task.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<script language="JavaScript" type="text/JavaScript" src="pages/course/arrange/arrange.js"></script> 
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.2,transition=1) blendtrans(duration=.2);
    }
-->
</style> 
<#assign isCompleted =RequestParameters['task.arrangeInfo.isArrangeComplete']/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 	<script>initToolTips()</script>
 	<div id="processDIV" style="display:block">页面加载中...</div>
 	<div id="contentDIV" style="display:none">
  	<form name="taskListForm" action="" method="post" onsubmit="return false;">
        <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
        <input type="hidden" name="type" value="course"/>
        <input type="hidden" name="id" value=""/>

  		<input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
  		<input type="hidden" name="calendar.id" value="${RequestParameters['task.calendar.id']}"/>
  		<input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
  		<input type="hidden" name="arrangeType" value="task"/>
 	 	<input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="${isCompleted}"/>
  	
  	<div id="taskListDiv">
  	<table id="arrangeTaskBar"></table>
 	<table width="100%" border="0" class="listTable">
    	<tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
      		<td align="center" >
        		<img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult"/>"/>
      		</td>
      		<td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      		<td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
      		<td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>               
      		<td><input style="width:100%" type="text" name="task.arrangeInfo.teachDepart.name" maxlength="20" value="${RequestParameters['task.arrangeInfo.teachDepart.name']?if_exists}"/></td>
      		<td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
      		<td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      		<td><input style="width:100%" type="text" name="task.requirement.roomConfigType.name" maxlength="20" value="${RequestParameters['task.requirement.roomConfigType.name']?if_exists}"/></td>             
      		<td><input style="width:100%" type="text" name="task.arrangeInfo.weekUnits" maxlength="2" value="${RequestParameters['task.arrangeInfo.weekUnits']?if_exists}"/></td>
    	</tr>
    	<tr align="center" class="darkColumn">
      		<td align="center" width="3%" >
        		<input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      		</td>
      		<td width="8%"><@bean.message key="attr.taskNo"/></td>
      		<td width="8%"><@bean.message key="attr.courseNo"/></td>
      		<td width="20%"><@bean.message key="attr.courseName"/></td>
	    	<td width="15%"><@bean.message key="attr.teachDepart"/></td>
      		<td width="10%"><@bean.message key="entity.courseType"/></td>
      		<td width="10%"><@bean.message key="entity.teacher"/></td>
      		<td width="10%"><@bean.message key="attr.roomConfigOfTask"/></td>
	      	<td width="7%"><@bean.message key="attr.weekHour"/></td>
    	</tr>
    	<#list taskList as task>
	   	  	<#if task_index%2==1 ><#assign class="grayStyle" ></#if>
		  	<#if task_index%2==0 ><#assign class="brightStyle" ></#if>
	     	<tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className);displayPrompt('${task.id}');" onmouseout="swapOutTR(this);toolTip();" onclick="onRowChange(event);">
	      		<td width="2%" class="select">
	        		<input type="checkBox" name="taskId" value="${task.id}"/>
	      		</td>
	      		<td><#if task.arrangeInfo.isArrangeComplete ==false>${task.seqNo}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}">${task.seqNo}</a></#if></td>
	      		<td><A href="javascript:courseInfo('id', ${task.course.id})">${task.course.code}</A></td>
	      		<td>
	       			<A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>">
	       			<@i18nName task.course/>
	       			</a>
	       		</td>
	      		<td><@i18nName task.arrangeInfo.teachDepart/></td>
	      		<td><@i18nName task.courseType/></td>
	      		<td><@getTeacherNames task.arrangeInfo.teachers/></td>
	      		<td><@i18nName task.requirement.roomConfigType/></td>
	      		<td>${task.arrangeInfo.weekUnits}</td>
	    	</tr>
		</#list>
	</form>
    <#include "/templates/newPageBar.ftl"/>
	</table>
  	</div>
  		<#if isCompleted=="0">
  			<form name="arrangeParamsForm" method="post" action="autoArrange.do?method=arrange" onsubmit="return false;">
  				<input type="hidden" name="taskGroup.suggest.time.available" value=""/>
  				<input type="hidden" name="taskIds" value=""/>
  				<input type="hidden" name="roomIds" value=""/>
  				<input type="hidden" name="isMonitor" value="1"/>
  				<input type="hidden" name="task" value="group"/>
  				<#include "params.ftl"/>
  			</form>
  		</#if>
 	</div>
 	<br><br><br><br><br>
   	<#if RequestParameters['task.arrangeInfo.isArrangeComplete']=="0">
   		<#include "/pages/course/arrange/taskArrangeSuggestPrompt.ftl"/>
   	<#else>
   		<#include "/pages/course/arrange/taskArrangeResult.ftl"/>
   	</#if>
	<script>
	    document.getElementById('processDIV').style.display = "none";
	    document.getElementById('contentDIV').style.display = "block";
	    
   		var bar = new ToolBar('arrangeTaskBar', '<#if isCompleted=="0"><@bean.message key="common.notArranged"/><#else><@bean.message key="common.alreadyArranged"/></#if><@bean.message key="entity.teachTask"/>', null, true, true);
   		bar.setMessage('<@getMessage/>');
   		<#if isCompleted == "0">
	   		bar.addItem("排课建议", "javascript:suggestTime(document.taskListForm)", 'update.gif');
	   		bar.addItem("<@bean.message key="action.modify"/>", editTeachTask, 'update.gif');
	   		bar.addItem("<@bean.message key="info.arrange.paramsManagement"/>", setParams, 'setting.png');
   		<#else>
   			bar.addItem("<@bean.message key="action.delete"/>", removeArrangeResult, 'delete.gif');
   		</#if>
   
	    var form = document.taskListForm;
    	function displayPrompt(taskId) {
        	<#if RequestParameters['task.arrangeInfo.isArrangeComplete'] == "0">
        		displaySuggestOfArrange(taskId);
        	<#else>
        		displayArrangeResult(taskId);
        	</#if>
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
	    
	    function checkSelectIds() {
	        var taskIds = getCheckBoxValue(document.getElementsByName("taskId"));
	        if (taskIds == "") {
	        	alert("请选择排课任务。");
	        	return false;
	        }
	        return true;
	    }
	    
		function query(pageNo, pageSize) {
		    form.action = "autoArrange.do?method=list";
		    if (null != pageNo) {
	          	form.action += "&pageNo=" + pageNo;
	        }
	        if (null != pageSize) {
	          	form.action += "&pageSize=" + pageSize;
	        }
		    form.submit();
		}
		
	    function pageGoWithSize(pageNo, pageSize) {
	        query(pageNo, pageSize);
	    }
	    function arrange() {
	        var taskIds = getCheckBoxValue(document.getElementsByName("taskId"));
	        if (taskIds == "") {
	        	alert("<@bean.message key="prompt.task.selector"/>");
	        	return;
	        }
	        
	        var allRoomIds = getAllOptionValue(parent.document.getElementById('selectedRoom'));
	        if (allRoomIds == "") {
	        	if(!confirm("没有为本次排课选用教室，确定使用任务的建议教室。\n点击确定，进行排课。否则点击取消")) {
	        		return;
	        	}
	        }
	        
	        document.arrangeParamsForm.roomIds.value = allRoomIds;
	        document.arrangeParamsForm.taskIds.value = taskIds;
	        document.arrangeParamsForm['taskGroup.suggest.time.available'].value = getAvailTime();
	        document.arrangeParamsForm.isMonitor.value = "1";
	        
	        document.arrangeParamsForm.submit();
	    }
	    
	    function editTeachTask() {
	       	var id = getCheckBoxValue(document.getElementsByName("taskId"));
	       	if(id == "") {
	       		alert("<@bean.message key="prompt.task.selector"/>");
	       		return;
	       	}
	       	if(id.indexOf(",") != -1) {
	       		alert("<@bean.message key="common.singleSelectPlease"/>。");
	       		return;
	       	}
	       	window.open("teachTask.do?method=edit&forward=actionResult&task.id=" + id);
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>