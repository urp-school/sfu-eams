<#include "/templates/head.ftl"/>
<BODY onblur="self.focus();" LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">  
   		<tr>
    		<td>
				<@table.table width="100%">
				   	<@table.thead>
				   		<td>&nbsp;</td>
				   		<@table.td name="attr.taskNo"/>
				   		<@table.td name="attr.courseName"/>
				   		<@table.td name="field.questionnaireStatistic.teacher"/>
				   		<@table.td name="field.questionnaireStatistic.courseOpenTime"/>
				   		<@table.td name="field.questionnaireStatistic.courseOpenCollege"/>
				   		<@table.td text="课程问卷"/>
				   	</@>
				   	<@table.tbody datas=tasks;teachTask>
				   		<@table.selectTd type="radio" id="taskId" value=teachTask.id/>
				     	<td>${teachTask.seqNo}</td>
				    	<td><label id="courseName${teachTask.id}">${teachTask.course.name?html}</label></td>
				    	<td id="teacherName${teachTask.id}"><#list teachTask.arrangeInfo.teachers?if_exists?sort_by("name") as teacher><#if teacher_index == teachTask.arrangeInfo.teachers?size - 1>${teacher.name?if_exists}<#else>${teacher.name?if_exists},</#if></#list></td>
				    	<td>${teachTask.calendar.year}&nbsp;&nbsp;${teachTask.calendar.term}</td>
				    	<td>${teachTask.arrangeInfo.teachDepart.name?html}</td>
				    	<td><input type="hidden" id="questionnaire${teachTask.id}" name="questionnaire${teachTask.id}" value="<#if teachTask.questionnaire?exists>true<#else>false</#if>"><#if teachTask.questionnaire?exists><input type="hidden" id="questionnaireValue${teachTask.id}" name="questionnaireValue${teachTask.id}"  value="${teachTask.questionnaire.id},${teachTask.questionnaire.depart.name}">${teachTask.questionnaire.description}<#else><font red="red">无问卷</font></#if></td>
				   	</@>
     			</@>
    		</td>
   		</tr>
   		<tr>
    		<td align="center">
     			<button name="button1" value="" class="buttonStyle" onClick="doSubmit();"><@bean.message key="action.confirm"/></button>
    		</td>
   		</tr>
  	</table>
	<script>
   		var bar = new ToolBar('backBar','<@bean.message key="field.questionnaireStatistic.courseList"/>',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		bar.addItem("选课课程","doSubmit()");
   		
	    var detailArray = {};
	    var arraySize = ${(tasks?size)?default(0)};
	    var teachTaskArray = new Array(arraySize);
	    <#list tasks as teachTask>
	    	teachTaskArray[${teachTask_index}] = new Array(2);
	    	<#list teachTask.arrangeInfo.teachers?if_exists as teacher>
	    		teachTaskArray[${teachTask_index}][0]=${teacher.id};
	    		teachTaskArray[${teachTask_index}][1]='${teacher.name}';
	    	</#list>
	    </#list>
	    function getName(id){
	       if (id != ""){
	          return detailArray[id]['name'];
	       }else{
	          return "";
	       }       
	    }
	    function pageGo(pageNo){
	       document.pageGoForm.pageNo.value = pageNo;
	       document.pageGoForm.submit();
	    }
	    function doSubmit(){
	    	var taskId = getSelectId("taskId");
	    	if("" == taskId){
	    		alert("请选择一个教学任务");
	    		return 
	    	} else {
	    		self.opener.$("taskId").value = taskId;
	    	}
	    	var flag = document.getElementById("questionnaire" + taskId);
	    	if("false" == flag.value){
	    		opener.addTargetValue("isHasQuestionnaire","true")
	    		if(confirm("你选课的课程没有问卷,请在[课程问卷]里面指定好问卷再提交")){
	    			window.close();
	    			return;
	    		}
	    	}
	    	
	    	opener.addTargetValue("isHasQuestionnaire","false");
	    	var departmentValue= document.getElementById("questionnaireValue"+taskId);
	    	if(null!=self.opener.document.getElementById("departmentId")){
	    		self.opener.addOption("departmentId",departmentValue.value);
	    	}
	    	var taskName = document.getElementById("courseName" + taskId).innerHTML;
	    	self.opener.setTeachTaskIdAndDescriptions(taskId, taskName);
	    	self.opener.removeSelect("teacherId");
	    	var whichId = selectedTeacher(teachTaskArray, taskId);
	    	if (whichId != -1) {
		    	self.opener.$("teacherId").options.add(new Option(teachTaskArray[whichId][1], teachTaskArray[whichId][0]));
	    	}
	    	window.close();
	    }
	    
	    function selectedTeacher(array, id) {
	    	if (array == null || array == "" || id == null || id == "") {
	    		return -1;
	    	}
	    	try {
	    		for (var i = 0; i < arraySize; i++) {
		    		if (array[i][1] == cleanSpaces($("teacherName" + id).innerHTML)) {
		    			return i;
		    		}
		    	}
		    	return -1;
	    	} catch (e) {
	    		return -1;
	    	}
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>