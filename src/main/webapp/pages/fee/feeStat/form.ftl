	 	<tr valign="top">
	 		<td>
 				<fieldSet align=left> 
		 			<legend style="font-weight:bold;font-size:12px">${strFieldSetName}</legend>
						<table class="searchTable" width="100%">
						   <tr>
						     <td class="infoTitle">所在年级:</td>
						     <td><input type="text" name="${clazzStdName}.enrollYear" style="width:100px;" maxlength="7"></td>
						   </tr>
					       <tr> 
						     <td class="infoTitle"><@bean.message key="entity.studentType" />:</td>
						     <td>
						          <select id="std_stdTypeOfSpeciality" name="${clazzStdName}.type.id" style="width:100px;">               
						            <option value=""><@bean.message key="filed.choose" /></option>
						          </select>	 
					         </td>
							</tr> 	      
					    	<tr>
						     <td class="infoTitle"><@bean.message key="common.college" />:</td>
						     <td>
					           <select id="std_department" name="${clazzStdName}.department.id" style="width:100px;">
					         	  <option value=""><@bean.message key="filed.choose" />...</option>
					           </select>            
					         </td>       
					        </tr> 
						   <tr>
						     <td class="infoTitle"><@bean.message key="entity.speciality" />:</td>
						     <td>
					           <select id="std_speciality" name="${clazzStdName}.firstMajor.id" style="width:100px;">
					         	  <option value=""><@bean.message key="filed.choose" />...</option>
					           </select>     	             
					         </td>            
					        </tr>
					        
						   <tr>
						     <td class="infoTitle"><@bean.message key="entity.specialityAspect" />:</td>
						     <td>
					           <select id="std_specialityAspect" name="${clazzStdName}.firstAspect.id" style="width:100px;">
					         	  <option value=""><@bean.message key="filed.choose" />...</option>
					           </select>     	             
					         </td>         
					        </tr>  
					        ${needClass?if_exists}
					        ${needFeeType?if_exists}
					</table>
				</fieldSet>
			</td>
		</tr>
		${extraHTML?if_exists}
