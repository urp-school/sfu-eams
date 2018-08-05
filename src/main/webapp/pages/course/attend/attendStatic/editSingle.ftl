<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src='dwr/interface/teachPlanService.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script> 
<body>
	<#assign labInfo>考勤信息修改</#assign>
	<#include "/templates/back.ftl"/>
    <table width="100%"  class="formTable">
      	<form action="attendStatic.do?method=save" name="planForm" method="post" onsubmit="return false;">
       	<tr class="darkColumn">
         	<td align="left" colspan="6"><B>考勤信息修改</B></td>
       	</tr>
       	<tr >
       	
         	<td align="left" colspan="6">
         	<input type="hidden" name="attendStatic.id" value="${(attendStatic.id)!}"/>
         	学生学号:${(attendStatic.student.code)!}&nbsp;&nbsp;
         	学生姓名:${(attendStatic.student.name)!}&nbsp;&nbsp;
         	学生班级:
         	<#assign adminClasses = attendStatic.student.adminClasses/>
      		<#list adminClasses as adminClass>
      			${adminClass.name}
      		</#list>
         	</td>
       	</tr>
	   	<tr>
	   		<td width="10%">考勤日期:<font color="red">*</font></td>
	   		<td>
	   		<input style="width:120px" maxlength="20" type="text" name="attendStatic.attenddate" value="${(attendStatic.attenddate!)?string('yyyy-MM-dd')}" />
	   		</td>
	   		<td width="10%">考勤时间:<font color="red">*</font></td>
	   		<td>
	   		<input style="width:120px" maxlength="5" type="text" name="attendStatic.attendtime" value="${(attendStatic.attendtime)!}" />
	   		</td>
	   		<td width="10%">考勤类型:<font color="red">*</font></td>
	   		<td>
	   		<select name="attendStatic.attendtype">
	   		<option value="1" [#if attendStatic.attendtype==0]selected="selected"[/#if]>出勤</option>
	   		<option value="2" [#if attendStatic.attendtype==1]selected="selected"[/#if]>缺勤</option>
	   		<option value="3" [#if attendStatic.attendtype==2]selected="selected"[/#if]>迟到</option>
	   		<option value="4" [#if attendStatic.attendtype==2]selected="selected"[/#if]>早退</option>
	   		<option value="5" [#if attendStatic.attendtype==2]selected="selected"[/#if]>请假</option>
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
         	<td width="3%"><input type="radio" name="takeid" value="0" checked="checked"/></td>
         	<@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      		<@table.sortTd id="task.course.code" name="attr.courseNo" width="10%"/>
      		<@table.sortTd id="task.course.name" width="15%" name="attr.courseName"/>
      		<@table.sortTd id="task.courseType.name" width="10%" name="entity.courseType"/>
      		<@table.sortTd id="task.teachClass.name" width="15%" name="entity.teachClass"/>
      		<@table.td width="9%" name="entity.teacher"/>
      		<@table.td width="20%" name="task.arrangeInfo"/>
         	</tr>
         	</thead>
         	<tbody >
         	<tr>
         	<td ><input type="hidden" name="attendStatic.task.id" value="${attendStatic.task.id}"/></td>
         	<td >${attendStatic.task.seqNo?if_exists}</td>
         	<td >${attendStatic.task.course.code}</td>
         	<td >${attendStatic.task.course.name}</td>
         	<td ><@i18nName attendStatic.task.courseType/></td>
         	<td >${attendStatic.task.teachClass.name?html}</td>
         	<td >
		      <#assign teachers = attendStatic.task.arrangeInfo.teachers/>
		      <#list teachers as teacher>
		      	${teacher.name}
      		  </#list>
      		</td>
      		<td align="left" >
      		${attendStatic.task.arrangeInfo.digest(attendStatic.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}
      		</td>
         	</tr>
         	</tbody>
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
  		form.submit();
  	}
 	</script>
</body>
<#include "/templates/foot.ftl"/>