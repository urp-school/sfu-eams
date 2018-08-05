<#include "/templates/head.ftl"/>
<BODY>
<table id="bar"></table>
<@table.table  width="100%" sortable="true" id="listTable">
	<@table.thead>
		<@table.selectAllTd id="textEvaluationId"/>
        <@table.sortTd width="15%" name="textEvaluation.course" id="textEvaluation.task.course.name"/>
		<@table.td width="10%" name="textEvaluation.teacher"/>
		<@table.td width="30%" name="textEvaluation.ideaContext"/>
		<@table.sortTd width="10%" name="field.evaluate.yearAndTerm" id="textEvaluation.calendar.start"/>
		<@table.sortTd width="10%" text="是否被确认" id="textEvaluation.isAffirm"/>
	</@>
	<@table.tbody datas=textEvaluations;textEvaluation>
		<@table.selectTd id="textEvaluationId" value=textEvaluation.id/>
   		<td><@i18nName textEvaluation.task.course/></td>
   		<td><@i18nName (textEvaluation.teacher)?if_exists/></td>
   		<td>${(textEvaluation.context)?default('')?html}</td>
   		<td>${textEvaluation.calendar.year?if_exists},${textEvaluation.calendar.term?if_exists}</td>
   		<td><#if textEvaluation.isAffirm?default(false)><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></td>
   	</@>
</@>
<@htm.actionForm name="actionForm" entity="textEvaluation" action="textEvaluationStd.do"/>
<script>
  var bar = new ToolBar("bar","<@msg.message key="evaluate.std.personalTextEvalutation"/>",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.add"/>","add()");
  bar.addItem("<@msg.message key="action.edit"/>","edit()");
  bar.addPrint("<@msg.message key="action.print"/>");
</script>
</body>
<#include "/templates/foot.ftl"/>