<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="pages/quality/evaluate/evaluateStat.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="deleteTableTd('listTable','');">
 <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="field.questionnaireStatistic.collegeTeacherDetailInfo"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
</script>

     	<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="questionnaireStatId"/>
      <@table.sortTd  name="workload.college" id="questionnaireStat.depart.name"/>
      <@table.sortTd  name="field.questionnaireStatistic.teacher" id="questionnaireStat.teacher.name"/>
      <@table.sortTd  name="field.select.selectQueryCourseName"  id="questionnaireStat.course.name"/>
      <#list questionTypeList?if_exists as questionType>
	     <@table.td text="${questionType.name}"/>
	   </#list>
	   <@table.td text="总评"/>
	   <@table.td text="学年度学期"/>
    </@>
    <@table.tbody datas=questionnaireStats;questionnaireStat>
      <@table.selectTd id="questionnaireStatId" value="${questionnaireStat.id}"/>
  		<td>${questionnaireStat.depart.name}</td>
  		<td>${questionnaireStat.teacher.name}</td>
  		<td>${questionnaireStat.course.name}</td>
        <#list questionTypeList?if_exists as questionType>
        	<td>${questionnaireStat.getTypeScoreDisplay(criteria,questionType.id)}</td>
        </#list>
        <td>${questionnaireStat.getScoreDisplay(criteria)}</td>
        <td>${questionnaireStat.calendar.year},${questionnaireStat.calendar.term}</td>
      </@>
    </@>
  </body>
<#include "/templates/foot.ftl"/>