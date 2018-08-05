<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="studentInfoUpdateSetting.do" method="post" onsubmit="return false;">
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
	   <tr class="darkColumn">
	     <td align="center" colspan="2">学生信息修改开关</td>
	   </tr>
	   <#assign moduleName="AuditStandardManager" />
	   <#if result.auditStandard.isTeachPlanCompleted?exists>
	    <#assign isTeachPlanCompleted=(result.auditStandard.isTeachPlanCompleted?string)>
	    <#else>	    
	   </#if>
	    <input type="hidden" name="auditStandard.isTeachPlanCompleted" value="1"/>
	   </tr>
	   <#include "/pages/selector/courseTypeSelectorBarwithoutAuthority.ftl" />
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <input type="hidden" name="method" value="update" />
	       <input type="hidden" name="auditStandard.id" value="${result.auditStandard.id}" />
	       <input type="button" value="<@bean.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
 </body>
<script language="javascript" >
    function doAction(form){   

     var a_fields = {
        'auditStandard.code':{'l':'<@bean.message key="attr.code"/>', 'r':true, 't':'f_code'},
     	'auditStandard.name':{'l':'<@bean.message key="attr.infoname"/>', 'r':true, 't':'f_name'},
     	'auditStandard.studentType.id':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
     };
     
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
</script>
<#include "/templates/foot.ftl"/>