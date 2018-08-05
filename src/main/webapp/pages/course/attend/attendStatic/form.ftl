<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script> 
<body>
	<#assign labInfo>考勤信息新增</#assign>
	<#include "/templates/back.ftl"/>
    <table width="100%"  class="formTable">
      	<form action="attendStatic.do?method=save" name="planForm" method="post" onsubmit="return false;">
       	<tr class="darkColumn">
         	<td align="left" colspan="6"><B>考勤信息新增</B></td>
       	</tr>
       	<tr >
       	
         	<td align="left" colspan="6">
         	学生学号:${student.code!}&nbsp;&nbsp;
         	学生姓名:${student.name!}&nbsp;&nbsp;
         	学生班级:
         	<#assign adminClasses = student.adminClasses/>
      		<#list adminClasses as adminClass>
      			${adminClass.name!}
      		</#list>
         	</td>
       	</tr>
	   	<tr>
	   		<td width="10%">考勤日期:<font color="red">*</font></td>
	   		<td>
	   		<input style="width:120px" id="attenddate" maxlength="20" type="text" onfocus="calendar()" name="attendStatic.attenddate" value="" />&nbsp;<span style="font-size:12px;">(例：2013-04-16)</span>
	   		</td>
	   		<td width="10%">考勤时间:<font color="red">*</font></td>
	   		<td>
	   		<input style="width:120px" id="attendtime" maxlength="5" type="text" name="attendStatic.attendtime" value="" />&nbsp;<span style="font-size:12px;">(例：12:00)</span>
	   		</td>
	   		<td width="10%">考勤类型:<font color="red">*</font></td>
	   		<td>
	   		<select name="attendStatic.attendtype">
		   		<option value="1" selected="selected">出勤</option>
		   		<option value="2" >缺勤</option>
		   		<option value="3" >迟到</option>
		   		<option value="4" >早退</option>
		   		<option value="5" >请假</option>
	   		</select>
	   		</td>
	   	</tr>
	   	
       	<tr >
         	<td align="left" colspan="6">
         	<table width="100%" class="formTable">
         	<thead>
         	<tr>
         	<td colspan="8">教学任务:<font color="red">*</font>
         	</td>
         	</tr>
         	<tr>
         	<td width="3%"><input type="radio" id="takeida" name="takeida" value="0" checked="checked"/></td>
         	<@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      		<@table.sortTd id="task.course.code" name="attr.courseNo" width="10%"/>
      		<@table.sortTd id="task.course.name" width="15%" name="attr.courseName"/>
      		<@table.sortTd id="task.courseType.name" width="10%" name="entity.courseType"/>
      		<@table.sortTd id="task.teachClass.name" width="15%" name="entity.teachClass"/>
      		<@table.td width="9%" name="entity.teacher"/>
      		<@table.td width="20%" name="task.arrangeInfo"/>
         	</tr>
         	</thead>
         	<@table.tbody datas=takes;take>
         	<td ><input type="radio" name="takeid" id="takeid" value="${take.id!}" /></td>
         	<td >${take.task.seqNo?if_exists}</td>
         	<td >${take.task.course.code!}</td>
         	<td >${take.task.course.name!}</td>
         	<td ><@i18nName take.task.courseType/></td>
         	<td >${take.task.teachClass.name?html}</td>
         	<td >
		      <#assign teachers = take.task.arrangeInfo.teachers/>
		      <#list teachers as teacher>
		      	${teacher.name}
      		  </#list>
      		</td>
      		<td align="left" >
      		${take.task.arrangeInfo.digest(take.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}
      		</td>
         	</@>
         	</table>
         	
         	</td>
       	</tr>
       	<tr align="center"  class="darkColumn">
	     <td colspan="4">
           <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
           <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
	     </td>
	   </tr> 
	   	</form> 
	</table>
  	<script language="javascript">
  	function save(form){
  		var attenddate = document.getElementById("attenddate").value;
  		var attendtime = document.getElementById("attendtime").value;
  		var takeids = document.getElementsByName("takeid");
  		if(attenddate==""){return alert("请输入考勤日期");}
  		if(attendtime==""){return alert("请输入考勤时间");}
  		var b=false;
  		for(var i=0;i<takeids.length;i++){
  			if(takeids[i].checked)
  			b=true;
  		}
  		if(!b){return alert("请选择一条任务");}
  		form.submit();
  	}
 	</script>
</body>
<#include "/templates/foot.ftl"/>