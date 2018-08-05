	  <tr>
	     <td class="grayStyle" width="25%" id="f_specialityAspect">
	      &nbsp;<@bean.message key="entity.specialityAspect"/><#if specialityAspectNeeded?exists&&specialityAspectNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${specialityAspectId}" 
	             value=<#if specialityAspectIdValue?exists>"${specialityAspectIdValue}"<#else>"${RequestParameters[specialityAspectId]?if_exists}"</#if> />
	      <input type="text" name="${specialityAspectDescriptions}" maxlength="100" onfocus="this.blur();" size="50" 
	             value=<#if specialityAspectDescriptionsValue?exists>"${specialityAspectDescriptionsValue}"<#else>"${RequestParameters[specialityAspectDescriptions]?if_exists}"</#if> />
	      &nbsp;
	      <input type="button" value="<@bean.message key="entity.specialityAspect"/>" name="button3" class="buttonStyle"
	             onClick="loadSpecialityAspectsSelector()"/>
         </td>
	   </tr>
	   <script language="javascript">
		    function setSpecialityAspectIdAndDescriptions(ids, descriptions){
		        setSelectorIdAndDescriptions(ids, descriptions, '${specialityAspectId}', '${specialityAspectDescriptions}');
		    }
		    
		    function loadSpecialityAspectsSelector(){
		        if(document.all['${specialityId}'].value == ""){
		            alert("<@bean.message key="attr.selector.firstSelectSpeciality"/>！");
		            return false;
		        }else{
		            var url="specialityAspectSelector.do?method=withSpeciality" 
		                    + "&specialityId=" + document.all['${specialityId}'].value
		                    + "&studentTypeId=" + document.all['${studentTypeId}'].value
		                    + "&departmentId=" + document.all['${departmentId}'].value;
		            popupMiniCommonSelector(url);
		        }
	        }
	        
	        function resetSpecialityAspectsSelector(){
	        	try{
		        	document.all['${specialityAspectDescriptions}'].value = "";
		        	document.all['${specialityAspectId}'].value = "";
		        }catch(e){
		        }	
	        }
	    </script>