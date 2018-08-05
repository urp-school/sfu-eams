	  	 <#assign defaultAdminClassKeyMessage><@bean.message key="entity.adminClass"/></#assign>
	     <td id="f_adminClass${selectorId?default(1)}">${adminClassKeyMessage?default(defaultAdminClassKeyMessage)}<#if adminClassNeeded?exists&&adminClassNeeded><font color="red">*</font></#if></td>
	     <td colspan="${colspanId?default(1)}">
				      <#assign sizeId = 10*(colspanId?default(1)) />
	      <input type="hidden" name="${adminClassId}${selectorId?default(1)}" 
	             value=<#if adminClassIdValue?exists>"${adminClassIdValue}"<#else>"${RequestParameters[adminClassId+selectorId?default(1)]?if_exists}"</#if> />	      
	      <input type="text" name="${adminClassDescriptions}${selectorId?default(1)}" maxlength="50" onfocus="this.blur();"
	             value=<#if adminClassDescriptionsValue?exists>"${adminClassDescriptionsValue}"<#else>"${RequestParameters[adminClassDescriptions+selectorId?default(1)]?if_exists}"</#if> style="width:60px"/>
	      <input type="button" value="<@bean.message key="entity.adminClass"/>" name="button3${selectorId?default(1)}" class="buttonStyle"
	             onClick="loadAdminClasssSelector${selectorId?default(1)}()" />         

		   <script language="javascript" >
			    function setAdminClassIdAndDescriptions${selectorId?default(1)}(ids, descriptions){
			        setSelectorIdAndDescriptions(ids, descriptions, '${adminClassId}${selectorId?default(1)}', '${adminClassDescriptions}${selectorId?default(1)}');
			    }
			    
			    function loadAdminClasssSelector${selectorId?default(1)}(){
			            var url="adminClassSelector.do?method=withSpeciality"+"&selectorId=${selectorId?default(1)}"
			            		<#if !(majorTypeId)?exists><#if (majorType.id)?exists>+"&adminClass.speciality.majorType.id=${majorType.id}"<#else>+"&adminClass.speciality.majorType.id=1"</#if><#else>+"&adminClass.speciality.majorType.id=${majorTypeId}"</#if>
			            		<#if enrollYearId?exists&&(enrollYearId?string?length>0)>+"&adminClass.enrollYear="+document.all['${enrollYearId}'].value</#if>
			            		<#if stdTypeId?exists&&(stdTypeId?string?length>0) >+"&adminClass.stdType.id=" + document.all['${stdTypeId}'].value</#if>
			            		<#if departmentId?exists&&(departmentId?string?length>0) >+"&adminClass.department.id=" + document.all['${departmentId}'].value</#if>
			            		<#if specialityId?exists&&(specialityId?string?length>0) >+"&adminClass.speciality.id=" + document.all['${specialityId}'].value</#if>
			                    <#if adminClassId?exists&&(adminClassId?string?length>0) >+"&adminClassId=" + document.all['${adminClassId}${selectorId?default(1)}'].value</#if>
			                    <#if specialityAspectId?exists&&(specialityAspectId?string?length>0) >+"&adminClass.aspect.id=" + document.all['${specialityAspectId}'].value</#if>;
			            popupMiniCommonSelector(url);
		        }
		        
		        function resetAdminClasssSelector${selectorId?default(1)}(){
		        	try{
			        	document.all['${adminClassDescriptions}${selectorId?default(1)}'].value = "";
			        	document.all['${adminClassId}${selectorId?default(1)}'].value = "";
			        }catch(e){
			        }	
		        }
		    </script>
         </td>
