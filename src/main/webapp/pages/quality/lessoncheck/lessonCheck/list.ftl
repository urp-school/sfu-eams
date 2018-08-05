<#include "/templates/head.ftl"/>
 <body>
 <table id="myBar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="lessonCheckId"/>
          <@table.sortTd width="10%" text="学年" id="lessonCheck.calendar.year"/>
          <@table.sortTd width="10%" name="attr.courseName" id="lessonCheck.task.course.name"/>
          <@table.sortTd width="10%" name="entity.courseType" id="lessonCheck.task.courseType.name"/>
          <@table.sortTd width="10%" name="info.studentClassManager.className" id="lessonCheck.task.teachClass.name"/>
          <@table.td width="10%" name="entity.teacher"/>
          <@table.td width="10%" text="上课地点" id="lessonCheck.checkRoom"/>
 	      <@table.td width="10%" text="听课类别" id="lessonCheck.lessonCheckType.name"/>
 	       <@table.td width="10%" text="听课对象" id="lessonCheck.checkers"/>
 	      
   </@>
   <@table.tbody datas=lessonChecks;lessonCheck>
      <@table.selectTd id="lessonCheckId" value=lessonCheck.id/>
      <td>${(lessonCheck.calendar.year)?if_exists}</td>
	  <td>${(lessonCheck.task.course.name)?if_exists}</td>
	  <td>${(lessonCheck.task.courseType.name)?if_exists}</td>
	  <td>${(lessonCheck.task.teachClass.name)?if_exists}</td>
	  <td><@getTeacherNames lessonCheck.task.arrangeInfo.teachers/></td>
	   <td>${(lessonCheck.checkRoom)?if_exists}</td>
	  <td>${(lessonCheck.lessonCheckType.name)?if_exists}</td>
	   <td>${(lessonCheck.checkers)?if_exists}</td>
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="lessonCheck.do" entity="lessonCheck">
  </@>
 <script>
   var bar = new ToolBar('myBar','&nbsp;督导组听课信息',null,true,true);
   var form =document.actionForm;
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.add"/>","add()");
   bar.addItem("<@bean.message key="action.modify"/>","edit()");
   bar.addItem("<@bean.message key="action.delete"/>","remove()");
 </script>

 </body>
 <#include "/templates/foot.ftl"/>