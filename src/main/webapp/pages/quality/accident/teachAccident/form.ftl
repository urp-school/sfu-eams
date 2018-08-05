<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body>
 <#assign labInfo><@bean.message key="field.teachAccident.addTeachAccident"/></#assign>
 <#include "/templates/back.ftl"/>
     <table width="100%" align="center" class="formTable">
       <form name="commonForm" method="post" action="" onsubmit="return false;">
       <input name="teachAccident.id" value="${teachAccident.id?default('')}" type="hidden"/>
       <@searchParams/>
       <input name="teachAccident.task.id" value="${teachAccident.task.id}" type="hidden"/>
       <tr align="center" class="darkColumn">
    		<td colspan="2" align="center"><B><@bean.message key="field.teachAccident.addTeachAccident"/></B></td>
   	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_courseId">&nbsp;<@bean.message key="entity.course"/>:</td>
	     <td><@i18nName teachAccident.task.course/></td>
	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_teacherId"><font color="red">*</font>&nbsp;<@bean.message key="field.teachAccident.selectTeacher"/>:</td>
	     <td>
			<@htm.i18nSelect datas=teachAccident.task.arrangeInfo.teachers selected="" name="teachAccident.teacher.id" style="width:300px;" />
         </td>
	   </tr>
	   <tr>
	   		<td class="title" width="30%" id="f_occurTime"><font color="red">*</font>&nbsp;<@bean.message key="field.teachAccident.occurTime"/>:</td>
	   		<td><input type="text" name="teachAccident.occurAt" maxlength="10" style="width:300px;" value="<#if teachAccident.occurAt?exists>${teachAccident.occurAt?string('')}</#if>" onfocus="calendar()"></td>
	   </tr>
	   <tr>
	   		<td class="title" width="30%" id="f_simpleDescibe"><font color="red">*</font>&nbsp;<@bean.message key="field.teachAccident.accidentDes"/>:</td>
	   		<td><textarea name="teachAccident.description" rows="5" cols="30" style="width:300px;">${teachAccident.description?default('')}</textarea></td>
	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_remark">&nbsp;<@bean.message key="field.teachAccident.remark"/>:</td>
	     <td ><textarea name="teachAccident.remark" rows="4" cols="30" style="width:300px;">${teachAccident.remark?default('')}</textarea></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <button onClick="save(this.form)"><@bean.message key="system.button.submit"/></button>&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
      </form>
     </table>
  <script language="javascript" >
   function save(form){
     var a_fields = {
          'teachAccident.occurAt':{'l':'<@bean.message key="field.teachAccident.occurTime"/>', 'r':true, 't':'f_occurTime'},
          'teachAccident.teacher.id':{'l':'<@bean.message key="entity.teacher"/>', 'r':true, 't':'f_teacherId'},          
          'teachAccident.description':{'l':'<@bean.message key="field.teachAccident.accidentDes"/>', 'r':true, 't':'f_simpleDescibe','mx':250},
          'teachAccident.remark':{'l':'<@bean.message key="field.teachAccident.remark"/>', 't':'f_remark','mx':200}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.action="teachAccident.do?method=save";
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>