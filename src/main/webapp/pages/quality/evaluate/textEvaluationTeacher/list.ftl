<#include "/templates/head.ftl"/>
<BODY>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="textEvaluation.teacher.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
</script>			
<@table.table width="100%" sortable="true" id="listTable">
  <@table.thead>
 	<@table.sortTd width="20%"  name="entity.course" id="textEvaluation.task.course.name"/>
 	<@table.td width="30%" name="textEvaluation.teacher.stdcomments"/>
 	<@table.td width="10%" name="evaluate.teacher"/>
 	<@table.sortTd width="20%" name="attr.yearAndTerm" id="textEvaluation.calendar.start"/>
  </@>
  <@table.tbody datas=textEvaluations;textEvaluation>
   	<td><@i18nName textEvaluation.task.course/></td>
   	<td>${(textEvaluation.context)?default('')?html}</td>
   	<td>${textEvaluation.isForCourse?default(false)?string("课程评教","教师评教")}</td>
   	<td><@bean.message key="teachCalendar.content" arg0=textEvaluation.task.calendar.year arg1=textEvaluation.task.calendar.term/></td>
  </@>
</@>
</BODY>
<#include "/templates/foot.ftl"/>