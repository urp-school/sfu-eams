<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="take.studentId"/>
	     <@table.sortTd width="10%" id="take.student.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="take.student.name" name="attr.personName"/>
	     <@table.sortTd width="8%" id="take.student.enrollYear" name="attr.enrollTurn"/>
	     <@table.sortTd width="15%"id="take.student.department.name" name="entity.college"/>
				<@table.sortTd width="8%" id="take.task.seqNo" name="attr.taskNo"/>
      <@table.sortTd width="8%" id="take.task.course.code" name="attr.courseNo"/>
				 <@table.sortTd width="15%" id="take.task.course.name" name="attr.courseName"/>
	   </@>
	   <@table.tbody datas=noGradeTakes;take>
	    <@table.selectTd  type="checkbox" id="take.studentId" value="${take.student.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${take.student.id}" target="blank" >${take.student.code}</A></td>
        <td><@i18nName take.student/></td>
        <td>${take.student.enrollYear}</td>
        <td><@i18nName take.student.department/></td>
        <td>${take.task.seqNo}</td>
        <td>${take.task.course.code}</td>
        <td><@i18nName take.task.course/></td>
	   </@>
     </@>
  <form name="actionForm" method="post" action="collegeGrade.do?method=noGradeTakeList" onsubmit="return false;">
      <input name="calendar.id" type="hidden" value="${RequestParameters['calendar.id']}">
  </form>
  <script>
    var form = document.actionForm;
    var bar = new ToolBar("gradeListBar","成绩查询结果",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.export"/>","exportData()");
    bar.addPrint("<@msg.message key="action.print"/>");
    function exportData(){
       if(confirm("是否导出所有查询结果？")){
          addInput(form,"keys","task.calendar.year,task.calendar.term,student.code,student.name,task.seqNo,task.course.code,task.course.name,task.arrangeInfo.teacherNames,task.course.credits,courseType.name,courseTakeType.name,student.type.name");
          addInput(form,"titles","学年度,学期,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,授课教师,课程学分,<@msg.message key="entity.courseType"/>,修读类别,<@msg.message key="entity.studentType"/>");
          form.action="collegeGrade.do?method=export";
          addInput(form, "kind", "noGradeTakeList", "hidden");
          form.submit();
       }
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
