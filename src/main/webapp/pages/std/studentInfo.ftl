	   <#if result.student?exists>
	      <#assign stdId=result.student.id?if_exists/>
	      <#assign studentName=result.student.name?if_exists/>
	      <#assign engName=result.student.engName?if_exists/>
	      <#assign enrollYear=result.student.enrollYear?if_exists/>
	      <#if (result.student.enrollYear?length>5)>
	      <#assign belongToYear=enrollYear?if_exists[0..3]/>
	      <#assign sequence=enrollYear?if_exists[5..result.student.enrollYear?length-1]/>
	      <#else>
	      <#assign belongToYear="0000"/>
	      <#assign sequence=0/>
	      </#if>
	      
	      <#if result.student.department?exists>
	         <#assign departmentIdValue=result.student.department.id/>
	         <#assign departmentDescriptionsValue=result.student.department.name/>
	      </#if>
	      
	      <#if result.student.firstMajor?exists>
	         <#assign specialityIdValue=result.student.firstMajor.id/>
	         <#assign specialityDescriptionsValue=result.student.firstMajor.name/>
	      </#if>
	      
	      <#if result.student.firstAspect?exists>
	         <#assign specialityAspectIdValue=result.student.firstAspect.id/>
	         <#assign specialityAspectDescriptionsValue=result.student.firstAspect.name/>
	      </#if>
	      
	      <#if result.student.type?exists>
	         <#assign studentTypeIdValue=result.student.type.id/>
	         <#assign studentTypeDescriptionsValue=result.student.type.name/>
	      </#if>
	      
	      <#if result.student.adminClasses?exists>
	      	 <#assign adminClasses=result.student.adminClasses/>
	      </#if>
	      
	      <#if result.student.isStudentStatusAvailable?exists>
	         <#assign isStudentStatusAvailable=result.student.isStudentStatusAvailable?string/>
	      <#else>
	      	 <#assign isStudentStatusAvailable="null"/>
	      </#if>
	      
	      <#assign remark=result.student.remark?if_exists/>
	   </#if>
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="info.studentRecordBasicInfo"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_stdCode">
	      &nbsp;<@bean.message key="attr.stdNo"/><#if RequestParameters['method']!="loadUpdateForm"><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <#if RequestParameters['method']=="loadUpdateForm">
	      ${(result.student.code)?default('')}
	      <input type="hidden" name="student.code" maxlength="32" value="${(result.student.code)?default('')}"/>
	      <#else>	
	      <input id="codeValue" type="text" name="student.code" maxlength="32" value="${(result.student.code)?default('')}"/>
      	  <#include "/pages/std/checkStdNo.ftl"/>
	      </#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_name">
	      &nbsp;<@bean.message key="attr.personName"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="student.name" maxlength="25" value="${studentName?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enName">
	      &nbsp;<@bean.message key="attr.engName"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="student.engName" maxlength="50" value="${engName?if_exists}"/>
	     </td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enrollYear">
	      &nbsp;<@bean.message key="filed.enrollYearAndSequence"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	       <input type="text" name="student.enrollYear" maxlength="6" size="7" value="${enrollYear?if_exists}"/>&nbsp;例如：2006-1
         </td>
	   </tr>
	   <#assign moduleName="StudentManager"/>
	   <#assign departmentId = "student.department.id"/>
       <#assign specialityId = "student.firstMajor.id"/>
	   <#assign specialityAspectId = "student.firstAspect.id"/>
       <#assign studentTypeId = "student.type.id"/>
	   <#--选择部门-->
	   <#assign departmentNeeded = true/>
	   <#assign departmentDescriptions = "departmentDescriptions"/>
	   <#include "/pages/selector/singleDepartmentSelectorBarWithAuthority.ftl"/>
       <#--选择专业-->
	   <#assign specialityDescriptions = "specialityDescriptions"/>
	   <#include "/pages/selector/singleSpecialitySelectorBarWithDepartment.ftl"/>
       <#--选择专业方向-->
	   <#assign specialityAspectDescriptions = "specialityAspectDescriptions"/>
	   <#include "/pages/selector/specialityAspectSelectorBarWithSpeciality.ftl"/>
	   <#--选择学生类别-->
	   <#assign studentTypeNeeded = true/>
	   <#assign studentTypeDescriptions = "studentTypeDescriptions"/>
	   <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl"/>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;行政管理院系： </td>
	     <td class="brightStyle">
	      <select name="student.managementDepart.id" style="width:200px;">
	        <option></option>
	        <#list result.managementDepartList?if_exists as managementDep>
	        <option value="${managementDep.id}" <#if (result.student.managementDepart.id)?exists && result.student.managementDepart.id == managementDep.id>selected</#if>><@i18nName managementDep?if_exists/></option>
	        </#list>
	      </select>
	     </td>
	  </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;学制：  </td>
	     <td class="brightStyle"><input type="text" name="student.schoolingLength" maxlength="10" size="7" value="${(result.student.schoolingLength)?if_exists}"/>&nbsp;例如：2或者2.5</td>
	  </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_isStudentStatusAvailable">
	      &nbsp;<@bean.message key="entity.isStudentStatusAvailable"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <input type="radio" name="student.active" value="true" <#if (result.student.active)?default(false)>checked</#if>><@bean.message key="entity.available"/>
	      <input type="radio" name="student.active" value="false" <#if !(result.student.active)?default(true)>checked</#if>><@bean.message key="entity.unavailable"/>
	     </td>
	  </tr>
      <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;是否在校<font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <input type="radio" name="student.inSchool" value="true" <#if (result.student.inSchool)?default(false)>checked</#if>>在校
	      <input type="radio" name="student.inSchool" value="false" <#if !(result.student.inSchool)?default(true)>checked</#if>>不在校
	     </td>
	  </tr>
	  	<tr>
     		<td class="grayStyle"  id="f_languageAbility">&nbsp;<@bean.message key="common.languageAbility"/>：</td>
     		<td class="brightStyle">
     		<select name="student.languageAbility.id" >
     			<#assign languageAbilityId=(result.student.languageAbility.id)?default(-1)/>
     			<option value="">&nbsp;</option>
	            <#list (result.languageAbilityList)?if_exists?sort_by("code") as languageAbilityElement>
	            	<option value="${languageAbilityElement.id}" <#if languageAbilityElement.id==languageAbilityId >selected</#if>><@i18nName languageAbilityElement/></option>
	            </#list>
            </select></td>
   		</tr>
     <tr>
     	<td class="grayStyle" width="25%" id="f_adminClass">&nbsp;<@bean.message key="entity.adminClass"/>：</td>
     	<td class="brightStyle">
     		<table class="listTable" width="100%"><tr width="100%"><td>
     		<select name="adminClass" MULTIPLE size="3" style="width:350px" onDblClick="JavaScript:removeSelectedOption(this.form['adminClass'])" >
            <#list adminClasses?if_exists?sort_by("code") as adminClass>
            	<#if !adminClass.speciality?if_exists.is2ndSpeciality?if_exists><option value="${adminClass['id']}"><@i18nName adminClass/></option></#if>
            </#list>
            </select>
            <input type="hidden" value="" name="adminClassIds"/>
            </td><td width="100%" align="left" valign="middle">
            &nbsp;<input OnClick="JavaScript:removeSelectedOption(this.form['adminClass'])" type="button" value="移出" class="buttonStyle"> 
            <br><br>
            &nbsp;<input type="button" value="<@msg.message key="action.add"/>" name="adminClassButton" class="buttonStyle" onClick="loadAdminClasssSelector()"/>
            &nbsp;<input type="checkbox" value="Y" name="adminClassSameSpeciality" checked/>同专业
		    <script language="javascript" >	
			    function setAdminClassIdAndDescriptions${(result.selectorId)?default(1)}(id, description){
			        var adminClassSelect=document.all['adminClass'];
			        var adminClassOption=new Option(description, id);
			        if (!hasOption(adminClassSelect, adminClassOption)){adminClassSelect.options[adminClassSelect.length]=adminClassOption}
			    }
			    
			    function loadAdminClasssSelector(){
		            var url="adminClassSelector.do?method=withSpeciality"+"&selectorId=1&adminClass.speciality.is2ndSpeciality=0";
		            if(document.all['adminClassSameSpeciality'].checked==true){
		            if(document.all['student.enrollYear'].value!=""){url+="&adminClass.enrollYear="+document.all['student.enrollYear'].value}
		            if(document.all['${studentTypeId}'].value!=""){url+="&adminClass.stdType.id="+document.all['${studentTypeId}'].value}
		            if(document.all['${departmentId}'].value!=""){url+="&adminClass.department.id="+document.all['${departmentId}'].value}
		            if(document.all['${specialityId}'].value!=""){url+="&adminClass.speciality.id="+document.all['${specialityId}'].value}
		            if(document.all['${specialityAspectId}'].value!=""){url+="&adminClass.aspect.id="+document.all['${specialityAspectId}'].value}
		            }
		            popupMiniCommonSelector(url);
		        }
		        
		        function resetAdminClasssSelector1(){
		        	
		        }
		    </script>
            <br>
            </td></tr></table>
        </td>
     </tr>
	  <tr>
	     <td class="grayStyle" width="25%" id="f_schoolDistrict">
	      &nbsp;<@bean.message key="entity.schoolDistrict"/>：
	     </td>
	     <td class="brightStyle">
	      	<@htm.i18nSelect name="student.schoolDistrict.id" datas=result.schoolDistrictList selected=(result.student.schoolDistrict.id)?default('')?string><option value="">...</option></@>
	     </td>
	  </tr>
	  <tr>
	     <td class="grayStyle" width="25%" id="f_remark">
	      &nbsp;<@bean.message key="common.remark"/>：
	     </td>
	     <td class="brightStyle">
	      	<textarea name="student.remark" cols="40" rows="1">${remark?if_exists}</textarea>
	     </td>
	  </tr>