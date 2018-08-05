<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0 >
  <@getMessage/>
<table width="100%" align="center" class="listTable">
	<form name="evaluateForm" method="post" action="" onsubmit="return false;">
	<tr class="darkColumn">
	    <td><@msg.message key="attr.courseNo"/></td>
		<td><@msg.message key="attr.courseName"/></td>
		<td><@msg.message key="attr.teachDepart"/></td>
		<td><@msg.message key="evaluate.forStudent"/></td>
		<td><@msg.message key="attr.courseTeacher"/></td>
		<td><@msg.message key="std.isBeenEvaluation"/></td>
		<td><@msg.message key="action.modeOfOperation"/></td>
	</tr>
	<#list teachTaskList?sort_by("seqNo") as teachTask>
	<#if teachTask_index%2==0><#assign class="grayStyle"><#else><#assign class="brightStyle"></#if>
	<#if teachTask.requirement.evaluateByTeacher?exists&& teachTask.requirement.evaluateByTeacher==true>
		<#assign teacherSize=teachTask.arrangeInfo.teachers?size>
		<#list teachTask.arrangeInfo.teachers?if_exists as teacher>
			<tr class="${class}">
			<#if "1"==evaluateMap[teachTask.id?string+"_"+teacher.id?string]?default("0")><#assign flag=true><#else><#assign flag=false></#if>
			<#if teacher_index%2==0><#assign class="grayStyle"><#else><#assign class="brightStyle"></#if>
			<#if teacher_index==0>
				<td rowspan="${teacherSize}">${teachTask.course.code}</td>
				<td rowspan="${teacherSize}"><@i18nName (teachTask.course)?if_exists/></td>
				<td rowspan="${teacherSize}"><@i18nName (teachTask.arrangeInfo.teachDepart)?if_exists/></td>
				<td rowspan="${teacherSize}"><@i18nName (teachTask.teachClass.stdType)?if_exists/></td>
				<td><@i18nName (teacher)?if_exists/></td>
				<td><#if flag><@msg.message key="evaluate.done"/><#else><font color="red"><@msg.message key="evaluate.noDo"/></font></#if></td>
				<td><input type="hidden" id="evaluate${teachTask_index}_${teacher_index}" name="evaluate${teachTask_index}_${teacher_index}" value="${teachTask.id},${teacher.id}"><a href="javascript:<#if flag>doEvaluate('update','${teachTask_index}_${teacher_index}')<#else>doEvaluate('evaluate','${teachTask_index}_${teacher_index}')</#if>"><font color="blue"><#if flag><@msg.message key="evaluate.update"/><#else><@msg.message key="evaluate.do"/></#if></font></a></td>
				</tr>
			<#else>
			<td>${teacher.name?if_exists}</td>
			<td><#if flag><@msg.message key="evaluate.done"/><#else><font color="red"><@msg.message key="evaluate.noDo"/></font></#if></td>
			<td><input type="hidden" id="evaluate${teachTask_index}_${teacher_index}" name="evaluate${teachTask_index}_${teacher_index}" value="${teachTask.id},${teacher.id}"><a href="javascript:<#if flag>doEvaluate('update','${teachTask_index}_${teacher_index}')<#else>doEvaluate('evaluate','${teachTask_index}_${teacher_index}')</#if>"><font color="blue"><#if flag><@msg.message key="evaluate.update"/><#else><@msg.message key="evaluate.do"/></#if></font></a></td>
			</tr>
			</#if>
		</#list>
	<#else>
		<#if "1"==evaluateMap[teachTask.id?string+"_0"]?default("0")><#assign flag=true><#else><#assign flag=false></#if>
		<tr class="${class}">
		    <td>${teachTask.course.code}</td>
			<td><@i18nName teachTask.course/></td>
			<td>${teachTask.arrangeInfo.teachDepart.name?if_exists}</td>
			<td>${teachTask.teachClass.stdType.name?if_exists}</td>
			<td><#list (teachTask.arrangeInfo.teachers)?if_exists as teacher><@i18nName teacher?if_exists/><#if teacher_has_next>,</#if></#list></td>
			<td><#if flag><@msg.message key="evaluate.done"/><#else><font color="red"><@msg.message key="evaluate.noDo"/></font></#if></td>
			<td><input type="hidden" id="evaluate${teachTask_index}" name="evaluate${teachTask_index}" value="${teachTask.id}"><a href="javascript:<#if flag>doEvaluate('update','${teachTask_index}')<#else>doEvaluate('evaluate','${teachTask_index}')</#if>"><font color="blue"><#if flag><@msg.message key="evaluate.update"/><#else><@msg.message key="evaluate.do"/></#if></font></a></td>
		</tr>
	</#if>
	</#list>
	<tr class="darkColumn">
		<td colspan="7" height="25px;"></td>
	</tr>
	</form>
</table>
 <script language="javascript">
	var form = document.evaluateForm;
	function doEvaluate(value,nodeName){
		var url="";
		var id = document.getElementById("evaluate"+nodeName).value;
		if(id==""){
			alert("<@msg.message key="info.choiceCourseTip"/>");
			return;
		}
		form.action="evaluateStd.do?method=loadQuestionnaire&calendar.id=${RequestParameters['calendar.id']}";
		addInput(form,"evaluateId",id);
		addInput(form,"evaluateState",value)
		form.submit();
	}
	</script>
</body>
<#include "/templates/foot.ftl"/>
