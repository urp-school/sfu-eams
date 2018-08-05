<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>" ></script>
<body>
    <#assign labInfo><B>新增考试记录</B></#assign>
	<#include "/templates/back.ftl"/>
  	<table width="100%" align="center" class="formTable">
     <form name="electParamsForm" action="examTake.do?method=editNextForm" method="post" onsubmit="return false;">
       <input type="hidden" name="courseTask.task.calendar.id" value="${RequestParameters['calendar.id']}"/>
        <input type="hidden" name="examType.id" value="${RequestParameters['take.examType.id']}"/>
       <tr>
         <td class="title" width="25%" id="f_stdCode">&nbsp;学生学号<font color="red">*</font>：</td>
	     <td>
	       <input name="courseTask.student.code" value="${(courseTask.student.code)?if_exists}"/>
	     </td>
       </tr>
       <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码1：</td>	
	     <td >
	       <input name="courseCode1" value="${RequestParameters['courseCode1']?if_exists}"/>
	     </td>
       </tr>  
          <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码2：</td>	
	     <td >
	       <input name="courseCode2" value="${RequestParameters['courseCode2']?if_exists}"/>
	     </td>
       </tr>  
         <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码3：</td>	
	     <td >
	       <input name="courseCode3" value="${RequestParameters['courseCode3']?if_exists}"/>
	     </td>
       </tr>  
         <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码4：</td>	
	     <td >
	       <input name="courseCode4" value="${RequestParameters['courseCode4']?if_exists}"/>
	     </td>
       </tr>  
         <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码5：</td>	
	     <td >
	       <input name="courseCode5" value="${RequestParameters['courseCode5']?if_exists}"/>
	     </td>
       </tr>  
         <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码6：</td>	
	     <td >
	       <input name="courseCode6" value="${RequestParameters['courseCode6']?if_exists}"/>
	     </td>
       </tr>  
         <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码7：</td>	
	     <td >
	       <input name="courseCode7" value="${RequestParameters['courseCode7']?if_exists}"/>
	     </td>
       </tr>    <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码8：</td>	
	     <td >
	       <input name="courseCode8" value="${RequestParameters['courseCode8']?if_exists}"/>
	     </td>
       </tr>   
        <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码9：</td>	
	     <td >
	       <input name="courseCode9" value="${RequestParameters['courseCode9']?if_exists}"/>
	     </td>
       </tr>  
       <tr>
	   	 <td class="title" width="25%" >&nbsp;课程代码10：</td>	
	     <td >
	       <input name="courseCode10" value="${RequestParameters['courseCode10']?if_exists}"/>
	     </td>
       </tr>  
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <input type="button" value="下一步" name="saveButton" onClick="save(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
   </form>
  </table>
  <script language="javascript">
   function save(form){
     var a_fields = {
         'courseTask.student.code':{'l':'学生学号','r':true,'t':'f_stdCode'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>