	   <tr>
	     <td class="grayStyle" width="25%" id="f_studentType">
	      &nbsp;<@bean.message key="entity.studentType"/><#if studentTypeNeeded?exists&&studentTypeNeeded><font color="red">*</font></#if>：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="${studentTypeId}" 
	             value=<#if studentTypeIdValue?exists>"${studentTypeIdValue}"<#else>"${RequestParameters[studentTypeId]?if_exists}"</#if> />
	      <input type="text" name="${studentTypeDescriptions}" maxlength="100" onfocus="this.blur();" size="50" 
	             value=<#if studentTypeDescriptionsValue?exists>"${studentTypeDescriptionsValue}"<#else>"${RequestParameters[studentTypeDescriptions]?if_exists}"</#if> />
	      &nbsp;
	      <input type="button" value="<@bean.message key="entity.studentType"/>" name="button3" class="buttonStyle"
	             onClick="loadStudentTypeSelector()"/>
         </td>
	   </tr>	   
       <script language="javascript">
            function loadStudentTypeSelector(){
                var url = "studentTypeSelector.do?method=withoutAuthority&studentTypeId="
                          + document.all['${studentTypeId}'].value;
                popupMiniCommonSelector(url);
            }
       
		    function setStudentTypeIdAndDescriptions(ids, descriptions){
		        setSelectorIdAndDescriptions(ids, descriptions, '${studentTypeId}', '${studentTypeDescriptions}');
		    }
		    
		    function resetStudentTypeSelector(){
	        	document.all['${studentTypeId}'].value = "";
	        	document.all['${studentTypeDescriptions}'].value = "";
	        }
       </script>