	   <tr>
	     <td class="grayStyle" width="25%" id="f_speciality">
	      &nbsp;<@bean.message key="entity.speciality"/><#if specialityNeeded?exists&&specialityNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${specialityId}" 
	             value=<#if specialityIdValue?exists>"${specialityIdValue}"<#else>"${RequestParameters[specialityId]?if_exists}"</#if> />
	      <input type="text" name="${specialityDescriptions}" maxlength="100" onfocus="this.blur();" size="50" 
	             value=<#if specialityDescriptionsValue?exists>"${specialityDescriptionsValue}"<#else>"${RequestParameters[specialityDescriptions]?if_exists}"</#if> />
	      &nbsp;
	      <input type="button" value="<@bean.message key="entity.speciality"/>" name="button2" class="buttonStyle"
	             onClick="loadSpecialitySelector()"/>
         </td>
	   </tr>
	   <script language="javascript">
		   function setSpecialityIdAndDescriptions(ids, descriptions){
		        setSelectorIdAndDescriptions(ids, descriptions, '${specialityId}', '${specialityDescriptions}');
		    }
		    
		    function loadSpecialitySelector(){
		        if(document.all['${departmentId}'].value == ""){
		            alert("<@bean.message key="attr.selector.firstSelectCollege"/>！");
		            return false;
		        }else{
		            var url="specialitySelector.do?method=withDepartment" 
		                    + "&departmentId=" + document.all['${departmentId}'].value
		                    + "&studentTypeId=" + document.all['${studentTypeId}'].value;
		            popupMiniCommonSelector(url);
		        }
	        }
	        
	        function resetSpecialitySelector(){
	        	try{
		        	document.all['${specialityDescriptions}'].value = "";
		        	document.all['${specialityId}'].value = "";
		        	resetSpecialityAspectsSelector();
		        }catch(e){
		        }	
	        }
	   </script>