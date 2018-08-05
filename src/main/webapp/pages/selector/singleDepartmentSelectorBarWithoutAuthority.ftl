	   
	   <tr>
	     <td class="grayStyle" width="25%" id="f_department">
	      &nbsp;<@bean.message key="common.college"/><#if departmentNeeded?exists&&departmentNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${departmentId}" 
	             value=<#if departmentIdValue?exists>"${departmentIdValue}"<#else>"${RequestParameters[departmentId]?if_exists}"</#if> />
	      <input type="text" name="${departmentDescriptions}" maxlength="100" onfocus="this.blur();" size="50" 
	             value=<#if departmentDescriptionsValue?exists>"${departmentDescriptionsValue}"<#else>"${RequestParameters[departmentDescriptions]?if_exists}"</#if> />
	      &nbsp;
	      <input type="button" value="<#if departmentSelectorBarName?exists>${departmentSelectorBarName}<#else><@bean.message key="common.college"/></#if>" name="button2" class="buttonStyle"
	             onClick="loadDepartmentSelector()"/>
         </td>
	   </tr>
	   <script language="javascript">
	    function setDepartmentIdAndDescriptions(ids, descriptions){
	        setSelectorIdAndDescriptions(ids, descriptions, '${departmentId}', '${departmentDescriptions}');
	    }
	    
	    function loadDepartmentSelector(){
	        var url = "departmentSelector.do?method=withoutAuthority&departmentId="
	                  + document.all['${departmentId}'].value
	        popupMiniCommonSelector(url);
	    }
	    function resetDepartmentSelector(){
	    	try{
		    	document.all['${departmentDescriptions}'].value = "";
		    	document.all['${departmentId}'].value = "";
		    	resetSpecialitySelector();
		    }catch(e){
		    }	
	    }
	   </script>