<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
   <tr>
   		<td>
   			<#assign labInfo><@bean.message key="rationWorkload.modifyRationWorkload"/></#assign>
   			<#include "/templates/back.ftl"/>
   		</td>
   </tr>
   <tr>
    <td>
     <table width="100%" align="center" class="listTable">
          <tr align="center" class="darkColumn">
    		<td colspan="2" align="center"><B><@bean.message key="rationWorkload.modifyRationWorkload"/></B></td>
   		  </tr>		
	   <tr>
	     <td class="grayStyle" width="30%" id="f_rationCn"><@bean.message key="rationWorkload.rationCn"/><font color="red">*</font>:</td>
	     <td class="brightStyle">
	     	<input type="text" name="rationWorkload.rationCn" maxlength="50" style="width:300px;" value="${rationWorkload.rationCn?default('')}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="30%" id="f_rationEn">&nbsp;<@bean.message key="rationWorkload.rationEn"/></td>
	     <td class="brightStyle">
			<input type="text" name="rationWorkload.rationEn" maxlength="120" style="width:300px;" value="${rationWorkload.rationEn?default('')}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="30%" id="f_value">&nbsp;<@bean.message key="rationWorkload.value"/><font color="red">*</font></td>
	     <td class="brightStyle">
			<input type="text" name="rationWorkload.value" maxlength="7" style="width:300px;" value="${rationWorkload.value?default('')}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="30%">&nbsp;<@bean.message key="rationWorkload.createDepartment"/><font color="red">*</font>:</td>
	     <td class="brightStyle"><@htm.i18nSelect datas=departmentList selected=(rationWorkload.department.id)?default('')?string name="rationWorkload.department.id" style="width:300px;"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="30%" id="f_remark">&nbsp;<@bean.message key="rationWorkload.remark"/>:</td>
	     <td class="brightStyle">
	     	<textarea name="rationWorkload.remark" rows="5" cols="30" style="width:300px;">${rationWorkload.remark?if_exists}</textarea>
         </td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <input type="hidden" name="rationWorkload.id" value="${rationWorkload.id?default('')}">
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>  
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   function doAction(form){     
     
     var a_fields = {
          'rationWorkload.rationCn':{'l':'<@bean.message key="rationWorkload.rationCn"/>', 'r':true, 't':'f_rationCn','mx':'100'},
          'rationWorkload.rationEn':{'l':'<@bean.message key="rationWorkload.rationEn"/>', 't':'f_rationEn','mx':'50'},
          'rationWorkload.value':{'l':'<@bean.message key="rationWorkload.value"/>', 'r':true, 't':'f_value','mx':'10','f':'unsignedReal'},
          'rationWorkload.remark':{'l':'<@bean.message key="rationWorkload.createDepartment"/>', 't':'f_remark','mx':'100'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	form.action="rationWorkload.do?method=save";
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>