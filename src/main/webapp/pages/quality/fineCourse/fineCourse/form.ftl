<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>精品课程信息</#assign>  
<#include "/templates/back.ftl"/>   
     <table width="90%" align="center"  class="formTable">
       <tr class="darkColumn">
         <td align="left" colspan="4"><B>精品课程</B></td>
       </tr>
	   <form action="fineCourse.do?method=save" name="fineCourseForm" method="post" onsubmit="return false;">
	   <@searchParams/>
	   <input type="hidden" name="fineCourse.id" value="${(fineCourse.id)?default('')}"/>
	   <tr>
	     <td  id="f_courseName" class="title"><font color="red">*</font><@bean.message key="attr.courseName"/>:</td>
	     <td colspan="3"><input type="text" name="fineCourse.courseName" value="${(fineCourse.courseName)?if_exists}" maxlength="50"></td>
	   </tr>
	   <tr>
	     <td class="title">级别:</td>
	     <td><@htm.i18nSelect datas=levels name="fineCourse.level.id" selected=(fineCourse.level.id)?default('')?string/></td>
	     <td class="title"><@msg.message key="entity.department"/>:</td>
	     <td><@htm.i18nSelect datas=departmentList name="fineCourse.department.id" selected=(fineCourse.department.id)?default('')?string>
	         <option value=""></option>
	         </@>
	     </td>
	   </tr>
       <tr>
	     <td class="title" >批准年度:</td>
   	     <td colspan="3"><input name="fineCourse.passedYear" value="${fineCourse.passedYear?default("")}" maxlength="4"></td>
	   </tr> 
	   <tr>
	     <td class="title" >负责人:</td>
   	     <td colspan="3">
   	       <input type="hidden" name="fineCourse.chargeIds" value="${(fineCourse.chargeIds)?default('')}"/>
	       <input type="text" name="chargeNames" style="border:0 solid #000000;width:50%" readOnly="true" value="${(fineCourse.chargeNames)?default('')}"/>
           <input type="button"  class="buttonStyle" value="<@bean.message key="action.clear"/>" onClick="this.form['fineCourse.chargeIds'].value='';this.form['chargeNames'].value='';"/>               
           <input type="button"  class="buttonStyle" value="<@bean.message key="action.add"/>" onClick="addCharge('fineCourse.chargeIds','chargeNames');"/><br>
   	       <select id="teachDepartment" style="width:150px">
   	         <option value=""></option>
	       </select>
   	       <select id="teacher" style="width:90px">
   	         <option value=""></option>
	       </select>
	     </td>
	   </tr> 
	   <tr >
	     <td  class="title" id="f_remark"><@bean.message key="attr.remark"/>:</td>
	     <td colspan="3"  >
	       <textarea name="fineCourse.remark" cols="40" rows="5">${(fineCourse.remark)?if_exists}</textarea>	              
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
         'fineCourse.courseName':{'l':'<@bean.message key="attr.courseName"/>', 'r':true, 't':'f_courseName'},
         'fineCourse.remark':{'l':'<@bean.message key="attr.remark"/>', 'r':false, 't':'f_remark','mx':200}
        };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {
        form.action="fineCourse.do?method=save";
        form.submit();
       }
   }
   function addCharge(hiddenTeacherIds,hiddenTeacherNames){
       var teacher = document.getElementById('teacher');
       if(teacher.value!=""){
           if(form[hiddenTeacherIds].value==""){
             form[hiddenTeacherIds].value=","+teacher.value +",";
             form[hiddenTeacherNames].value=DWRUtil.getText('teacher');
           }else if(form[hiddenTeacherIds].value.indexOf(","+teacher.value+",")==-1){
              form[hiddenTeacherIds].value+=teacher.value +",";
              form[hiddenTeacherNames].value+=" "+DWRUtil.getText('teacher');
           }
       }
   }
 </script>
  <#include "/templates/departTeacher2Select.ftl"/>
 </body> 
</html>