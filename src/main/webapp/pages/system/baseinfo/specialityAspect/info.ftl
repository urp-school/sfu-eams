<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@bean.message key="page.specialityAspectInfo.label" /></#assign>   
<#include "/templates/back.ftl"/> 
     <#assign aspect = specialityAspect>
     <table  class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.code" />:</td>
	     <td class="content">${aspect.code}</td>
	     <td class="title" >&nbsp;<@bean.message key="attr.infoname" />:</td>
	     <td class="content"> ${aspect.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.engName" />:</td>
	     <td class="content">${aspect.engName?if_exists}  </td>
	     <td class="title" >&nbsp;<@bean.message key="common.abbreviation" />:</td>
         <td class="content">${aspect.abbreviation?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="entity.department" />:</td>
         <td class="content"><@i18nName aspect.speciality?if_exists.department/></td>
	     <td class="title" >&nbsp;<@bean.message key="entity.speciality"/> :</td>
         <td class="content"><@i18nName aspect.speciality/></td>	    
	   </tr>
	   <tr>
         <td class="title" >&nbsp;<@bean.message key="attr.dateEstablished" /> :</td>
         <td class="content">${(aspect.dateEstablished?string("yyyy-MM-dd"))?if_exists}</td>	   
        <td class="title" >&nbsp;<@bean.message key="specialityAspect.maxPeople" />:</td>
        <td  class="content" >${aspect.maxPeople?if_exists}</td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="attr.state" />:</td>
        <td class="content" >
      	      <#if aspect.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
        </td> 
	    <td class="title" ><@bean.message key="entity.secondDirection"/>:</td>
        <td class="content" >
      	      <#if aspect.speciality.is2ndSpeciality?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
        </td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="common.remark" />:</td>
        <td class="content" colspan="3">${aspect.remark?if_exists}</td>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.createAt" />:</td>
        <td  class="content" >${(aspect.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title" >&nbsp;<@bean.message key="attr.modifyAt" />:</td>
        <td  class="content" >${(aspect.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr>       
      </table>
  </body>
<#include "/templates/foot.ftl"/>