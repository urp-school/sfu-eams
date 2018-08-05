<#include "/templates/head.ftl"/>
<#include "/templates/calendarSelect.ftl"/>


    <table class="frameTable_title">
        <tr >
          <form name="calendarForm"  method="post" action="?method=index" onsubmit="return false;">
          <td style="width:35%;"></td>
          <input type="hidden" name="calendar.id" id="calendarId" value="${calendar.id}" />
          <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    </form>
    <table id="bar"></table>
    <form name="taskListForm" id="taskForm" method="post" action="" onsubmit="return false;">
    <table width="100%" id="listTable" class="formTable">
  		<@table.thead>
         	<@table.selectAllTd id="taskid" width="3%" />
         	<@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      		<@table.sortTd id="task.course.code" name="attr.courseNo" width="10%"/>
      		<@table.sortTd id="task.course.name" width="15%" name="attr.courseName"/>
      		<@table.sortTd id="task.courseType.name" width="10%" name="entity.courseType"/>
      		<@table.sortTd id="task.teachClass.name" width="15%" name="entity.teachClass"/>
      		<@table.td width="9%" name="entity.teacher"/>
      		<@table.td width="20%" name="task.arrangeInfo"/>
     	</@>
     <@table.tbody datas=taskList;task>
         	<td ><input type="checkbox" name="taskid" value="${task.id}"/></td>
         	<td >${task.seqNo?if_exists}</td>
         	<td >${task.course.code}</td>
         	<td >${task.course.name}</td>
         	<td ><@i18nName task.courseType/></td>
         	<td >${task.teachClass.name?html}</td>
         	<td >
		      <#assign teachers = task.arrangeInfo.teachers/>
		      <#list teachers as teacher>
		      	${teacher.name}
      		  </#list>
      		</td>
      		<td align="left" >
      		${task.arrangeInfo.digest(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}
      		</td>
  	 </@>
  	 <tr align="center"  class="darkColumn">
	     <td colspan="8">
	       <input type="hidden" name="calendar.id" id="calendarId" value="${calendar.id}" />
           <#--<input type="button" onClick='search(this.form)' value="查看出勤情况" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;-->
	     </td>
	 </tr> 
    </table>
    </form>
    <script>
  		var bar =new ToolBar("bar","教师考勤报表",null,true,true);
  		bar.addItem('查看明细','showDetailList()');
  		bar.addItem('查看出勤情况','search()');
	</script> 
    
	<script>
        function search(){
        	var form=document.getElementById("taskForm");
        	ids = getSelectIds("taskid");
       		if(ids=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       		var calendarId=document.getElementById("calendarId").value;
       		window.open("attendReportTea.do?method=search&taskids="+ids+"&calendarId="+calendarId,"newWindow")
        }
        
        function showDetailList(){
        	var form=document.getElementById("taskForm");
        	ids = getSelectIds("taskid");
       		if(ids=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
        	form.action="?method=showDetailList&taskIds="+ids;
            form.target="_blank";
            form.submit();
        }
	</script>
<#include "/templates/foot.ftl"/> 

  