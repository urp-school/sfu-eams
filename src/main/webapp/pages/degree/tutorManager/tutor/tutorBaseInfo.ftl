 <table width="90%" align="center"  class="listTable">       
       <tr class="grayStyle">
         <td align="left" colspan="4"><B><@bean.message key="page.teacherForm.label"/></B></td>
       </tr>
	   
	   <div id= "baseinfo">
	   <tr>
    	 <td width="15%"  align="center" id="f_id" class="grayStyle"><@bean.message key="attr.code"/><font color="red">*</font>:</td>
	     <td width="35%" class="brightStyle" colspan='3'>
	 	 <#if tutorFlag>
	 	 	<input type="hidden" name="tutor.id" 
		       <#if result.tutorId?exists>
		       	value="${result.tutorId}"
		       <#else>
		        value="${tutor.id?if_exists}" 
		       </#if>/>
	       <#if result.tutorId?exists>
	       	${result.tutorId}
	       <#else>
	        ${tutor.id?if_exists} 
	       </#if>		       
		 <#else>
	     <input type="text" name="tutor.id" maxlength="32"
	       <#if result.tutorId?exists>
	       	value="${result.tutorId}"
	       <#else>
	        value="${tutor.id?if_exists}" 
	       </#if>
	     </#if>	
	     </td>
	     
	   </tr>
	   <tr>  
	     <td width="15%" align="center" id="f_code" class="grayStyle"><@bean.message key="teacher.code"/><font color="red">*</font>:</td>
	     <td width="35%" class="brightStyle"><input type="text" name="tutor.code" maxlength="32" value="${tutor.code?if_exists}" <#if tutorFlag>disabled</#if>/></td>	     	   	     	     
	     
	     <td width="15%" align="center" id="f_name" class="grayStyle"><@bean.message key="attr.infoname"/><font color="red">*</font>:</td>
	     <td width="35%" class="brightStyle"><input type="text" name="tutor.name" maxlength="20" value="${tutor.name?if_exists}" <#if tutorFlag>disabled</#if>/></td>	     
	   </tr>
	   <tr>
	   	 <td align="center" id="f_gender" class="grayStyle"><@bean.message key="common.gender"/>:</td>
	     <td class="brightStyle">
               <select name="tutor.gender.id" style="width:60px;" <#if tutorFlag>disabled</#if>>
               <#list result.genderList as gender>
               <#if gender.id?string == tutor.gender?if_exists.id?if_exists?string>
               <option value="${gender.id}" selected>${gender.name}
               <#else>
               <option value="${gender.id}">${gender.name}
               </#if>
               </#list>
	     </td>
	   	 <td align="center" id="f_department" class="grayStyle"><@bean.message key="entity.department"/>:</td>
	     <td class="brightStyle">
               <select name="tutor.department.id" style="width:100px" <#if tutorFlag>disabled</#if>>
               <#list result.departmentList as department>
               <#if department.id?string == tutor.department?if_exists.id?if_exists?string>
                 <option value="${department.id}" selected>${department.name}
               <#else>
                 <option value="${department.id}">${department.name}
               </#if>               
               </#list>
	     </td>
	   </tr>
       <tr>
	   	 <td align="center" id="f_country" class="grayStyle"><@bean.message key="common.country"/>:</td>
	     <td class="brightStyle">
               <select name="tutor.country.id" style="width:100px" <#if tutorFlag>disabled</#if>>
               <#if tutor.country?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease"/>...</option>
               </#if>
               <#list result.countryList as country >
               <#if country.id?string == tutor.country?if_exists.id?if_exists>
               <option value="${country.id}" selected>${country.name}</option>
               <#else>
               <option value="${country.id}">${country.name}</option>
               </#if>
               </#list>
               </select>
	     </td>
	   	 <td align="center" id="f_nation" class="grayStyle"><@bean.message key="common.nation"/>:</td>
	     <td class="brightStyle">              
               <select name="tutor.nation.id" style="width:100px" <#if tutorFlag>disabled</#if>>
               <#if tutor.nation?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease"/>...</option>
               </#if>
               
               <#list result.nationList as nation>
               <#if nation.id?string ==tutor.nation?if_exists.id?if_exists>
               <option value="${nation.id}" selected>${nation.name}</option>
               <#else>
               <option value="${nation.id}">${nation.name}</option>
               </#if>
               </#list>
               </select>
	     </td>
	   </tr>
       <tr>
	   	 <td align="center" id="f_birthday" class="grayStyle"><@bean.message key="common.birthday"/>:</td>
         <td class="brightStyle">
            <input type="text" name="tutor.birthday" id="birthday" <#if tutorFlag>disabled</#if>
                   value="${result.tutor?if_exists.birthday?if_exists}"  onfocus='calendar()'/></td>	             
	   	 <td align="center" id="f_credentialNumber" class="grayStyle"><@bean.message key="teacher.CredentialNumber"/>:</td>
	   	 <td class="brightStyle"><input type="text" name="tutor.credentialNumber" value="${tutor.credentialNumber?if_exists}" <#if tutorFlag>disabled</#if>/></td>	             	   	 
	   </tr>
       <tr>
	   	 <td align="center" id="f_tutorType" class="grayStyle"><@bean.message key="entity.teacherType"/>:</td>
	     <td class="brightStyle">              
               <select name="tutor.teacherType.id" style="width:100px" <#if tutorFlag>disabled</#if>>
               <#if tutor.teacherType?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease"/>...</option>
               </#if>               
               <#list result.teacherTypeList as teacherType>
               <#if teacherType.id?string == tutor.teacherType?if_exists.id?if_exists>
               <option value="${teacherType.id}" selected>${teacherType.name}</option>
               <#else>
               <option value="${teacherType.id}">${teacherType.name}</option>
               </#if>
               </#list>
               </select>
	     </td>
	   	 <td align="center" id="f_duty" class="grayStyle"><@bean.message key="teacher.duty"/>:</td>
         <td class="brightStyle"><input type="text" name="tutor.duty" maxlength="20" value="${tutor.duty?if_exists}" <#if tutorFlag>disabled</#if>></td>
       </tr>
       </div>
     </table>
<script>
	function getTutor(obj)
	{
		if (window.event.keyCode == 13){			 
			 document.tutorForm.action="tutorManager.do?method=loadTutorFromTeacher&tutorId="+obj.value;
			 alert(document.tutorForm.action);
			 document.tutorForm.submit();			 
		}
	} 
</script>     