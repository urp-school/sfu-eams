<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/quality/evaluate/evaluateStat.js"></script>
<BODY onload="deleteTableTd('teacherStatId','');f_frameStyleResize(self);">
	<table id="backBar" width="100%"></table>
	<@table.table id="teacherStatId" width="100%">
		<@table.thead>
			<@table.selectAllTd id="teacherStatId"/>
			<@table.td name="entity.course" id="teacherStat.course.id"/>
			<@table.td name="quality.questionnaireStatTeacher.instructor" id="teacher.name"/>
			<#list questionTypeList?if_exists as questionType>
				<#assign questionTypeName><@i18nName questionType/></#assign>
				<@table.td text=questionTypeName?replace("'","&#39;")/>
 				</#list>
			<@table.td name="grade.average"/>
			<@table.td name="attr.yearAndTerm"/>
			<@table.td text="明细评教情况"/>
		</@>
		<@table.tbody datas=questionnaireStatTeachers;teacherStat>
			<@table.selectTd id="teacherStatId" value=teacherStat.id/>
			<input type="hidden" id="teacherStat_${teacherStat.id}" value="${(teacherStat.task.id)?if_exists}"/>
  			<td width="20%"><@i18nName teacherStat.course/></td>
   			<td><#list (teacherStat.task.arrangeInfo.teachers)?if_exists as teacher>
   			    	${teacher.name}<#if teacher_has_next>,</#if>
   				</#list>
   			</td>
   			<#list questionTypeList?if_exists as questionType>
 			<td>${teacherStat.getTypeScoreDisplay(criteria,questionType.id)}</td>
 			</#list>
 			<td>${teacherStat.getScoreDisplay(criteria)}</td>
   			<td>${teacherStat.calendar.year?if_exists},${teacherStat.calendar.term?if_exists}</td>
   			<td><#assign isEvaluateDetail = !evaluateDetailMap[teacherStat.id?string]/><#if isEvaluateDetail><A href="#" onclick="javascript:evaluatePersonInfo('${teacherStat.id}')">已评教</A><#else>未评教</#if></td>
			<input type="hidden" id="isEvaluateDetail_${teacherStat.id}" value="${isEvaluateDetail?string("1", "0")}"/>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="questionnaireStatTeacher.do" entity="teacherStat">
        <input type="hidden" name="teacherStatId" value=""/>
    </@>
	<script>
	   	var bar = new ToolBar('backBar','<@msg.message key="field.teacherEvaluate.teachQuestionnaireResults"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("查询个人评教情况", "evaluatePersonInfo()");
	   	bar.addPrint("<@msg.message key="action.print"/>");
	   
		function exportObject(){
			form.action="questionnaireStatTeacher.do?method=export";
			setSearchParams(parent.form,form,"questionnaireStat");
			form.submit();
		}
		
		function evaluatePersonInfo(teacherStatId) {
			if (null == teacherStatId || "" == teacherStatId) {
                teacherStatId = getSelectIds("teacherStatId");
			}
			if (null == teacherStatId || "" == teacherStatId || null != teacherStatId.match(new RegExp(",", "gi")) && teacherStatId.match(new RegExp(",", "gi")).length > 0) {
				alert("请选择一条要操作的记录。");
				return;
			}
			
			if ("" == $("teacherStat_" + teacherStatId).value) {
				alert("当前课程不在教学任务中，无法查看评教详情。");
				return;
			}
			
			if ("0" == $("isEvaluateDetail_" + teacherStatId).value) {
				alert("当前课程还没有被评教。");
				return;
			}
			
			form.action = "questionnaireStatTeacher.do?method=evaluatePersonInfo";
			form["teacherStatId"].value = teacherStatId;
			form.submit();
		}
	</script>
</BODY>
<#include "/templates/foot.ftl"/>