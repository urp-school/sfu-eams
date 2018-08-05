 <table  width="90%" align="center" class="listTable">
       <tr  class="darkColumn">
          <td colspan="4" class="grayStyle"><input type="checkbox"  onClick="displayDiv(event,'degreeInfo')"/> 学历学位职称信息</td>
       </tr>
</table>
   <div id="degreeInfo" style="display:none">
   <table  width="90%" align="center" class="listTable"> 
   <tr>
   	 <td width="15%" align="center" id="f_eduDegreeOutside" class="grayStyle">
   	 	国外最高学历:
   	 </td>
     <td width="35%" class="brightStyle">    
           <select name="tutor.degreeInfo.eduDegreeOutside.id" style="width:100px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.eduDegreeOutside?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if>
           <#list result.eduDegreeList as eduDegree>
           <#if (tutor.degreeInfo?if_exists.eduDegreeOutside?if_exists.id?exists) && (eduDegree.id?if_exists == tutor.degreeInfo.eduDegreeOutside.id)>
           <option value="${eduDegree.id}" selected>${eduDegree.name}</option>
           <#else>
           <option value="${eduDegree.id}">${eduDegree.name}</option>
           </#if>
           </#list>
           </select>
     </td>
   	 <td width="15%" align="center" id="f_dateOfEduDegreeOutside" class="grayStyle"><@bean.message key="teacher.dateOfEduDegree" />:</td>
     <td width="35%" class="brightStyle">
       <input type="text" maxlength="10" name="tutor.degreeInfo.dateOfEduDegreeOutside" <#if tutorFlag>disabled</#if>
              value="${tutor.degreeInfo?if_exists.dateOfEduDegreeOutside?if_exists}" onfocus='calendar()'>
     </td>
   </tr>   
   <tr>
   	 <td width="15%" align="center" id="f_eduDegreeInside" class="grayStyle">
   	 	国内最高学历:
   	 </td>
     <td width="35%" class="brightStyle">    
           <select name="tutor.degreeInfo.eduDegreeInside.id" style="width:100px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.eduDegreeInside?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if>
           <#list result.eduDegreeList as eduDegree>
           <#if (tutor.degreeInfo?if_exists.eduDegreeInside?if_exists.id?exists)&&(eduDegree.id?if_exists == tutor.degreeInfo.eduDegreeInside.id)>
           <option value="${eduDegree.id}" selected>${eduDegree.name}</option>
           <#else>
           <option value="${eduDegree.id}">${eduDegree.name}</option>
           </#if>
           </#list>
           </select>
     </td>
   	 <td width="15%" align="center" id="f_dateOfEduDegreeInside" class="grayStyle"><@bean.message key="teacher.dateOfEduDegree" />:</td>
     <td width="35%" class="brightStyle">
       <input type="text" maxlength="10" name="tutor.degreeInfo.dateOfEduDegreeInside" <#if tutorFlag>disabled</#if>
              value="${tutor.degreeInfo?if_exists.dateOfEduDegreeInside?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
    
   <tr>
   	 <td align="center" id="f_degree" class="grayStyle"><@bean.message key="common.degree" />:</td>
     <td class="brightStyle">              
           <select name="tutor.degreeInfo.degree.id" style="width:100px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.degree?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if>   
           <#list result.degreeList as degree>
           <#if (tutor.degreeInfo?if_exists.degree?if_exists.id?exists) && (degree.id?if_exists == tutor.degreeInfo.degree.id)>
           <option value="${degree.id}" selected>${degree.name}</option>
           <#else>
           <option value="${degree.id}">${degree.name}</option>
           </#if>
           </#list>
           </select>
     </td>
   	 <td align="center" id="f_dateOfDegree" class="grayStyle"><@bean.message key="teacher.dateOfDegree" />:</td>
     <td class="brightStyle">
        <input type="text" maxlength="10" name="tutor.degreeInfo.dateOfDegree" <#if tutorFlag>disabled</#if>
               value="${tutor.degreeInfo?if_exists.dateOfDegree?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
  
   <tr>
   	 <td align="center" id="f_graduateSchool" class="grayStyle"><@bean.message key="teacher.graduateSchool" />:</td>
     <td colspan="3" class="brightStyle">
           <select name="tutor.degreeInfo.graduateSchool.id" style="width:522px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.graduateSchool?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if> 
           <#list result.schoolList as school>
           <#if (tutor.degreeInfo?if_exists.graduateSchool?if_exists.id?exists)&&(school.id?if_exists ==tutor.degreeInfo.graduateSchool.id)>
           <option value="${school.id}" selected>${school.name} </option>    
           <#else>
           <option value="${school.id}">${school.name}  </option>      
           </#if>
           </#list>
           </select>
     </td>
   </tr>
   <tr> 
   	 <td align="center" id="f_title" class="grayStyle"><@bean.message key="common.teacherTitle" />:</td>
     <td class="brightStyle">              
           <select name="tutor.title.id" style="width:100px" <#if tutorFlag>disabled</#if>>           
           <#if tutor.title?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if> 
           <#list result.titleList as title>
           <#if title.id?string == tutor.title?if_exists.id?if_exists?string>
           <option value="${title.id}" selected>${title.name}</option>
           <#else>
           <option value="${title.id}">${title.name}</option>
           </#if>
           </#list>           
           </select>
     </td>	      
   	 <td align="center" id="f_dateOfTitle" class="grayStyle"><@bean.message key="teacher.dateOfTitle" />:</td>
     <td class="brightStyle">
          <input type="text" name="tutor.dateOfTitle" maxlength="10" <#if tutorFlag>disabled</#if>
          value="${tutor.dateOfTitle?if_exists}" onfocus='calendar()'>
     </td>          
   </tr>
   <tr>
     <td colspan='2'> 									
		  <table width="100%"  align="center" class="listTable">
		   <tr>
		     <td class="grayStyle" width="30%" id="f_speciality">
		      &nbsp;<@msg.message key="entity.speciality"/>：
		     </td>
		     <td class="brightStyle">		
		           <select id="speciality" name="tutor.speciality.id"  style="width:100px;">
		         	  <option value="${tutor.speciality?if_exists.id?if_exists}" >...</option>
		           </select>  
		     </td>
		   </tr>		   
		   <tr>
		     <td class="grayStyle" width="30%" id="f_specialityAspect">
		      &nbsp;<@msg.message key="entity.specialityAspect"/>：
		     </td>
		     <td class="brightStyle">
		           <select id="specialityAspect" name="tutor.aspect.id"  style="width:100px;" >
		         	  <option value="${tutor.aspect?if_exists.id?if_exists}">...</option>
		           </select>     
		     </td>
		   </tr>		   	
		  </table> 								
	 </td>
     <td colspan='2'> 
     	<table width="100%"  align="center" class="listTable">
     	  <tr>
		   	 <td align="center" id="f_tutorType" class="grayStyle"><@bean.message key="common.tutorType" />:</td>
		     <td class="brightStyle">              
	               <select name="tutor.tutorType.id" style="width:100px" <#if tutorFlag>disabled</#if>>
		               <#if tutor.degreeInfo?if_exists.tutorType?if_exists.id?exists>
		               <#else>
		                <option value="">...</option>
		               </#if>                
		               <#list result.tutorTypeList as tutorType>
		               <#if tutorType.id?string ==tutor.tutorType?if_exists.id?if_exists?string>
		               <option value="${tutorType.id}" selected>${tutorType.name}</option>
		               <#else>
		               <option value="${tutorType.id}">${tutorType.name}</option>
		               </#if>
		               </#list>
	               </select>
		     </td>
		  </tr>
		  <tr>   
	       	 <td align="center" id="f_dateOfTutor" class="grayStyle"><@bean.message key="teacher.dateOfTutor" />:</td>
	         <td class="brightStyle">
	            <input type="text" name="tutor.dateOfTutor" maxlength="10" <#if tutorFlag>disabled</#if>
	                   value="${tutor.degreeInfo?if_exists.dateOfTutor?if_exists}" onfocus='calendar()'>
	         </td>
     	  </tr>     	  
     	</table>
	 </td> 		  
   </tr>	            
   </table>      
   </div>
