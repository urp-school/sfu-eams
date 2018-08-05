<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
    <#assign labInfo><B>新增考试信息</B></#assign>
	<#include "/templates/back.ftl"/>
  	<table width="100%" align="center" class="formTable">
     <form name="electParamsForm" action="examTake.do?method=saveExamTake" method="post" onsubmit="return false;">
        <input type="hidden" name="examTypeId" value="${examTypeId?if_exists}"/>
         <input type="hidden" name="calendarId" value="${calendarId?if_exists}"/>
       <tr>
         <td class="title" width="25%" id="f_stdCode">&nbsp;学生学号<font color="red">*</font>：</td>
	     <td  colspan="3"> 
	       ${(courseTake.student.code)?if_exists}
	     </td>
       </tr>
       <#list courseTakes as courseTake>
       <tr>
         <input type="hidden" name="cousrseId${courseTake_index+1}" value="${courseTake.id?if_exists}">
        <td class="title" width="25%" id="f_stdCode">&nbsp;课程代码${courseTake_index+1}<font color="red">*</font>：</td>
	     <td>
	      ${(courseTake.task.course.code)?if_exists}
	     </td>
	   	 <td class="title" width="25%" id="f_courseCode">&nbsp;课程名称${courseTake_index+1}<font color="red">*</font>：</td>
	     <td><@i18nName (courseTake.task.course)?if_exists/></td>
       </tr>
       </#list>
	   <tr class="darkColumn">
	     <td colspan="4" align="center">
	       <input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle"/>&nbsp;
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