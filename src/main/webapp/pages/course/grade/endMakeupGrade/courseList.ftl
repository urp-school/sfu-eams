<#include "/templates/head.ftl"/>
<body>
 <table id="taskBar"></table>
 <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="courseId"/>
      <@table.sortTd id="activity.task.course.code" width="8%" name="attr.courseNo" />
      <@table.sortTd id="activity.task.course.name" width="20%" name="attr.courseName"/>
      <td width="20%" id="activity.time.year,activity.time.validWeeksNum desc,activity.time.weekId,activity.time.startTime" desc="activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc,activity.time.startTime desc" class="tableHeaderSort">考试安排</td>
      <@table.sortTd id="activity.task.arrangeInfo.teachDepart.name" width="20%" name="attr.teachDepart"/>      
    </@>
    <@table.tbody datas=courseList;task>
      <@table.selectTd type="checkbox" value="${task[0].id+'@'+task[1].id}" id="courseId"/>
      <td>${(task[0].code)?if_exists}</td>
      <td><@i18nName task[0]/></A></td> 
      <td>20${(task[2].firstDay)?if_exists?string("yy-MM-dd")} 日--${(task[2].timeZone)?if_exists}</td>
      <td>${(task[1].name)?if_exists}</td>      
    </@>
  </@>
  <br><br><br><br>
  <form name="actionForm" method="post" onsubmit="return false;">
     <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
     <input type="hidden" name="courseIds" value=""/>
  </form>
  <script> 
	var bar=new ToolBar('taskBar','补缓考课程列表',null,true,true);
	bar.addItem("<@msg.message key="action.info"/>","gradeInfo(document.actionForm)");
	bar.addItem("补缓考成绩录入", 'batchAddGrade(document.actionForm)',"new.gif");
	bar.addItem('补缓考成绩登分册','printEmptyGradeTable(document.actionForm)');
	function printEmptyGradeTable(form){
		var courseIds = getSelectIds("courseId");
		if(""==courseIds){alert("请选择一个或多个补缓考课程");return;}
		form.courseIds.value = courseIds;
		form.action = "makeupGrade.do?method=gradeTable&calendar.id=${RequestParameters['calendar.id']}";
		form.target = "_blank";
		form.submit();
	}
	function batchAddGrade(form){
		var courseIds = getSelectIds("courseId");
		if((courseIds.split(",")).length!=1||""==courseIds){ alert("请选择一门课程"); return;}
		form.courseIds.value = courseIds;
		form.action="makeupGrade.do?method=batchAddGrade&calendar.id=${RequestParameters['calendar.id']}";
		form.target="_blank";
		form.submit();
	}
	function gradeInfo(form){
		var courseIds = getSelectIds("courseId");
		if((courseIds.split(",")).length!=1||""==courseIds){ alert("请选择一门课程"); return;}
		form.courseIds.value = courseIds;
		form.action="makeupGrade.do?method=gradeInfo&calendar.id=${RequestParameters['calendar.id']}";
		form.target="_blank";
		form.submit();
	}
  </script>
</body> 
<#include "/templates/foot.ftl"/> 