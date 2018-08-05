<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript" >
   function doAction(form){
     var a_fields = {
          'teachModulus.studentType.id':{'l':'<@bean.message key="field.teachModulus.selectStudentType"/>', 'r':true, 't':'f_studentTypeId'},
          'teachModulus.courseCategory.id':{'l':'<@bean.message key="field.teachModulus.selectCourseCategory"/>', 'r':true, 't':'f_courseCategory'},
          'teachModulus.maxPeople':{'l':'<@bean.message key="field.teachModulus.maxPerson"/>', 'r':true, 't':'f_maxPerson','mx':5,'f':'unsigned'},
          'teachModulus.minPeople':{'l':'<@bean.message key="field.teachModulus.minPerson"/>', 'r':true, 't':'f_minPerson','mx':5,'f':'unsigned'},
          'teachModulus.modulusValue':{'l':'<@bean.message key="field.teachModulus.teachModulus"/>', 'r':true, 't':'f_teachModulus','f':'unsignedReal','mx':5},
          'teachModulus.department.id':{'l':'<@bean.message key="field.teachModulus.selectCreateDepart"/>', 'r':true, 't':'f_createDepart'},
          'teachModulus.remark':{'l':'<@bean.message key="field.teachModulus.remark"/>', 't':'f_remark','mx':500}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        	form.action="teachModulus.do?method=update";
        	form.submit();
     }
   }
   </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" scrolling="yes">
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
	<tr>
    <td>
    	<#assign labInfo><@bean.message key="field.teachModulus.modifyTeachModulus"/></#assign>
       <#include "/templates/back.ftl">
    </td>
   </tr>
	<tr>
	<td>
	<form name="teachModulusForm" method="post" action="" onsubmit="return false;">
	<table width="100%" align="center" class="listTable">
		<tr align="center" class="darkColumn">
			<td colspan="2">
				<@bean.message key="field.teachModulus.modifyTeachModulus"/>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" id="f_studentTypeId">
				<@bean.message key="field.teachModulus.selectStudentType"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle" >
				<select name="teachModulus.studentType.id" style="width:300px;">
						<option value=""><@bean.message key="field.teachModulus.selectStudentType"/></option>
					<#list result.studentTypeList?if_exists as studentType>
						<#if studentType.id==result.teachModulus.studentType.id>
						<option value="${studentType.id}" selected>${studentType.name}</option>
						<#else>
						<option value="${studentType.id}">${studentType.name}</option>
						</#if>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" id="f_courseCategory">
				<@bean.message key="field.teachModulus.selectCourseCategory"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle">
				<select name="teachModulus.courseCategory.id" style="width:300px;">
							<option value=""><@bean.message key="field.teachModulus.selectCourseCategory"/></option>
						<#list result.courseCategoryList?if_exists as courseCategory>
							<#if courseCategory.id==result.teachModulus.courseCategory.id>
								<option value="${courseCategory.id}" selected>${courseCategory.name}</option>
							<#else>
								<option value="${courseCategory.id}">${courseCategory.name}</option>
							</#if>
						</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" id="f_maxPerson">
				<@bean.message key="field.teachModulus.maxPerson"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle">
				<input type="text" name="teachModulus.maxPeople" style="width:300px;" value="${result.teachModulus.maxPeople}" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle"  id="f_minPerson">
				<@bean.message key="field.teachModulus.minPerson"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle">
				<input type="text" name="teachModulus.minPeople" style="width:300px;" value="${result.teachModulus.minPeople}" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" id="f_teachModulus">
				<@bean.message key="field.teachModulus.teachModulus"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle">
				<input type="text" name="teachModulus.modulusValue" style="width:300px;" value="${result.teachModulus.modulusValue?string("#0.00")}" maxlength="5"/>
			</td>
		</tr>
		<tr>
			<td align="center"  class="grayStyle"  id="f_createDepart">
				<@bean.message key="field.teachModulus.selectCreateDepart"/><font color="red">*</font>:
			</td>
			<td align="left" class="brightStyle">
				<select name="teachModulus.department.id" style="width:300px;">
					<	option value=""><@bean.message key="field.teachModulus.selectCreateDepart"/></option>
					<#list result.adminList?if_exists as admin>
					<#if admin.id==result.teachModulus.department.id>
						<option value="${admin.id}" selected>${admin.name}</option>
					<#else>
						<option value="${admin.id}">${admin.name}</option>
					</#if>
					</#list>
				</select>
			</td>
	   </tr>
		<tr>
			<td align="center"  class="grayStyle" id="f_remark">
				<@bean.message key="field.teachModulus.remark"/>:
			</td>
			<td align="left" class="brightStyle">
				<textarea name="teachModulus.remark" rows="5" cols="30" style="width:300px;">${result.teachModulus.remark?if_exists}</textarea>
			</td>
		</tr>
		<tr class="darkColumn">
		 <td colspan="2" align="center" >
		 	<input type="hidden" name="teachModulus.id" value="${result.teachModulus.id}">
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
		</tr>
	</table>
	</form>
	</td>
	</tr>
	</table>
  </body>
<#include "/templates/foot.ftl"/>