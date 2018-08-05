<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="gradeListBar" width="100%"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="gradeId"/>
	     <@table.sortTd width="10%" id="grade.std.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="grade.std.name" name="attr.personName"/>
		 <@table.sortTd width="8%" id="grade.taskSeqNo" name="attr.taskNo"/>
         <@table.sortTd width="8%" id="grade.course.code" name="attr.courseNo"/>
		 <@table.sortTd width="15%" id="grade.course.name" name="attr.courseName"/>
		 <@table.sortTd width="15%" id="grade.score" text="成绩"/>
	   </@>
	   <@table.tbody datas=noPassCourseGrades;grade>
	    <@table.selectTd  type="checkbox" id="gradeId" value="${grade.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${grade.std.id}" target="blank" >${grade.std.code}</A></td>
        <td>${grade.std.name}</td>
        <td>${(grade.taskSeqNo)?default((grade.task.seqNo)?default(""))}</td>
        <td>${grade.course.code}</td>
        <td><@i18nName grade.course/></td>
        <td>${grade.getScoreDisplay()}</td>
	   </@>
     </@>
  <form name="actionForm" method="post" action="collegeGrade.do?method=noPassCourseGradeList" onsubmit="return false;">
      <input name="calendar.id" type="hidden" value="${RequestParameters['calendar.id']}">
  </form>
  <script>
    var bar = new ToolBar("gradeListBar","不及格成绩查询结果",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addItem("<@msg.message key="action.export"/>", "exportData()");
    
    var form = document.actionForm;
    
    function exportData() {
    	form.action = "collegeGrade.do?method=export";
    	var keys = "std.code,std.name,taskSeqNo,course.code,course.name,score";
    	var titles = "学号,姓名,课程序号,课程代码,课程名称，成绩";
    	addInput(form, "keys", keys, "hidden");
    	addInput(form, "titles", titles, "hidden");
    	addInput(form, "kind", "noPassCourseGradeList", "hidden");
    	form.submit();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
