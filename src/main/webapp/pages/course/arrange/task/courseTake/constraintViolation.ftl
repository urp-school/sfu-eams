<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="addBar" width="100%"></table>
 <@table.table id="listTable" width="100%" sortable="true">
	  <@table.thead>
	   <@table.sortTd id="courseTake.task.seqNo" name="attr.taskNo" width="8%"/>
	   <@table.sortTd id="courseTake.task.course.name" name="attr.courseName" width="20%"/>
	   <@table.sortTd id="courseTake.task.courseType.name" name="entity.courseType" width="13%"/>
	   <@table.td name="task.arrangeInfo" width="20%"/>
	   <@table.sortTd id="courseTake.task.course.credits" name="attr.credit" width="5%"/>
	  </@>
	  <@table.tbody datas=tasks;task>
	    <td>${task.seqNo}</td>
	    <td><@i18nName task.course/></td>
	    <td><@i18nName task.courseType/></td>
	    <td align="left">${task.arrangeInfo.digest(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}</td>
	    <td>${task.course.credits}</td>
	  </@>
  </@>
  <pre>
    1.系统会检测学生的学分上限(如果有学分上限),并更新学生的已选学分
    2.系统会检测任务选课人数上限(如果任务参选),并更新教学任务的上课人数
    3.自动判断修读类别,如果学生已经修过这门课,则会免修不免试.
  </pre>
   <form name="actionForm" method="post" action="courseTake.do?method=add" onsubmit="return false;">
	   <input name="stdId" type="hidden" value="${RequestParameters['stdId']}"/>
	   <input name="calendarId" type="hidden" value="${RequestParameters['calendarId']}"/>
	   <input name="seqNo" type="hidden" value="${RequestParameters['seqNo']}"/>
	   <input name="params" type="hidden" value="${RequestParameters['params']}"/>
	   <input name="checkConstraint" type="hidden" value="0"/>
   </form>
 <script>
   var bar = new ToolBar("addBar","违反约束",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("继续添加","actionForm.submit()");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/>