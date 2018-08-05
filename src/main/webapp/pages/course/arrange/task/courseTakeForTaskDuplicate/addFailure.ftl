<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="addBar" width="100%"></table>
 <table class="infoTable">
    <tr>
        <td class="title">课程:</td>
        <td class="content" style="width:35%">${task.course.name}（${task.course.code}）</td>
        <td class="title">实际/上限/教室真实:</td>
        <td class="content" style="width:35%">${task.teachClass.stdCount}/${task.electInfo.maxStdCount}/${task.roomsOfCapacity?default(0)}</td>
    </tr>
 </table>
 <hr>
 <@table.table id="listTable" width="100%">
	  <@table.thead>
	   <@table.selectAllTd id="stdNo"/>
	   <@table.td name="attr.stdNo"/>
	   <@table.td name="attr.personName"/>
	   <@table.td name="entity.department"/>
	   <@table.td name="entity.speciality"/>
	   <@table.td text="班级"/>
	   <@table.td text="原因"/>
	  </@>
	  <@table.tbody datas=failedStds;std>
	    <@table.selectTd id="stdNo" value=std.code/>
		<td>${std.code}</td>
		<td><@i18nName std/></td>
		<td><@i18nName std.department/></td>
		<td><@i18nName std.firstMajor?if_exists/></td>
		<td><@i18nName std.firstMajorClass?if_exists/></td>
		<td><@bean.message key=failMsgs[std.code]/></td>
	  </@>
  </@>
  <pre>
    1.系统会检测学生的学分上限(如果有学分上限),并更新学生的已选学分
    2.系统会检测任务选课人数上限(如果任务参选),并更新教学任务的上课人数
    3.自动判断修读类别,如果学生已经修过这门课,则会免修不免试.
  </pre>
   <form name="actionForm" method="post" action="courseTakeForTaskDuplicate.do?method=add" onsubmit="return false;">
   <input name="checkConstraint" type="hidden" value="0"/>
   <input name="taskId" type="hidden" value="${RequestParameters['taskId']}"/>
   </form>
 <script>
   var bar = new ToolBar("addBar","添加失败（实践）",null,true,true);
   bar.addItem("继续添加","add()");
   bar.addBack("<@msg.message key="action.back"/>");
   function add(){
    if (${task.teachClass.stdCount} >= ${task.roomsOfCapacity?default(0)} && confirm("当前课程选课人数已满或已超出，是否要继续？")) {
     submitId(document.actionForm,"stdNo",true);
    }
   }
 </script>
 </body>
<#include "/templates/foot.ftl"/>