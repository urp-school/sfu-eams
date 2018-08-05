<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
   <script language="javascript" >
   function doAction(form){
     var a_fields = {
          <#list result.collegeList?if_exists as college>
          		'rationWorkloadId${college_index}':{'l':'${college.name}','r':true, 't':'f_${college_index}'},
          </#list>
          		'studentTypeId':{'l':'<@bean.message key="field.configRationWorkload.configRationWorkload"/>','r':true, 't':'f_studentTypeId'}
          };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	form.action="configRationWorkloadAction.do?method=doConfig";
        form.submit();
     }
   }
  </script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
   <tr>
   		<td>
   		<#assign labInfo><@bean.message key="field.configRationWorkload.configRationWorkload"/></#assign>
   		<#include "/templates/help.ftl"/>
   		</td>
   </tr>
   <tr>
   		<td>
   			<table width="100%" align="center" class="listTable">
   				<tr class="darkColumn" align="center">
   					<td colspan="2">
   						<@bean.message key="field.configRationWorkload.configRationWorkload"/>
   					</td>
   				</tr>
   				<tr >
   					<td class="grayStyle" align="center" width="30%" id="f_studentTypeId">
   						<@bean.message key="field.configRationWorkload.selectStudentType"/><font color="red">*</font>
   					</td>
   					<td class="brightStyle" align="left">
   					<select name="studentTypeId" style="width:300px;" >
   						<option value=""><@bean.message key="field.configRationWorkload.selectStudentType"/></option>
   						<#list result.studentTypeList?if_exists as studentType>
   							<option value="${studentType.id}">${studentType.name}</option>
   						</#list>
   					</select>
   					</td>
   				</tr>
   				<#list result.collegeList?if_exists as college>
   					<tr>
   						<td class="grayStyle" align="center" width="30%" id="f_${college_index}">
   							${college.name}<font color="red">*</font>
   						</td>
   						<td class="brightStyle" align="left">
   							<select name="rationWorkloadId${college_index}" style="width:300px;">
   										<option value=""><@bean.message key="field.configRationWorkload.selectRationWorkload"/></option>
   									<#list result.rationWorkloadList?if_exists as rationWorkload>
   										<option value="${rationWorkload.id}">${rationWorkload.rationCn}&nbsp;&nbsp;${rationWorkload.value}&nbsp;&nbsp;${rationWorkload.department.name}</option>
   									</#list>
   							</select>
   						</td>
   					</tr>
   				</#list>
   				<tr class="darkColumn" align="center">
   					<td colspan="2">
   						<input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       				<input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
   					</td>
   				</tr>
   			</table>
   		</td>
   </tr>
   
   </table>
</body>
<#include "/templates/foot.ftl"/>