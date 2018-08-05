<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="auditStandardOperation.do" method="post" action="" onsubmit="return false;">
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="attr.graduate.graduateAuditStandard"/></td>
	   </tr>
	   <#assign moduleName="AuditStandardManager" />
		<tr>
			<td id="f_name" class="grayStyle">&nbsp;<@bean.message key="attr.infoname" /><font color="red">*</font>:</td>
			<td class="brightStyle"><input type="text" name="auditStandard.name" value="${result.auditStandard.name?if_exists}" maxLength="50"></td>
		</tr>
	   	<tr>
		    <td class="grayStyle" width="25%" id="f_studentType">&nbsp;<@bean.message key="entity.studentType"/><font color="red">*</font>：</td>
		    <td class="brightStyle">
		    	<select name="auditStandard.studentType.id" style="width:300px;" >
		    		<#list stdTypeList?if_exists as stdType>
						<option value="${stdType.id}" <#if (result.auditStandard.studentType?if_exists.id?exists)&&(result.auditStandard.studentType.id==stdType.id)>selected</#if>><@i18nName stdType /></option>
					</#list>
				</select>
		    </td>
	   	</tr>
	   <#if result.auditStandard.isTeachPlanCompleted?exists>
	    <#assign isTeachPlanCompleted=(result.auditStandard.isTeachPlanCompleted?string)>
	    <#else>	    
	   </#if>
	    <input type="hidden" name="auditStandard.isTeachPlanCompleted" value="1"/>
	   </tr>
	   <#--选择培养计划中无需审核的课程类别-->
	   <#assign courseTypeNeeded = false />
       <#assign courseTypeId = "disauditCourseTypeId" />
	   <#assign courseTypeDescriptions = "disauditCourseTypeDescriptions" />
	   <#assign courseTypeIdValue = "," />
	   <#assign courseTypePurpose><@bean.message key="attr.graduate.disauditCourseType"/></#assign>
	   <#assign courseTypeTDid="f_disauditCourseType"/>
	   <#assign courseTypeDescriptionsValue = "" />
	   
	   <#list result.auditStandard?if_exists.disauditCourseTypeSet?if_exists as courseType>
	   
	   <#if courseType_has_next>
	     <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
	     <#if courseType.name?exists>
	     <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + "," />
	     </#if>
	   <#else>
	   	 <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
	     <#if courseType.name?exists>
	     <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
	     </#if>
	   </#if>
	   </#list>
	   <#include "/pages/selector/courseTypeSelectorBarwithoutAuthority.ftl" />
	   <#--选择培养计划中多出学分可冲抵任意选修课的课程类别-->
	   <#assign courseTypeNeeded = false />
       <#assign courseTypeId = "convertableCourseTypeId" />
	   <#assign courseTypeDescriptions = "convertableCourseTypeDescriptions" />
	   <#assign courseTypeIdValue = "," />
	   <#assign courseTypePurpose>多出学分可冲抵任意选修课的课程类别</#assign>
	   <#assign courseTypeTDid="f_convertableCourseType"/>
	   
	   <#assign courseTypeDescriptionsValue = "" />
	   
	   <#list result.auditStandard?if_exists.convertableCourseTypeSet?if_exists as courseType>
	   
	   <#if courseType_has_next>
	     <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
	     <#if courseType.name?exists>
	     <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + "," />
	     </#if>
	   <#else>
	   	 <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
	     <#if courseType.name?exists>
	     <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
	     </#if>
	   </#if>
	   </#list>
	   <#include "/pages/selector/courseTypeSelectorBarwithoutAuthority.ftl" />
	   <tr>
	    <td class="grayStyle" width="25%" id="f_remark">&nbsp;<@bean.message key="attr.remark"/>：</td>
	    <td class="brightStyle">
	     <textarea name="auditStandard.remark" cols="55" rows="3">${result.auditStandard.remark?if_exists}</textarea>
	    </td>
	   </tr>
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