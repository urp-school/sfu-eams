<#include "/templates/head.ftl"/>
<body >
<table id="examActivityBar" width="100%"></table>
 <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="courseTakeId"/>
      <@table.sortTd width="8%" name="attr.stdNo" id="take.student.code"/>
      <@table.sortTd width="10%" name="attr.personName" id="take.student.name"/>
      <@table.sortTd width="8%" name="attr.courseNo" id="take.task.course.code"/>
      <@table.sortTd width="8%" name="attr.taskNo" id="take.task.seqNo"/>
      <@table.sortTd width="20%" name="attr.courseName" id="take.task.course.name"/>
      <@table.sortTd width="5%" name="attr.credit" id="take.task.course.credits"/>
    </@>
    <@table.tbody datas=courseTakes;take>
      <@table.selectTd id="courseTakeId" value="${take.id}"/>
      <td>${take.student.code}</td>
      <td><@i18nName take.student/></td>
      <td>${take.task.course.code}</td>
      <td>${take.task.seqNo?if_exists}</td>
      <td><@i18nName take.task.course/></td>
      <td>${take.task.course.credits}</td>
    </@>
  </@>
	<form name="actionForm" method="post" action="examTake.do?method=noTakeStdList" onsubmit="return false;">
	   <input type="hidden" name="pageNo" value="1">
	   <input type="hidden" name="pageSize" value="20">
	   <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}">
	   <input type="hidden" name="take.examType.id" value="${RequestParameters['take.examType.id']}">
	</form>
  <script>
     var bar=new ToolBar('examActivityBar','未参加 [<@i18nName examType/>] 学生名单',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("生成考试记录","genExamTake()");
     bar.addItem("<@msg.message key="action.print"/>","print()");
     var form = document.actionForm;
	 function genExamTake(){
	    submitId(form,"courseTakeId",true,"examTake.do?method=genExamTake","确认生成？");
	 }
	</script>
</body>
<#include "/templates/foot.ftl"/> 