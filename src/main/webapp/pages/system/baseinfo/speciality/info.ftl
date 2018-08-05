<#include "/templates/head.ftl"/>
 <body>
 <#assign labInfo><@bean.message key="page.specialityInfo.label" /></#assign>    
	<#include "/templates/back.ftl"/> 
     <table  class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.code" />:</td>
	     <td class="content">${speciality.code}</td>
	     <td class="title" >&nbsp;<@bean.message key="attr.infoname" />:</td>
	     <td class="content"> ${speciality.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.engName" />:</td>
	     <td class="content">${speciality.engName?if_exists}  </td>
	     <td class="title" >&nbsp;<@bean.message key="common.abbreviation" />:</td>
         <td class="content">${speciality.abbreviation?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="entity.department" />:</td>
         <td class="content"><@i18nName speciality.department/></td>
	     <td class="title" ><@bean.message key="entity.studentType" /> :</td>
         <td class="content"><@i18nName speciality.stdType?if_exists/></td>
	   </tr>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="speciality.maxPeople" />:</td>
        <td  class="content" >${speciality.maxPeople?if_exists}</td>
	    <td class="title" ><@bean.message key="attr.dateEstablished" /> :</td>
        <td class="content">${(speciality.dateEstablished?string("yyyy-MM-dd"))?if_exists}</td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="attr.state" />:</td>
        <td class="content" >
      	      <#if speciality.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
        </td> 
	    <td class="title" ><@bean.message key="entity.secondSpeciality"/>:</td>
        <td class="content" >
      	      <#if speciality.is2ndSpeciality?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
        </td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="common.remark" />:</td>
        <td class="content">${speciality.remark?if_exists}</td>
	    <td class="title" >&nbsp;学科门类:</td>
        <td class="content"><@i18nName speciality.subjectCategory?if_exists/></td>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.createAt" />:</td>
        <td  class="content" >${(speciality.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title" >&nbsp;<@bean.message key="attr.modifyAt" />:</td>
        <td  class="content" >${(speciality.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr>       
      </table>
  </body>
<#include "/templates/foot.ftl"/>