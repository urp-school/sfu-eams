<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY>
	<table id="taskBar"></table>
	<script>
	  var bar=new ToolBar("taskBar","排考人数调整",null,true,true);
	  bar.addItem("<@msg.message key="action.save"/>","save()","save.gif");
	  <!--FIXME-->
	  bar.addItem("添加其他空闲教室","freeRoomList()");
	  bar.addBack("<@msg.message key="action.back"/>");
	  function freeRoomList(){
	    var form = document.activityForm;
	    form.action="examArrange.do?method=freeRoomList";
	    form.submit();
	  }
	</script> 
	<table  class="frameTable_title">
		<form name="activityForm" method="post" action="examArrange.do?method=save" onsubmit="return false;">
     	<tr>
       		<td><@msg.message key="attr.taskNo"/>:</td><td>${task.seqNo}</td>
       		<td><@msg.message key="attr.courseName"/>:</td><td><@i18nName task.course/></td>
       		<td>授课教师:</td><td><@getTeacherNames task.arrangeInfo.teachers/></td>
       		<td>考试人数:</td><td id="count">${task.teachClass.getExamTakes(examType)?size}</td>
       		<#assign maxlength = (task.teachClass.stdCount?default(0))?string?length>
     	</tr>
	</table>
	<@table.table width="100%">
	    <input type="hidden" name="params" value="${RequestParameters['params']}"/>
	    <input type="hidden" name="task.id" value="${task.id}"/>
	    <input type="hidden" name="examType.id" value="${examType.id}"/>
	    <input type="hidden" name="examActivity.id" value="${(examActivites?first.id)?default("")}"/>
		<@table.thead>
			<@table.td text="序号"/>
			<@table.td text="占用教室"/>
			<@table.td text="容纳考试人数上限"/>
			<@table.td text="实际排考人数<font color=\"red\">*</font>"/>
		</@>
		<#assign examActivitySize = examActivites?size/>
		<@table.tbody datas=examActivites;activity,activity_index>
			<td id="f_size${activity_index+1}" style="text-align:center">${activity_index+1}</td>
       		<td><@i18nName activity.room?if_exists/></td>
       		<td id="stdCount_${activity.id}">${(activity.room.capacityOfExam)?if_exists}</td>
       		<td><input name="size${activity.id}" style="width:60px" value="${activity.examTakes?size}" maxlength="${maxlength}"/></td>
		</@>
	</@>
  	<pre>　提示：调整人数时,不会将已有的排考结果删除,仅仅调整学生与教室之间占用关系<br>　　　　将考试人数为0的排考,将会删除对应教室的排考结果,但不删除应考记录.</pre>
  	<#if (noExamCourseTakes?size > 0)>
  		<@table.table width="100%">
    		<tr class="darkColumn">
    			<td colspan="6">未参加考试的学生名单</td>
    		</tr>
    		<input type="hidden" name="noTakedStdCount" value="${noExamCourseTakes?size}">
    	  	<@table.thead>
	  			<@table.td text="序号"/>
	  			<@table.td name="attr.stdNo"/>
	  			<@table.td name="attr.personName"/>
	  			<@table.td name="entity.department"/>
	  			<@table.td text="考试教室"/>
	  		</@>
	  		<@table.tbody datas=noExamCourseTakes;courseTake,courseTake_index>
		       	<td>${courseTake_index+1}</td>
		       	<td>${(courseTake.student.code)?if_exists}</td>
		       	<td><@i18nName courseTake.student/></td>
		       	<td><@i18nName courseTake.student.department/></td>
		       	<td>
		          	<input type="hidden" name="courseTakeId${courseTake_index}" value="${courseTake.id}">
		          	<select name="examActivityId${courseTake_index}">
		             	<option value="">...</option>
		              	<#list unsaturatedActivites as activity>
		              		<option value="${activity.id}"><@i18nName activity.room/></option>
		              	</#list>
		          	</select>
		       	</td>
	  		</@>
  		</@>
  	</#if>
</form>

<script>
	var form = document.activityForm;
   	function save() {
   		var a_fields = {
   			<#list examActivites as activity>
   				'size${activity.id}':{'l':'第${activity_index+1}条记录实际排考人数', 'r':true, 't':'f_size${activity_index+1}', 'f':'unsigned'}<#if activity_has_next>,</#if>
   			</#list>
   		};
   		
   		var v = new validator(form, a_fields, null);
   		if (v.exec()) {
   			if (<#list examActivites as activity>parseInt(form["size${activity.id}"].value) > parseInt($("stdCount_${activity.id}").innerHTML)<#if activity_has_next> || </#if></#list>) {
   				alert("实际排考人数不能超过容纳考试人数上限。");
   				return;
   			}
   			if ((<#list examActivites as activity>parseInt(form["size${activity.id}"].value)<#if activity_has_next> + </#if></#list>) != parseInt($("count").innerHTML)) {
   				alert("调整前后人数不一致");
   				return;
   			}
   			form.submit();
   		}
   	}
</script>
</body>
<#include "/templates/foot.ftl"/>
