<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
	<table id="bar"></table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
   		<form name="commonForm" action="" method="post">
		<tr height="28">
			<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff"><B><#if (result.auditStandard.id)?exists><@msg.message key="attr.graduate.modifyGraduateAudit"/><#else><@msg.message key="attr.graduate.addNewGraduateAudit"/></#if></B></td>
		</tr>
		<tr>
			<td>
				<table width="80%" align="center" class="listTable">
					<tr class="darkColumn">
						<td align="center" colspan="2"><@bean.message key="attr.graduate.graduateAuditStandard"/></td>
					</tr>
	   				<#assign moduleName="AuditStandardManager" />
					<tr>
						<td id="f_name" class="grayStyle">&nbsp;<@bean.message key="attr.infoname" /><font color="red">*</font>:</td>
						<td class="brightStyle"><input type="text" name="auditStandard.name" maxLength="50" value="${(result.auditStandard.name)?if_exists}"></td>
					</tr>
	   				<tr>
					    <td class="grayStyle" width="25%" id="f_studentType">&nbsp;<@bean.message key="entity.studentType"/><font color="red">*</font>：</td>
					    <td class="brightStyle"><@htm.i18nSelect datas=stdTypeList selected=(result.auditStandard.studentType.id?string)?default("") name="auditStandard.studentType.id"/></td>
	   				</tr>
	   				<tr>
	   					<td class="grayStyle">&nbsp;指定任意选修课：</td>
	   					<td><@htm.i18nSelect datas=courseTypes selected=(result.auditStandard.publicCourseType.id?string)?default("93") name="auditStandard.publicCourseType.id"/></td>
	   				</tr>
	   				<input type="hidden" name="auditStandard.isTeachPlanCompleted" value="1"/>
	   				<input type="hidden" name="auditStandard.isDualConvert" value="${(auditStandard.isDualConvert?string('1', '0'))?default('0')}"/>
	   				<tr>
				   		<#--选择培养计划中无需审核的课程类别-->
				   		<#assign courseTypeNeeded = false/>
			       		<#assign courseTypeId = "disauditCourseTypeId"/>
				   		<#assign courseTypeDescriptions = "disauditCourseTypeDescriptions"/>
				   		<#assign courseTypeIdValue = ","/>
				   		<#assign courseTypePurpose><@bean.message key="attr.graduate.disauditCourseType"/></#assign>
				   		<#assign courseTypeTDid="f_disauditCourseType"/>
				   		<#assign courseTypeDescriptionsValue = ""/>
	   
	   					<#list result.auditStandard?if_exists.disauditCourseTypes?if_exists as courseType>
	   						<#if courseType_has_next>
	     						<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
	     						<#if courseType.name?exists>
	     							<#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
	     						</#if>
		   					<#else>
	   	 						<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
								<#if courseType.name?exists>
									<#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string/>
								</#if>
		   					</#if>
	   					</#list>
	   					<#include "/pages/selector/courseTypeSelectorBarwithoutAuthority.ftl"/>
	   					<#--选择培养计划中多出学分可冲抵任意选修课的课程类别-->
				   		<#assign courseTypeNeeded = false/>
			       		<#assign courseTypeId = "convertableCourseTypeId"/>
				   		<#assign courseTypeDescriptions = "convertableCourseTypeDescriptions"/>
				   		<#assign courseTypeIdValue = ","/>
				   		<#assign courseTypePurpose>多出学分可冲抵任意选修课的课程类别</#assign>
				   		<#assign courseTypeTDid="f_convertableCourseType"/>
   
   						<#assign courseTypeDescriptionsValue = ""/>
   
   						<#list result.auditStandard?if_exists.convertableCourseTypes?if_exists as courseType>
   							<#if courseType_has_next>
     							<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
     							<#if courseType.name?exists>
     								<#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
     							</#if>
   							<#else>
   	 							<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
     							<#if courseType.name?exists>
     								<#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
     							</#if>
   							</#if>
   						</#list>
   						<#include "/pages/selector/courseTypeSelectorBarwithoutAuthority.ftl" />
    					<td class="grayStyle" width="25%" id="f_remark">&nbsp;<@bean.message key="attr.remark"/>：</td>
	    				<td class="brightStyle">
	     					<textarea name="auditStandard.remark" cols="55" rows="3">${(result.auditStandard.remark)?if_exists}</textarea>
	    				</td>
	  				</tr>
	   				<tr class="darkColumn">
	     				<td colspan="2" align="center">
	     					<input type="hidden" name="auditStandard.id" value="${(result.auditStandard.id)?if_exists}"/>
	       					<input type="button" value="<@bean.message key="system.button.submit"/>" onClick="doAction()" class="buttonStyle"/>
	       					<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
	     				</td>
	   				</tr>
     			</table>
    		</td>
   		</tr>
   		</form>
  	</table>
	<script>
		var bar = new ToolBar("bar", "标准维护", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
		
		var form = document.commonForm;
	    function doAction(){
	     	var a_fields = {
	     		'auditStandard.name':{'l':'<@bean.message key="attr.infoname"/>', 'r':true, 't':'f_name'},
	        	'auditStandard.studentType.id':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'},
	        	'auditStandard.remark':{'l':'<@msg.message key="attr.remark"/>', 'r':false, 't':'f_remark', 'mx':100}
	     	};
	     	var v = new validator(form, a_fields, null);
	     	if (v.exec()) {
	     		form.action = "auditStandard.do?method=save";
	     		form.target = "_self";
	        	form.submit();
	     	}
	   	}
	</script>
</body>
<#include "/templates/foot.ftl"/>