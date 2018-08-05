<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>听课信息</#assign>  
<#include "/templates/back.ftl"/>   
     <table width="90%" align="center"  class="formTable">
	   <form action="fineCourse.do?method=save" name="fineCourseForm" method="post" onsubmit="return false;">
	   <@searchParams/>
	   <input type="hidden" name="lessonCheck.id" value="${(lessonCheck.id)?default('')}"/>
	   <tr>
		   <td  align="right" >学生类别</td>
		  <td align="bottom" class="title" style="width:100px;">
		      <select id="stdType" name="studentType.id" style="width:200px;">               
		        <option value="${studentType.id}"></option>
		      </select>
		   </td>
	   </tr>
	   <tr> 
		   <td  align="right" class="title"><@bean.message key="attr.year2year"/></td>
		   <td style="width:100px;">
		     <select id="year" name="calendar.year"  style="width:200px;">                
		        <option value="${calendar.year}"></option>
		      </select>
	   	</td>
     </tr>
     <tr>
        <td  align="right" class="title"><@bean.message key="attr.term"/></td>
	    <td style="width:60px;">     
	     <select id="term" name="calendar.term" style="width:200px;">
	        <option value="${calendar.term}"></option>
	      </select>
	   </td>
     </tr>
   

	   <tr>
	     <td  id="f_courseName" class="title"><font color="red">*</font>课程名称:</td>
	     <td colspan="3">
	      <input type="hidden" name="lessonCheck.task.id" value="${lessonCheck.task.id}" />
	      <@i18nName lessonCheck.task.course/>
	     </td>
	   </tr>
	    <tr>
	     <td  id="f_courseName" class="title"><font color="red">*</font>老师:</td>
	     <td colspan="3">
	      <@htm.i18nSelect datas=lessonCheck.task.arrangeInfo.teachers selected="" name="teacher.id" style="width:200px;" />
	     </td>
	   </tr>
	   <tr>
	     <td  id="f_checkAt" class="title"><font color="red">*</font>上课时间:</td>
	     <td colspan="3">
	     	<input type="text" style="width:200px" maxlength="10" name="lessonCheck.checkAt" value='<#if (lessonCheck.checkAt)?exists>${(lessonCheck.checkAt)?string("yyyy-MM-dd")}</#if>' onfocus="calendar()"/>
	     </td>
	   </tr>
	    <tr>
	     <td  id="f_checkRoom" class="title"><font color="red">*</font>上课地点:</td>
	     <td colspan="3">
	     	 	<select name="classRoomId" style="width:250px">
		        <#list (lessonCheck.task.arrangeInfo.activities) as activities>
			       <option value="${activities.room.id}"<#if (lessonCheck.checkRoom.id)?default("")?string == (activities.id)?string>selected</#if>>${(activities.room.name)?if_exists}</option>
			    </#list>
	     	</select>
	     </td>
	   </tr>
	    <tr>
	     <td  id="f_lessonCheckType" class="title"><font color="red">*</font>听课类别:</td>
	     <td colspan="3">
	     	<select name="lessonCheck.lessonCheckType.id" style="width:250px">
		        <#list lessonCheckTypes as lessonCheckType>
			       <option value="${lessonCheckType.id}"<#if (lessonCheck.lessonCheckType.id)?default("")?string == (lessonCheckType.id)?string>selected</#if>><@i18nName lessonCheckType/></option>
			    </#list>
	     	</select>
	     </td>
	   </tr>
	   <tr>
	     <td  id="f_checkers" class="title"><font color="red">*</font>听课对象:</td>
	     <td colspan="3">
	     	<textarea name="lessonCheck.checkers" cols="50" rows="8" >${(lessonCheck.checkers)?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr >
	     <td colspan="4" align="center" class="darkColumn">
           <button onClick='save(this.form)'><@bean.message key="system.button.submit"/></button>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>"  class="buttonStyle"/> 
	     </td>
	   </tr>
	   </form> 
     </table>
   <script language="javascript" > 
     var form=document.fineCourseForm;
     function reset(){
       	   document.fineCourseForm.reset();
     }
     function save(form){
        var a_fields = {
         'lessonCheck.checkAt':{'l':'上课时间', 'r':true, 't':'f_checkAt'},
         'lessonCheck.checkers':{'l':'听课对象', 'r':true, 't':'f_checkers','mx':200}
        };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {
        form.action="lessonCheck.do?method=save";
        form.submit();
       }
   }
 </script>
 </body> 
</html>
<#include "/templates/calendarSelect.ftl"/>