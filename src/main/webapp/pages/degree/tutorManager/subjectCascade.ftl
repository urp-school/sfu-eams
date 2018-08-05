  <table width="100%"  align="center" class="listTable">
   <tr>
     <td class="grayStyle" width="30%" id="f_firstSubject">
      &nbsp;<@bean.message key="filed.firstSubject" />：
     </td>
     <td class="brightStyle">
 	   <#if tutorFlag>
 	   		${(tutor.speciality.name)?default("")}
 	   <#else>
           <select id="tutorSpeciality" name="tutor.speciality.id"  style="width:100px;">
         	  <option value="${tutor.speciality.id?if_exists}"><@bean.message key="common.selectPlease" />...</option>
           </select>  
   	   </#if>
     </td>
   </tr>
   
   <tr>
     <td class="grayStyle" width="30%" id="f_secondSubject">
      &nbsp;<@bean.message key="filed.secondSubject" />：
     </td>
     <td class="brightStyle">
     	   <#if tutorFlag>
     	   		${(tutor.aspect.name)?if_exists}
     	   <#else>		
           <select id="tutorAspect" name="tutor.aspect.id"  style="width:100px;" >
         	  <option value="${tutor.aspect.id?if_exists}"><@bean.message key="common.selectPlease" />...</option>
           </select>     
		   </#if>
     </td>
   </tr>
   <tr>
     <td class="grayStyle" width="30%" id="f_thirdSubject">
      &nbsp;<@bean.message key="filed.tutorAndMaster" />：
     </td>
     <td class="brightStyle">
     	   <#if tutorFlag>
     	   		${tutor?if_exists.thirdSubject?if_exists.name}
     	   <#else>
           <select id="thirdSubject" name="tutor.thirdSubject.thirdCode"  style="width:100px;" >
         	  <option value="${thirdSubjectId?if_exists}"><@bean.message key="common.selectPlease" />...</option>
           </select>     
		   </#if>
     </td>
   </tr>
   	
  </table>  
<#if !tutorFlag> 	 
<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='scripts/secondSpeciality2Select.js'></script>
<script>
    var specialityArray = new Array();
    <#list specialityList as speciality>
    specialityArray[${speciality_index}]={'id':'${speciality.id?if_exists}','name':'<@i18nName speciality/>'};
    </#list>
    var ss = new SecondSpeciality2Select("tutorSpeciality","tutorAspect",true,true);
    ss.init(specialityArray);
</script>
</#if>