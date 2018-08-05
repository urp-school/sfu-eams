	  <tr>
	     <td class="grayStyle" width="25%" id="f_secondSpecialityAspect">
	      &nbsp;<@bean.message key="entity.secondDirection"/><#if secondSpecialityAspectNeeded?exists&&secondSpecialityAspectNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${secondSpecialityAspectId}" 
	             value=<#if secondSpecialityAspectIdValue?exists>"${secondSpecialityAspectIdValue}"<#else>"${RequestParameters[secondSpecialityAspectId]?if_exists}"</#if> />
	      <input type="text" name="${secondSpecialityAspectDescriptions}" maxlength="100" onfocus="this.blur();" size="50" 
	             value=<#if secondSpecialityAspectDescriptionsValue?exists>"${secondSpecialityAspectDescriptionsValue}"<#else>"${RequestParameters[secondSpecialityAspectDescriptions]?if_exists}"</#if> />
	      &nbsp;
	      <input type="button" value="<@bean.message key="entity.specialityAspect"/>" name="button3" class="buttonStyle"
	             onClick="loadSecondSpecialityAspectsSelector()"/>
         </td>
	   </tr>
	   <script language="javascript">
		    function setSecondSpecialityAspectIdAndDescriptions(ids, descriptions){
		        setSelectorIdAndDescriptions(ids, descriptions, '${secondSpecialityAspectId}', '${secondSpecialityAspectDescriptions}');
		    }
		    
		    function loadSecondSpecialityAspectsSelector(){
		    		        
		            var url="specialityAspectSelector.do?method=allSecondSpecialityAspects" 		                    
		                    + "&secondSpecialityAspectId=" + document.all['${secondSpecialityAspectId}'].value;
		            popupMiniCommonSelector(url);
		        
	        }
	        
	        function resetSecondSpecialityAspectsSelector(){
	        	try{
		        	document.all['${secondSpecialityAspectDescriptions}'].value = "";
		        	document.all['${secondSpecialityAspectId}'].value = "";
		        }catch(e){
		        }	
	        }
	    </script>