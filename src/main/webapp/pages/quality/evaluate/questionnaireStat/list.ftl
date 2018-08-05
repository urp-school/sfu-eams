<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/quality/evaluate/evaluateStat.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="deleteTableTd('listTable','');f_frameStyleResize(self);">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="field.questionnaireStatistic.collegeTeacherDetailInfo"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("更改教师","doUpdateObject()");
   bar.addItem("<@msg.message key="action.delete"/>","doDeleteObject()");
   bar.addPrint("<@msg.message key="action.print"/>");
</script>

  <@table.table width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="questionnaireStatId"/>
      <@table.sortTd  name="workload.college" id="questionnaireStat.depart.name"/>
      <@table.sortTd  name="field.questionnaireStatistic.teacher" id="questionnaireStat.teacher.name"/>
      <@table.sortTd  name="field.select.selectQueryCourseName"  id="questionnaireStat.course.name"/>
      <@table.sortTd  name="field.select.selectQueryCourseId"  id="questionnaireStat.course.code"/>
      <@table.sortTd  name="attr.taskNo"  id="questionnaireStat.taskSeqNo"/>
      <@table.sortTd  name="field.questionnaireStatistic.validTicketNumber"  id="questionnaireStat.validTickets"/>
      <#list questionTypeList?if_exists as questionType>
	     <@table.td text=questionType.name?if_exists/>
	   </#list>
	   <@table.td text="总评"/>
	   <@table.td text="学年度学期"/>
    </@>
    <@table.tbody datas=questionnaireStats;questionnaireStat>
      <@table.selectTd id="questionnaireStatId" value="${questionnaireStat.id}"/>
  		<td>${questionnaireStat.depart.name}</td>
  		<td>&nbsp;<@i18nName (questionnaireStat.teacher)?if_exists/>&nbsp;</td>
  		<td>${questionnaireStat.course.name}</td>
  		<td>${questionnaireStat.course.code}</td>
  		<td>${questionnaireStat.taskSeqNo}</td>
        <td>${questionnaireStat.validTickets?default("0")}</td>
        <#list questionTypeList?if_exists as questionType>
        	<td>${questionnaireStat.getTypeScoreDisplay(criteria,questionType.id)}</td>
        </#list>
        <td>${questionnaireStat.getScoreDisplay(criteria)}</td>
        <td>${questionnaireStat.calendar.year},${questionnaireStat.calendar.term}</td>
      </@>
    </@>
	<form name="listForm" method="post" action="" onsubmit="return false;"></form>
  </body>
    <script language="javascript">
  	var form = document.listForm;
  	var action="questionnaireStat.do";
 	function doUpdateObject(){
 		form.action=action+"?method=modifyTeacher";
 		setSearchParams(parent.document.searchForm,form);
 		submitId(form,"questionnaireStatId",false);
 	}
 	function doDeleteObject(){
 		form.action=action+"?method=remove";
 	    setSearchParams(parent.searchForm,form);
 		submitId(form,"questionnaireStatId",true,null,"你确定要删除这条评教数据吗?");
 	}
 </script>
<#include "/templates/foot.ftl"/>