	   <tr>
	     <td class="grayStyle" width="25%" id="${courseTypeTDid?default('f_courseType')}"><#assign defaultCourseTypePurpose><@bean.message key="entity.courseType"/></#assign>
	      &nbsp;${courseTypePurpose?default(defaultCourseTypePurpose)}<#if courseTypeNeeded?exists&&courseTypeNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${courseTypeId}" 
	             value=<#if courseTypeIdValue?exists>"${courseTypeIdValue}"<#else>"${RequestParameters[courseTypeId]?if_exists}"</#if> />
	      <input type="text" name="${courseTypeDescriptions}" maxlength="100" onfocus="this.blur();" size="50" style="width:400px"
	             value="<#if courseTypeDescriptionsValue?exists>${courseTypeDescriptionsValue}<#elseif RequestParameters[courseTypeDescriptions]?exists>${RequestParameters[courseTypeDescriptions]}<#elseif result[courseTypeDescriptions]?exists>${result[courseTypeDescriptions]}<#else></#if>"/>
	      &nbsp;
	      <input type="button" value="<@bean.message key="entity.courseType"/>" name="button3" class="buttonStyle"
	             onClick="loadCourseTypeSelector${courseTypeId}()"/>
         </td>
	   </tr>	   
       <script language="javascript" >
            function loadCourseTypeSelector${courseTypeId}(){
                var url = "courseTypeSelector.do?method=withoutAuthority&courseTypeId="
                          + document.all['${courseTypeId}'].value + "&showCourseType=${courseTypeId}";
                popupMiniCommonSelector(url);
            }
       
		    function setCourseTypeIdAndDescriptions${courseTypeId}(ids, descriptions){
		        setSelectorIdAndDescriptions(','+ids+',', descriptions, '${courseTypeId}', '${courseTypeDescriptions}');
		    }
		    
		    function resetCourseTypeSelector${courseTypeId}(){
	        	document.all['${courseTypeId}'].value = "";
	        	document.all['${courseTypeDescriptions}'].value = "";
	        }
       </script>