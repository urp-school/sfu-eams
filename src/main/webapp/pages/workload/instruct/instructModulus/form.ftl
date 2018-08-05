<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript" >
   function doAction(form){
     var a_fields = {
          'instructModulus.studentType.id':{'l':'<@msg.message key="instructWorkload.selectStudentType"/>', 'r':true, 't':'f_studentTypeId'},
          'instructModulus.modulusValue':{'l':'<@msg.message key="instructWorkload.modulus"/>', 'r':true, 't':'f_instructModulus','f':'unsignedReal'},
          'instructModulus.department.id':{'l':'<@msg.message key="instructWorkload.selectCreateDepart"/>', 'r':true, 't':'f_createDepart'},
          'instructModulus.remark':{'l':'<@msg.message key="instructWorkload.remark"/>', 't':'f_remark','mx':200}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        	form.action="instructModulus.do?method=save";
        	form.submit();
     }
   }
   </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" scrolling="yes">
<#assign labInfo>指导工作量系数</#assign>
<#include "/templates/back.ftl">
	<table width="100%"  class="formTable">
		<form name="instructModulusForm" method="post" action="" onsubmit="return false;">
		<tr align="center" class="darkColumn">
			<td colspan="2">指导工作量系数信息</td>
		</tr>
		<tr>
			<td  class="title" id="f_studentTypeId"><@msg.message key="entity.studentType"/>:</td>
			<td ><@htm.i18nSelect datas=stdTypeList selected=(instructModulus.studentType.id)?default('')?string  name="instructModulus.studentType.id" style="width:300px;"/>
			</td>
		</tr>
		<tr>
			<td id="f_instructModulus" class="title"><font color="red">*</font><@msg.message key="instructWorkload.modulus"/>:</td>
			<td >
				<input type="text" name="instructModulus.modulusValue" maxlength="5" style="width:300px;" value="<#if (instructModulus.modulusValue)?exists>${(instructModulus.modulusValue)?string("##.00")}</#if>"/>
			</td>
		</tr>
		<tr>
			<td class="title"  id="f_createDepart">创建部门:</td>
			<td>
			 <@htm.i18nSelect datas=departmentList selected=(instructModulus.department.id)?default('')?string name="instructModulus.department.id" style="width:300px;"/>
			</td>
	   </tr>
	   <tr>
			<td class="title">工作量系数性质:</td>
			<td>
				<select name="instructModulus.itemType" style="width:300px;">
                    <option value="practice" <#if (instructModulus.itemType)?default('practice')?string=='practice'>selected</#if>>实习指导</option>
					<option value="thesis" <#if (instructModulus.itemType)?default('practice')?string=='thesis'>selected</#if>>毕业指导</option>
					<option value="usual" <#if (instructModulus.itemType)?default('practice')?string=='usual'>selected</#if>>平时指导</option>
				</select>
			</td>
	   </tr>
		<tr>
			<td class="title" id="f_remark"><@msg.message key="instructWorkload.remark"/>:</td>
			<td >
				<textarea name="instructModulus.remark" rows="5" cols="30" style="width:300px;">${(instructModulus.remark)?if_exists}</textarea>
			</td>
		</tr>
		<tr class="darkColumn">
		 <td colspan="2" align="center" >
		 	<input type="hidden" name="instructModulus.id" value="${(instructModulus.id)?default("")}">
	       <button name="button1" onClick="doAction(this.form)"><@msg.message key="system.button.submit"/></button>&nbsp;
	     </td>
		</tr>
	</form>
	</table>
  </body>
<#include "/templates/foot.ftl"/>