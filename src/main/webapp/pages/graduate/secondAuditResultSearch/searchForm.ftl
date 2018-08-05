<table width="100%">
	<tr valign="top" style="font-size:8pt">
		<td style="text-align:text;text-valign:bottom;font-weight:bold" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;学籍查询项</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2"><img src="${static_base}/images/action/keyline.gif" align="top" width="100%" height="2"/></td>
	</tr>
	<form name="${formName}" method="post" action="${formAction}" target="pageIFrame" onsubmit="return false;">
	<tr>
		<td id="f_studentType"><@bean.message key="entity.studentType"/><font color='red'>*</font></td>
		<td>
			<select id="stdType" name="calendar.studentType.id" style="${formElementWidth}"/>
				<option value="${RequestParameters['calendar.studentType.id']?if_exists}"></option>
			</select>
		</td>
	</tr>
	<tr>
		<td id="f_year"><@bean.message key="attr.year2year"/><font color='red'>*</font></td>
		<td>
			<select id="year" name="calendar.year" style="${formElementWidth}"/>
				<option value="${RequestParameters['calendar.year']?if_exists}"></option>
			</select>
		</td>
	</tr>
	<tr>
		<td id="f_term"><@bean.message key="attr.term"/><font color='red'>*</font></td>
 		<td>
     		<select id="term" name="calendar.term" style="${formElementWidth}"/>
        		<option value="${RequestParameters['calendar.term']?if_exists}"></option>
      		</select>
 		</td>
	</tr>
	<script>
		function doValidate(){
			var a_fields = {
         		'year':{'l':'<@bean.message key="attr.year2year"/>', 'r':true, 't':'f_year'},
         		'term':{'l':'<@bean.message key="attr.term"/>', 'r':true, 't':'f_term'},
         		'stdType':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
   			};     
   			var v = new validator(document.${formName}, a_fields, null);
   			return v.exec();
   		}
   	</script>
   	<#assign stdTypeId = "calendar.studentType.id"/>
 	<tr>
		<td id="f_secondSpeciality">双专业</td>
		<td>
			<select id="secondMajor" name="std.secondMajor.id" style="${formElementWidth}"/>
				<option value="${RequestParameters['std.secondMajor.id']?if_exists}">...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td id="f_specialityAspect">双专业方向</td>
 		<td>
   			<select id="secondAspect" name="std.secondAspect.id" style="${formElementWidth}"/>
 	  			<option value="${RequestParameters['std.secondAspect.id']?if_exists}">...</option>
   			</select>
 		</td>
	</tr>
	<#include "/templates/secondSpeciality2Select.ftl"/>
	<tr>
		<td width="38%"><@msg.message key="attr.stdNo"/></td>
		<td>
			<input type="text" name="std.code" maxlength="32" value="${RequestParameters['std.code']?if_exists}" style="${formElementWidth}"/>
		</td>
	</tr>
  	<tr>
		<td><@msg.message key="attr.personName"/></td>
		<td>
			<input type="text" name="std.name" maxlength="20" value="${RequestParameters['std.name']?if_exists}" style="${formElementWidth}"/>
		</td>
	</tr>
	<tr>
		<td id="f_belongToYear"><@bean.message key="filed.enrollYearAndSequence"/></td>
		<td>
			<input type="text" name="std.enrollYear" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}" style="${formElementWidth}"/>
		</td>
	</tr>
	<tr>
		<#assign enrollYearId = "std.enrollYear"/>
 		<#assign departmentId = ""/>
 		<#assign specialityId = "std.secondMajor.id"/>
 		<#assign specialityAspectId = "std.secondAspect.id"/>
 		<#assign adminClassId = "adminClasssId"/>
   		<#assign adminClassDescriptions = "adminClassDescriptions"/>
   		<#assign colspanId = 1 />
   		<#assign majorTypeId = 2 />
   		<#assign selectorId = 2 />
   		<#assign adminClassKeyMessage>双专业班级</#assign>
   		<#include "../adminClassSelectorBarWithSpeciality.ftl"/>
	</tr>
	<tr>
   		<td id="f_isSecondMajorStudy">双专业是否就读</td>
 		<td>
   			<select id="isSecondMajorStudy" name="std.isSecondMajorStudy" style="${formElementWidth}"/>
 	  			<option value="">...</option>
 	  			<option value="1" <#if RequestParameters['std.isSecondMajorStudy']?default("")=="1">selected</#if>>就读</option>
 	  			<option value="0" <#if RequestParameters['std.isSecondMajorStudy']?default("")=="0">selected</#if>>不就读</option>
   			</select>
 		</td>
	</tr>
	<tr>
		<td id="f_isSecondMajorThesisNeed">双专业是否写论文</td>
 		<td>
   			<select name="std.isSecondMajorThesisNeed" style="${formElementWidth}"/>
 	  			<option value="">...</option>
 	  			<option value="1" <#if RequestParameters['std.isSecondMajorThesisNeed']?default("")=="1">selected</#if>>需要</option>
 	  			<option value="0" <#if RequestParameters['std.isSecondMajorThesisNeed']?default("")=="0">selected</#if>>不需要</option>
   			</select>
 		</td>
	</tr>
	<tr>
	 	<td id="f_active">学籍是否有效</td>
	 	<td>
	 		<select name="std.active" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
				<option value="">...</option>
				<option value="1" <#if RequestParameters['std.active']?default("1")=="1">selected</#if>>有效</option>
				<option value="0" <#if RequestParameters['std.active']?default("-1")=="0">selected</#if>>无效</option>
			</select>
	 	</td>
	</tr>
	<tr>
	 	<td id="f_inSchool">学生是否在校</td>
	 	<td>
	 		<select name="std.inSchool" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
				<option value="">...</option>
				<option value="1" <#if RequestParameters['std.inSchool']?default("1")=="1">selected</#if>>有效</option>
				<option value="0" <#if RequestParameters['std.inSchool']?default("-1")=="0">selected</#if>>无效</option>
			</select>
	 	</td>
	</tr>
	<tr>
	 	<td id="f_studentStatus">学籍状态</td>
	 	<td>
	 		<select name="std.state.id" style="${formElementWidth}"/>
				<option value="">...</option>
				<#list result.studentStateList as studentState>
					<option value="${studentState.id}" <#if RequestParameters['std.state.id']?default("0")==studentState.id?string >selected</#if>  /><@i18nName studentState /></option>
				</#list>
			</select>
	 	</td>
	</tr>
  	<tr>
 		<td id="f_omitSmallTerm">是否计算小学期</td>
 		<td>
     		<select id="omitSmallTerm" name="omitSmallTerm" style="${formElementWidth}"/>						
				<option value="1" <#if RequestParameters['omitSmallTerm']?default("-1")=="1">selected</#if>>计算</option>
				<option value="0" <#if RequestParameters['omitSmallTerm']?default("-1")=="0">selected</#if>>不计算</option>
			</select>
 		</td>	     		
  	</tr>
  	<tr>
   		<td colspan="2" align="center">
		    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
		    <input type="reset" onClick="document.${formName}.reset()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>				    
   		</td>
  	</tr>
	</form>
	<#include "/templates/calendarSpecialitySelect.ftl"/>
</table>
<br>
<script>
	var form = document.${formName};
	function search() {
		form.action = "${formAction}?method=search&orderBy=std.code asc";
		addInput(form, "std.type.id", form['calendar.studentType.id'].value, "hidden");
		addAllParams(form);
		document.pageGoForm.target = "pageIFrame";
		form.submit();
	}
	
	function addAllParams(form){
		var params = getInputParams(form,null,false);
       	addInput(form,"params",params);
	}
</script>