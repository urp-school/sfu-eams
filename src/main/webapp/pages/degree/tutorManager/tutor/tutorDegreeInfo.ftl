 <table  width="90%" align="center" class="listTable">
       <tr  class="darkColumn">
          <td colspan="4" class="grayStyle"><input type="checkbox"  onClick="displayDiv(event,'degreeInfo')"/> 学历学位职称信息</td>
       </tr>
</table>
   <div id="degreeInfo" style="display:none">
   <table  width="90%" align="center" class="listTable"> 
   <tr>
   	 <td width="15%" align="center" id="f_eduDegree" class="grayStyle">
   	 <@bean.message key="common.eduDegree" />:
   	 </td>
     <td width="35%" class="brightStyle">    
           <select name="tutor.degreeInfo.eduDegree.id" style="width:100px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.eduDegree?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if>
           <#list result.eduDegreeList as eduDegree>
           <#if eduDegree.id?string == tutor.degreeInfo?if_exists.eduDegree?if_exists.id?if_exists>
           <option value="${eduDegree.id}" selected>${eduDegree.name}</option>
           <#else>
           <option value="${eduDegree.id}">${eduDegree.name}</option>
           </#if>
           </#list>
           </select>
     </td>
   	 <td width="15%" align="center" id="f_dateOfEduDegree" class="grayStyle"><@bean.message key="teacher.dateOfEduDegree" />:</td>
     <td width="35%" class="brightStyle">
       <input type="text" name="tutor.degreeInfo.dateOfEduDegree" maxlength="10" <#if tutorFlag>disabled</#if>
              value="${tutor.degreeInfo?if_exists.dateOfEduDegree?if_exists}" onfocus='calendar()'>
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
           <#if degree.id?string == tutor.degreeInfo?if_exists.degree?if_exists.id?if_exists>
           <option value="${degree.id}" selected>${degree.name}</option>
           <#else>
           <option value="${degree.id}">${degree.name}</option>
           </#if>
           </#list>
           </select>
     </td>
   	 <td align="center" id="f_dateOfDegree" class="grayStyle"><@bean.message key="teacher.dateOfDegree" />:</td>
     <td class="brightStyle">
        <input type="text" name="tutor.degreeInfo.dateOfDegree" maxlength="10" <#if tutorFlag>disabled</#if>
               value="${tutor.degreeInfo?if_exists.dateOfDegree?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
  
   <tr>
   	 <td align="center" id="f_graduateSchool" class="grayStyle"><@bean.message key="teacher.graduateSchool" />:</td>
     <td colspan="3" class="brightStyle">
           <select name="degreeInfo.graduateSchool.id" style="width:522px" <#if tutorFlag>disabled</#if>>
           <#if tutor.degreeInfo?if_exists.graduateSchool?if_exists.id?exists>
           <#else>
            <option value=""><@bean.message key="common.selectPlease" />...</option>
           </#if> 
           <#list result.schoolList as school>
           <#if school.id?string ==tutor.degreeInfo?if_exists.graduateSchool?if_exists.id?if_exists>
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
        <#assign firstSubjectId=tutor.secondSubject?if_exists.firstSubject?if_exists.firstCode?if_exists>
        <#assign secondSubjectId=tutor.secondSubject?if_exists.secondCode?if_exists>
        <#assign thirdSubjectId=tutor.thirdSubject?if_exists.thirdCode?if_exists>
     	<#include "../subjectCascade.ftl"/>
	 </td>
     <td colspan='2'>
     	<table width="100%"  align="center" class="listTable">
     	  <tr>
		   	 <td align="center" id="f_tutorType" class="grayStyle"><@bean.message key="common.tutorType" />:</td>
		     <td class="brightStyle">              
	               <select name="tutor.tutorType.id" style="width:100px" <#if tutorFlag>disabled</#if>>
	               <#if tutor.degreeInfo?if_exists.tutorType?if_exists.id?exists>
	               <#else>
	                <option value=""><@bean.message key="common.selectPlease" />...</option>
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
	            <input type="text" name="tutor.dateOfTutor" <#if tutorFlag>disabled</#if>
	                   value="${tutor.degreeInfo?if_exists.dateOfTutor?if_exists}" onfocus='calendar()'>
	         </td>
     	  </tr>
	      <tr><td colspan='2' class="brightStyle">&nbsp;</td></tr>	     	  
     	</table>
	 </td> 		  
   </tr>	            
   </table>      
   </div>
