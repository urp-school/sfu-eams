<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="addBar" width="100%"></table>
 <@table.table id="listTable" width="100%">
	  <@table.thead>
	   <@table.selectAllTd id="stdNo" />
	   <td><@msg.message key="attr.stdNo"/></td><td><@msg.message key="attr.personName"/></td><td><@msg.message key="entity.department"/></td><td><@msg.message key="entity.speciality"/></td><td>班级</td><td>原因</td>
	  </@>
	  <@table.tbody datas=failedStds;std>
	    <td class="selectTd">
	        <input type="checkbox" name="stdNo" value="${std.code}"/>
	    </td>
		<td>${std.code}</td>
		<td><@i18nName std/></td>
		<td><@i18nName std.department/></td>
		<td><@i18nName std.firstMajor?if_exists/></td>
		<td><@i18nName std.firstMajorClass?if_exists/></td>
		<td><@bean.message key="${failMsgs[std.code]}"/></td>
	  </@>
  </@>
  <pre>
    1.系统会检测学生的学分上限(如果有学分上限),并更新学生的已选学分
    2.系统会检测任务选课人数上限(如果任务参选),并更新教学任务的上课人数
    3.自动判断修读类别,如果学生已经修过这门课,则会免修不免试.
  </pre>
   <form name="actionForm" method="post" action="courseTakeForTask.do?method=add" onsubmit="return false;">
   <input name="checkConstraint" type="hidden" value="0"/>
   <input name="taskId" type="hidden" value="${RequestParameters['taskId']}"/>
   </form>
 <script>
   var bar = new ToolBar("addBar","添加失败",null,true,true);
   bar.addItem("继续添加","add()");
   bar.addBack("<@msg.message key="action.back"/>");
   function add(){
     submitId(document.actionForm,"stdNo",true);
   }
 </script>
 </body>
<#include "/templates/foot.ftl"/>